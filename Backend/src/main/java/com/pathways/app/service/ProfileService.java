package com.pathways.app.service;

import com.pathways.app.dto.MentorProfileDTO;
import com.pathways.app.dto.StudentProfileDTO;
import com.pathways.app.dto.UserDTO;
import com.pathways.app.exception.EmailAlreadyUsedException;
import com.pathways.app.exception.UserNotFoundException;
import com.pathways.app.exception.UserPasswordDoesNotMatchException;
import com.pathways.app.model.Mentor;
import com.pathways.app.model.Student;
import com.pathways.app.model.User;
import com.pathways.app.payload.UpdateAdminProfileRequest;
import com.pathways.app.payload.UpdateMentorProfileRequest;
import com.pathways.app.payload.UpdateStudentProfileRequest;
import com.pathways.app.repository.MentorRepository;
import com.pathways.app.repository.StudentRepository;
import com.pathways.app.repository.UserRepository;
import com.pathways.app.util.MentorProfileMapper;
import com.pathways.app.util.StudentProfileMapper;
import com.pathways.app.util.UserMapper;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    MentorRepository mentorRepository;

    public UserDTO getAdminProfile(String id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        return UserMapper.toDTO(user.get());
    }

    public UserDTO updateAdminProfile(UpdateAdminProfileRequest adminUser) {
        Optional<User> user = userRepository.findById(adminUser.getId());

        // User Validation
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        Optional<User> userByEmail = userRepository.findByEmail(adminUser.getEmail());
        if (adminUser.getEmail() != null && userByEmail.isPresent()) {
            throw new EmailAlreadyUsedException("A user with this email already exists");
        }
        if (adminUser.getOldPassword() != null && BCrypt.checkpw(adminUser.getOldPassword(), user.get().getPassword()) == false) {
            throw new UserPasswordDoesNotMatchException("The old password provided those not match. Please provide original password to proceed with the update of the user credentials.");
        }

        // Update User Fields
        if (adminUser.getEmail() != null) {
            user.get().setEmail(adminUser.getEmail());
        }
        if (adminUser.getOldPassword() != null && adminUser.getNewPassword() != null) {
            String hashedPassword = BCrypt.hashpw(adminUser.getNewPassword(), BCrypt.gensalt());
            user.get().setPassword(hashedPassword);
        }

        User updatedUser = userRepository.save(user.get());
        return UserMapper.toDTO(updatedUser);
    }

    public Student getStudentProfile(Long id) {
        Optional<Student> student = studentRepository.findById(id);

        if (student.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        return student.get();
    }

    public Student updateStudentProfile(UpdateStudentProfileRequest studentUser) {

        // Validate the student exists in the system
        Optional<Student> student = studentRepository.findById(studentUser.getId());
        if (student.isEmpty()) {
            throw new UserNotFoundException("A student with this Id does not exist in the system.");
        }

        Optional<User> user = userRepository.findById(student.get().getUser().getId());
        if (user.isEmpty()) {
            throw new UserNotFoundException("A user for this student those not exist. Please communicate with the system administrator.");
        }

        // Validate User fields
        Optional<User> userByEmail = userRepository.findByEmail(studentUser.getEmail());
        if (studentUser.getEmail() != null && userByEmail.isPresent()) {
            throw new EmailAlreadyUsedException("A user with this email already exists");
        }
        if (studentUser.getOldPassword() != null && BCrypt.checkpw(studentUser.getOldPassword(), user.get().getPassword()) == false) {
            throw new UserPasswordDoesNotMatchException("The old password provided those not match. Please provide original password to proceed with the update of the user credentials.");
        }

        StudentProfileDTO studentProfileDTO = StudentProfileMapper.toDTO(studentUser, student.get(), user.get());

        User updatedUser = userRepository.save(studentProfileDTO.getUser());
        Student updatedStudent = studentRepository.save(studentProfileDTO.getStudent());
        return updatedStudent;
    }

    public Mentor getMentorProfile(Long id) {
        Optional<Mentor> mentor = mentorRepository.findById(id);

        if (mentor.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        return mentor.get();
    }

    public Mentor updateMentorProfile(UpdateMentorProfileRequest mentorUser) {

        // Validate the mentor exists in the system
        Optional<Mentor> mentor = mentorRepository.findById(mentorUser.getId());
        if (mentor.isEmpty()) {
            throw new UserNotFoundException("A mentor with this Id does not exist in the system.");
        }

        Optional<User> user = userRepository.findById(mentor.get().getUser().getId());
        if (user.isEmpty()) {
            throw new UserNotFoundException("A user for this mentor those not exist. Please communicate with the system administrator.");
        }

        // Validate User fields
        Optional<User> userByEmail = userRepository.findByEmail(mentorUser.getEmail());
        if (mentorUser.getEmail() != null && userByEmail.isPresent()) {
            throw new EmailAlreadyUsedException("A user with this email already exists");
        }
        if (mentorUser.getOldPassword() != null && BCrypt.checkpw(mentorUser.getOldPassword(), user.get().getPassword()) == false) {
            throw new UserPasswordDoesNotMatchException("The old password provided those not match. Please provide original password to proceed with the update of the user credentials.");
        }

        MentorProfileDTO mentorProfileDTO = MentorProfileMapper.toDTO(mentorUser, mentor.get(), user.get());

        User updatedUser = userRepository.save(mentorProfileDTO.getUser());
        Mentor updatedMentor = mentorRepository.save(mentorProfileDTO.getMentor());
        return updatedMentor;
    }

}
