package com.pathways.app.repository;

import com.pathways.app.model.User;
import com.pathways.app.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByEmail_WhenUserExists() {
        // Arrange
        User user = new User();
        user.setEmail("text@gmail.com");
        user.setPassword("TestPassword1234");
        user.setRole("Admin");

        userRepository.save(user);

        // Act
        Optional<User> foundUser = userRepository.findByEmail(user.getEmail());

        // Assert
        assertTrue(foundUser.isPresent());
        assertEquals(user.getEmail(), foundUser.get().getEmail());
    }

    @Test
    public void testFindByEmail_WhenUserDoesNotExist() {
        // Arrange
        // No user is saved in the DB

        // Act
        Optional<User> foundUser = userRepository.findByEmail("test@gmail.com");

        // Assert
        assertFalse(foundUser.isPresent());
    }
}
