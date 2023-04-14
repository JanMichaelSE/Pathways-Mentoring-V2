package com.pathways.app.model;

import jakarta.persistence.*;

import javax.xml.crypto.Data;
import java.util.Date;


@Entity
@Table(name = "app_user")
public class User {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

   @Column(nullable = false, length = 150)
   private String email;

   @Column(nullable = false, length = 65)
   private String password;

   @Column(nullable = false, length = 10)
   private String role;

    @Column(nullable = false)
    private boolean isApproved = false;

    private String accessToken;

    private String refreshToken;

    @Column(nullable = false)
    private Date createdDate = new Date();

    @Column(nullable = false)
    private Date lastModified = new Date();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Student student;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Mentor mentor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Mentor getMentor() {
        return mentor;
    }

    public void setMentor(Mentor mentor) {
        this.mentor = mentor;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }
}
