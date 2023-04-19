package com.pathways.app.controller;

import com.pathways.app.dto.UserDTO;
import com.pathways.app.model.Mentor;
import com.pathways.app.model.Student;
import com.pathways.app.payload.UpdateAdminProfileRequest;
import com.pathways.app.payload.UpdateMentorProfileRequest;
import com.pathways.app.payload.UpdateStudentProfileRequest;
import com.pathways.app.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    ProfileService profileService;

    @GetMapping("/admin/{id}")
    public UserDTO getAdminProfile(@PathVariable String id) {
        return profileService.getAdminProfile(id);
    }

    @GetMapping("/student/{id}")
    public Student getStudentProfile(@PathVariable Long id) {
        return profileService.getStudentProfile(id);
    }

    @GetMapping("/mentor/{id}")
    public Mentor getMentorProfile(@PathVariable Long id) {
        return profileService.getMentorProfile(id);
    }

    @PutMapping("/admin")
    public UserDTO updateAdminProfile(@Valid @RequestBody UpdateAdminProfileRequest adminUser) {
        return profileService.updateAdminProfile(adminUser);
    }

    @PutMapping("/student")
    public Student updateStudentProfile(@Valid @RequestBody UpdateStudentProfileRequest studentUser) {
        return profileService.updateStudentProfile(studentUser);
    }

    @PutMapping("/mentor")
    public Mentor updateMentorProfile(@Valid @RequestBody UpdateMentorProfileRequest mentorUser) {
        return profileService.updateMentorProfile(mentorUser);
    }
}
