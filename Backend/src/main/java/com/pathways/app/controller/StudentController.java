package com.pathways.app.controller;

import com.pathways.app.model.Student;
import com.pathways.app.payload.StudentMentorshipRequest;
import com.pathways.app.service.StudentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    StudentService studentService;

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/user/{userId}")
    public Student getStudentByUserId(@PathVariable String userId) {
        return studentService.getStudentByUserId(userId);
    }

    @GetMapping("/mentor/{mentorId}")
    public List<Student> getAllStudentsByMentor(@PathVariable Long mentorId) {
        return studentService.getStudentsByMentorId(mentorId);
    }

    @PutMapping("/request-mentorship")
    public String requestStudentMentorship(@Valid @RequestBody StudentMentorshipRequest studentMentorshipRequest) {
        return studentService.requestStudentMentorship(studentMentorshipRequest.getMentorEmail(), studentMentorshipRequest.getId());
    }

    @PutMapping("/cancel-mentorship/{studentId}")
    public String cancelStudentMentorship(@PathVariable @NotNull(message = "Must provide student id.") Long studentId) {
        return studentService.cancelStudentMentorship(studentId);
    }
}
