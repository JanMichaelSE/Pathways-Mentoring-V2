package com.pathways.app.controller;

import com.pathways.app.dto.UserDTO;
import com.pathways.app.model.Mentor;
import com.pathways.app.model.Student;
import com.pathways.app.model.User;
import com.pathways.app.payload.LoginRequest;
import com.pathways.app.payload.RegisterMentorRequest;
import com.pathways.app.payload.RegisterStudentRequest;
import com.pathways.app.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        UserDTO loggedInUser = authService.login(loginRequest.getEmail(), loginRequest.getPassword());

        if (loggedInUser.getAccessToken() == null) {
            return new ResponseEntity<>("Not Authorized", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(loggedInUser, HttpStatus.OK);
    }

    @PostMapping("/signup/admin")
    public @ResponseBody UserDTO registerAdmin(@Valid @RequestBody User user) {
        return authService.registerAdmin(user);
    }

//    @PostMapping("/logout")
//    public ResponseEntity<Void> logoutUser() {
//        authService.logout();
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }

    @PostMapping("/signup/student")
    public @ResponseBody Student registerStudent(@Valid @RequestBody RegisterStudentRequest registerStudentRequest) {
        return authService.registerStudent(registerStudentRequest);
    }


    @PostMapping("/signup/mentor")
    public @ResponseBody Mentor registerMentor(@Valid @RequestBody RegisterMentorRequest registerMentorRequest) {
        return authService.registerMentor(registerMentorRequest);
    }

//    @PutMapping("/forgotPassword")
//    public ResponseEntity<Void> forgotPassword(@RequestBody User user) {
//        authService.forgotPassword(user);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
//    @PutMapping("/resetPassword")
//    public ResponseEntity<Void> resetPassword(@RequestBody User user) {
//        authService.resetPassword(user);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
}
