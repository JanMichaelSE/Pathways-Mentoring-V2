package com.pathways.app.service;

import com.pathways.app.dto.UserDTO;
import com.pathways.app.exception.UserNotFoundException;
import com.pathways.app.model.Student;
import com.pathways.app.model.User;
import com.pathways.app.payload.LoginRequest;
import com.pathways.app.payload.RegisterStudentRequest;
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

    public Student registerStudent(RegisterStudentRequest registerStudentRequest) {

        User userToSave = new User();
        userToSave.setEmail(registerStudentRequest.getEmail());
        userToSave.setPassword(registerStudentRequest.getPassword());
        userToSave.setRole("Student");
        User savedUser = userRepository.save(userToSave);

        Student studentToSave = new Student();
        studentToSave.setUser(savedUser);
        studentToSave.setEmail(registerStudentRequest.getEmail());
        studentToSave.setName(registerStudentRequest.getName());
        studentToSave.setPhone(registerStudentRequest.getPhone());
        studentToSave.setGender(registerStudentRequest.getGender());
        studentToSave.setGraduationDate(registerStudentRequest.getGraduationDate());
        studentToSave.setGpa(registerStudentRequest.getGpa());
        studentToSave.setInstitution(registerStudentRequest.getInstitution());
        studentToSave.setFieldOfStudy(registerStudentRequest.getFieldOfStudy());
        studentToSave.setHasResearch(registerStudentRequest.isHasResearch());
        studentToSave.setProfilePicture(registerStudentRequest.getProfilePicture());

        return studentRepository.save(studentToSave);
    }

    public UserDTO registerMentor(User user) {
        User savedUser = userRepository.save(user);
        return UserMapper.toDTO(savedUser);
    }

}
