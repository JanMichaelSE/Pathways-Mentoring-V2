package com.pathways.app.dto;

import com.pathways.app.model.Mentor;
import com.pathways.app.model.User;

public class MentorProfileDTO {
    User user;
    Mentor mentor;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Mentor getMentor() {
        return mentor;
    }

    public void setMentor(Mentor mentor) {
        this.mentor = mentor;
    }
}
