package com.pathways.app.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String gender;

    private Date graduationDate;

    @Column(nullable = false)
    private String gpa;

    @Column(nullable = false)
    private String institution;

    @Column(nullable = false)
    private String fieldOfStudy;

    @Column(nullable = false)
    private boolean hasResearch = false;

    @Column(nullable = false)
    private boolean isPendingMentorshipApproval = false;

    private String profilePicture;

    @Column(nullable = false)
    private Date createdDate = new Date();

    @Column(nullable = false)
    private Date lastModified = new Date();

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getGraduationDate() {
        return graduationDate;
    }

    public void setGraduationDate(Date graduationDate) {
        this.graduationDate = graduationDate;
    }

    public String getGpa() {
        return gpa;
    }

    public void setGpa(String gpa) {
        this.gpa = gpa;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    public void setFieldOfStudy(String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }

    public boolean isHasResearch() {
        return hasResearch;
    }

    public void setHasResearch(boolean hasResearch) {
        this.hasResearch = hasResearch;
    }

    public boolean isPendingMentorshipApproval() {
        return isPendingMentorshipApproval;
    }

    public void setPendingMentorshipApproval(boolean pendingMentorshipApproval) {
        isPendingMentorshipApproval = pendingMentorshipApproval;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
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

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }
}