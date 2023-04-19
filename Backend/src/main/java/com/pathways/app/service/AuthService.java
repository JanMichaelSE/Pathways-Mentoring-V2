package com.pathways.app.service;

import com.pathways.app.dto.UserDTO;
import com.pathways.app.exception.EmailAlreadyUsedException;
import com.pathways.app.exception.UserNotFoundException;
import com.pathways.app.exception.UserPasswordDoesNotMatchException;
import com.pathways.app.model.Mentor;
import com.pathways.app.model.Student;
import com.pathways.app.model.User;
import com.pathways.app.payload.RegisterMentorRequest;
import com.pathways.app.payload.RegisterStudentRequest;
import com.pathways.app.repository.MentorRepository;
import com.pathways.app.repository.StudentRepository;
import com.pathways.app.repository.UserRepository;
import com.pathways.app.util.UserMapper;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MentorRepository mentorRepository;

    public UserDTO login(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        boolean doPasswordsMatch = BCrypt.checkpw(password, user.get().getPassword());
        if (doPasswordsMatch == false) {
            throw new UserPasswordDoesNotMatchException("Provided password does not match.");
        }

        return UserMapper.toDTO(user.get());
    }

    public UserDTO registerAdmin(User user) {

        Optional<User> doesUserExist = userRepository.findByEmail(user.getEmail());
        if (doesUserExist.isPresent()) {
            throw new EmailAlreadyUsedException("The email provided is already taken.");
        }

        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        user.setRole("Admin"); // Default for admin users
        User savedUser = userRepository.save(user);
        return UserMapper.toDTO(savedUser);
    }

    public Student registerStudent(RegisterStudentRequest student) {

        Optional<User> doesUserExist = userRepository.findByEmail(student.getEmail());
        if (doesUserExist.isPresent()) {
            throw new EmailAlreadyUsedException("The email provided is already taken.");
        }

        User userToSave = new User();
        userToSave.setEmail(student.getEmail());
        userToSave.setPassword(BCrypt.hashpw(student.getPassword(), BCrypt.gensalt()));
        userToSave.setApproved(true);
        userToSave.setRole("Student");
        User savedUser = userRepository.save(userToSave);

        Student studentToSave = new Student();
        studentToSave.setUser(savedUser);
        studentToSave.setEmail(student.getEmail());
        studentToSave.setName(student.getName());
        studentToSave.setPhone(student.getPhone());
        studentToSave.setGender(student.getGender());
        studentToSave.setGraduationDate(student.getGraduationDate());
        studentToSave.setGpa(student.getGpa());
        studentToSave.setInstitution(student.getInstitution());
        studentToSave.setFieldOfStudy(student.getFieldOfStudy());
        studentToSave.setHasResearch(student.isHasResearch());
        studentToSave.setProfilePicture(student.getProfilePicture());

        return studentRepository.save(studentToSave);
    }

    public Mentor registerMentor(RegisterMentorRequest mentor) {

        Optional<User> doesUserExist = userRepository.findByEmail(mentor.getEmail());
        if (doesUserExist.isPresent()) {
            throw new EmailAlreadyUsedException("The email provided is already taken.");
        }

        User userToSave = new User();
        userToSave.setEmail(mentor.getEmail());
        userToSave.setPassword(BCrypt.hashpw(mentor.getPassword(), BCrypt.gensalt()));
        userToSave.setApproved(false);
        userToSave.setRole("Mentor");
        User savedUser = userRepository.save(userToSave);

        Mentor mentorToSave = new Mentor();
        mentorToSave.setUser(savedUser);
        mentorToSave.setEmail(mentor.getEmail());
        mentorToSave.setName(mentor.getName());
        mentorToSave.setPhone(mentor.getPhone());
        mentorToSave.setGender(mentor.getGender());
        mentorToSave.setDepartment(mentor.getDepartment());
        mentorToSave.setAcademicDegree(mentor.getAcademicDegree());
        mentorToSave.setOffice(mentor.getOffice());
        mentorToSave.setOfficeHours(mentor.getOfficeHours());
        mentorToSave.setFacultyStatus(mentor.getFacultyStatus());
        mentorToSave.setInterests(mentor.getInterests());
        mentorToSave.setDescription(mentor.getDescription());
        mentorToSave.setProfilePicture(mentor.getProfilePicture());

        return mentorRepository.save(mentorToSave);
    }

}
