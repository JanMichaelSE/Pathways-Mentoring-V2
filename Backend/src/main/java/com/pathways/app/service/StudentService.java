package com.pathways.app.service;

import com.pathways.app.exception.UserNotFoundException;
import com.pathways.app.model.Mentor;
import com.pathways.app.model.Student;
import com.pathways.app.repository.MentorRepository;
import com.pathways.app.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    MentorRepository mentorRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentByUserId(String userId) {
        Optional<Student> student = studentRepository.findByUserId(userId);

        if (student.isEmpty()) {
            throw new UserNotFoundException("A student with this user id does not exist.");
        }

        return student.get();
    }

    public List<Student> getStudentsByMentorId(Long mentorId) {
        return studentRepository.findByMentorId(mentorId);
    }

    public String requestStudentMentorship(String mentorEmail, Long studentId) {
        Optional<Student> student = studentRepository.findById(studentId);
        if (student.isEmpty()) {
            throw new UserNotFoundException("A student with this Id doesn't exist");
        }

        Optional<Mentor> mentor = mentorRepository.findByEmail(mentorEmail);
        if (mentor.isEmpty()) {
            throw new UserNotFoundException("A mentor with this email does not exist.");
        }

        student.get().setPendingMentorshipApproval(true);
        student.get().setMentor(mentor.get());
        studentRepository.save(student.get());

        return "Mentorship Request has been sent.";
    }

    public String cancelStudentMentorship(Long studentId) {
        Optional<Student> student = studentRepository.findById(studentId);
        if (student.isEmpty()) {
            throw new UserNotFoundException("A student with this Id doesn't exist");
        }

        if (student.get().getMentor() == null) {
            throw new UserNotFoundException("No mentor found for this student at the moment.");
        }

        student.get().setMentor(null);
        studentRepository.save(student.get());

        return "Mentor has been removed and mentorship has been canceled";
    }
}
