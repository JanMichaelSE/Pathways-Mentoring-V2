package com.pathways.app.controller;

import com.pathways.app.model.Mentor;
import com.pathways.app.payload.AcceptStudentMentorshipRequest;
import com.pathways.app.service.MentorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mentors")
public class MentorController {
    @Autowired
    MentorService mentorService;

    @GetMapping
    public List<Mentor> getAllMentors() {
        return mentorService.getAllMentors();
    }

    @GetMapping("/unapproved")
    public List<Mentor> getAllUnapprovedMentors() {
        return mentorService.getAllUnapprovedMentors();
    }

    @PutMapping("/accept-mentorship")
    public String acceptStudentMentorshipRequest(@RequestBody AcceptStudentMentorshipRequest acceptRequest) {
        return mentorService.acceptStudentMentorshipRequest(acceptRequest.getStudentId(), acceptRequest.getMentorId());
    }

    @PutMapping("/approve-mentor/{mentorId}")
    public String approveMentorAccessRequest(@PathVariable Long mentorId) {
        return mentorService.approveMentorAccessRequest(mentorId);
    }


}
