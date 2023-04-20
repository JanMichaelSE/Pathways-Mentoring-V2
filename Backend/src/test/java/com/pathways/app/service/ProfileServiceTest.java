package com.pathways.app.service;

import com.pathways.app.dto.UserDTO;
import com.pathways.app.exception.EmailAlreadyUsedException;
import com.pathways.app.exception.UserNotFoundException;
import com.pathways.app.exception.UserPasswordDoesNotMatchException;
import com.pathways.app.model.User;
import com.pathways.app.payload.UpdateAdminProfileRequest;
import com.pathways.app.repository.UserRepository;
import com.pathways.app.service.ProfileService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProfileServiceTest {

    @InjectMocks
    private ProfileService profileService;
    @Mock
    private UserRepository userRepository;

    @Test
    public void testUpdateAdminProfile_UserNotFound() {
        // Arrange
        UpdateAdminProfileRequest updateRequest = new UpdateAdminProfileRequest();
        updateRequest.setId("Test123");
        updateRequest.setEmail("NewAdmin@gmail.com");
        updateRequest.setNewPassword("ChangedPassword1234");
        updateRequest.setOldPassword("OldPassword1234");

        when(userRepository.findById(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> profileService.updateAdminProfile(updateRequest));
    }

    @Test
    public void testUpdateAdminProfile_OldPasswordNotMatching() {
        // Arrange
        UpdateAdminProfileRequest updateRequest = new UpdateAdminProfileRequest();
        updateRequest.setId("Test123");
        updateRequest.setEmail("NewAdmin@gmail.com");
        updateRequest.setNewPassword("ChangedPassword1234");
        updateRequest.setOldPassword("WrongPassword");

        User existingUser = new User();
        existingUser.setId("Test123");
        existingUser.setEmail("NewAdmin@gmail.com");
        String hashedPassword = BCrypt.hashpw("CorrectPassword", BCrypt.gensalt());
        existingUser.setPassword(hashedPassword);

        when(userRepository.findById(anyString())).thenReturn(Optional.of(existingUser));

        // Act & Assert
        assertThrows(UserPasswordDoesNotMatchException.class, () -> profileService.updateAdminProfile(updateRequest));
    }

    @Test
    public void testUpdateAdminProfile_EmailAlreadyExists() {
        // Arrange
        UpdateAdminProfileRequest updateRequest = new UpdateAdminProfileRequest();
        updateRequest.setId("Test123");
        updateRequest.setEmail("NewAdmin@gmail.com");

        User existingUser = new User();
        existingUser.setId("Test123");
        existingUser.setEmail("NewAdmin@gmail.com");

        when(userRepository.findById(anyString())).thenReturn(Optional.of(existingUser));
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(existingUser));

        // Act & Assert
        assertThrows(EmailAlreadyUsedException.class, () -> profileService.updateAdminProfile(updateRequest));
    }

    @Test
    public void testUpdateAdminProfile_SuccessfulUpdate() {
        // Arrange
        UpdateAdminProfileRequest updateRequest = new UpdateAdminProfileRequest();
        updateRequest.setId("Test123");
        updateRequest.setEmail("NewAdmin@gmail.com");
        updateRequest.setNewPassword("ChangedPassword1234");
        updateRequest.setOldPassword("CorrectPassword");

        User existingUser = new User();
        existingUser.setId("Test123");
        existingUser.setEmail("OldAdmin@gmail.com");
        String hashedPassword = BCrypt.hashpw("CorrectPassword", BCrypt.gensalt());
        existingUser.setPassword(hashedPassword);

        User updatedUser = new User();
        updatedUser.setId(updateRequest.getId());
        updatedUser.setEmail(updateRequest.getEmail());
        String changedHashedPassword = BCrypt.hashpw(updateRequest.getNewPassword() ,BCrypt.gensalt());
        updatedUser.setPassword(changedHashedPassword);


        when(userRepository.findById(anyString())).thenReturn(Optional.of(existingUser));
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        UserDTO userDTO = profileService.updateAdminProfile(updateRequest);

        // Act & Assert
        assertEquals("NewAdmin@gmail.com", userDTO.getEmail());
    }
}
