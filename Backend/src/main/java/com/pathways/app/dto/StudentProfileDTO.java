package com.pathways.app.dto;

import com.pathways.app.model.Student;
import com.pathways.app.model.User;

public class StudentProfileDTO {
    private User user;
    private Student student;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
