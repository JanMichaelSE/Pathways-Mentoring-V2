package com.pathways.app.service;

import com.pathways.app.dto.UserDTO;
import com.pathways.app.exception.UserNotFoundException;
import com.pathways.app.model.User;
import com.pathways.app.payload.LoginRequest;
import com.pathways.app.repository.MentorRepository;
import com.pathways.app.repository.StudentRepository;
import com.pathways.app.repository.UserRepository;
import com.pathways.app.util.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MentorRepository mentorRepository;

    public UserDTO login(String email, String password) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

        if (user.getPassword().equals(password) == false) {
            return new UserDTO();
        }

        return UserMapper.toDTO(user);
    }

    public UserDTO registerAdmin(User user) {
        user.setRole("Admin"); // Default for admin users
        User savedUser = userRepository.save(user);
        return UserMapper.toDTO(savedUser);
    }

    public UserDTO registerStudent(User user) {
        User savedUser = userRepository.save(user);
        return UserMapper.toDTO(savedUser);
    }

    public UserDTO registerMentor(User user) {
        User savedUser = userRepository.save(user);
        return UserMapper.toDTO(savedUser);
    }

}
