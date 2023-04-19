package com.pathways.app.service;

import com.pathways.app.exception.UserNotFoundException;
import com.pathways.app.model.Mentor;
import com.pathways.app.model.Student;
import com.pathways.app.model.User;
import com.pathways.app.repository.MentorRepository;
import com.pathways.app.repository.StudentRepository;
import com.pathways.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MentorService {

    @Autowired
    MentorRepository mentorRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    UserRepository userRepository;

    public List<Mentor> getAllMentors() {
        return mentorRepository.findAll();
    }

    public List<Mentor> getAllUnapprovedMentors() {
        return mentorRepository.findAllUnapprovedMentors();
    }

    public String acceptStudentMentorshipRequest(Long studentId, Long mentorId) {
        Optional<Student> student = studentRepository.findById(studentId);
        if (student.isEmpty()) {
            throw new UserNotFoundException("A student with this Id does not exist.");
        }

        Optional<Mentor> mentor = mentorRepository.findById(mentorId);
        if (mentor.isEmpty()) {
            throw new UserNotFoundException("A mentor with this Id does not exist.");
        }

        // * TODO: Remember to create the associated records for the mentor and students.
        student.get().setPendingMentorshipApproval(false);
        studentRepository.save(student.get());

        return "The student mentorship request has been accepted.";
    }

    public String approveMentorAccessRequest(Long mentorId) {
        Optional<Mentor> mentor = mentorRepository.findById(mentorId);
        if (mentor.isEmpty()) {
            throw new UserNotFoundException("A mentor with this Id does not exist.");
        }

        Optional<User> user = userRepository.findById(mentor.get().getUser().getId());
        if (user.isEmpty()) {
            throw new UserNotFoundException("A user with this Id does not exist.");
        }

        user.get().setApproved(true);
        userRepository.save(user.get());

        return "The mentor has been given access.";
    }
}
