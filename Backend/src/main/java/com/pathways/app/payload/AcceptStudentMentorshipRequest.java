package com.pathways.app.payload;

import jakarta.validation.constraints.NotNull;

public class AcceptStudentMentorshipRequest {

    @NotNull(message = "The mentor Id must be provided.")
    private Long mentorId;

    @NotNull(message = "The student Id must be provided.")
    private Long studentId;

    public Long getMentorId() {
        return mentorId;
    }

    public void setMentorId(Long mentorId) {
        this.mentorId = mentorId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
}
