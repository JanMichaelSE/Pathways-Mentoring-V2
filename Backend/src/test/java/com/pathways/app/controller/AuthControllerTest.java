package com.pathways.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pathways.app.model.Mentor;
import com.pathways.app.model.Student;
import com.pathways.app.model.User;
import com.pathways.app.payload.LoginRequest;
import com.pathways.app.payload.RegisterMentorRequest;
import com.pathways.app.payload.RegisterStudentRequest;
import com.pathways.app.repository.MentorRepository;
import com.pathways.app.repository.StudentRepository;
import com.pathways.app.repository.UserRepository;
import com.pathways.app.service.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AuthService authService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private MentorRepository mentorRepository;

    @Test
    public void testLoginUser() throws Exception {
        // Arrange
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@gmail.com");
        loginRequest.setPassword("TestPassword1234");

        User user = new User();
        user.setEmail("test@gmail.com");
        String hashedPassword = BCrypt.hashpw("TestPassword1234", BCrypt.gensalt());
        user.setPassword(hashedPassword);
        user.setApproved(true);
        user.setRole("Admin");
        User savedUser = userRepository.save(user);

        // Act & Assert
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(jsonPath("email").value(loginRequest.getEmail()))
                .andExpect(jsonPath("role").value("Admin"));

        // Clean Up
        userRepository.deleteById(savedUser.getId());
    }

    @Test
    public void testRegisterAdmin() throws Exception {
        // Arrange
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setPassword("TestPassword1234");
        user.setRole("Admin");

        // Act & Assert
        mockMvc.perform(post("/api/auth/signup/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(jsonPath("email").value(user.getEmail()))
                .andExpect(jsonPath("role").value("Admin"));
    }

    @Test
    public void testRegisterStudent() throws Exception {
        // Arrange
        RegisterStudentRequest registerStudentRequest = new RegisterStudentRequest();
        registerStudentRequest.setEmail("student@gmail.com");
        registerStudentRequest.setPassword("TestPassword1234");
        registerStudentRequest.setName("Jan Montalvo");
        registerStudentRequest.setPhone("7877101074");
        registerStudentRequest.setGender("Male");
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        registerStudentRequest.setGraduationDate(localDate);
        registerStudentRequest.setGpa("3.25");
        registerStudentRequest.setInstitution("Poly Technic of Puerto Rico");
        registerStudentRequest.setFieldOfStudy("CS");
        registerStudentRequest.setHasResearch(true);
        registerStudentRequest.setProfilePicture("Test Photo");

        // Act & Assert
        MvcResult result = mockMvc.perform(post("/api/auth/signup/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerStudentRequest)))
                .andExpect(jsonPath("email").value(registerStudentRequest.getEmail()))
                .andExpect(jsonPath("name").value(registerStudentRequest.getName()))
                .andReturn();

        // Clean Up
        Student createdStudent = objectMapper.readValue(result.getResponse().getContentAsString(), Student.class);
        Optional<User> user = userRepository.findByEmail(createdStudent.getEmail());
        user.ifPresent(value -> userRepository.deleteById(value.getId()));
        studentRepository.deleteById(createdStudent.getId());
    }

    @Test
    public void testRegisterMentor() throws Exception {
        // Arrange
        RegisterMentorRequest registerMentorRequest = new RegisterMentorRequest();
        registerMentorRequest.setEmail("mentor@gmail.com");
        registerMentorRequest.setPassword("TestPassword1234");
        registerMentorRequest.setName("Jan Montalvo");
        registerMentorRequest.setPhone("7877101074");
        registerMentorRequest.setGender("Male");
        registerMentorRequest.setDepartment("CS");
        registerMentorRequest.setAcademicDegree("COE");
        registerMentorRequest.setFacultyStatus("Professor");
        registerMentorRequest.setOffice("302P");
        registerMentorRequest.setOfficeHours("12:00PM");
        registerMentorRequest.setInterests("AI");
        registerMentorRequest.setDescription("A passionate professor");
        registerMentorRequest.setProfilePicture("Test Photo");

        // Act & Assert
        MvcResult result = mockMvc.perform(post("/api/auth/signup/mentor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerMentorRequest)))
                .andExpect(jsonPath("email").value(registerMentorRequest.getEmail()))
                .andExpect(jsonPath("name").value(registerMentorRequest.getName()))
                .andExpect(jsonPath("facultyStatus").value(registerMentorRequest.getFacultyStatus()))
                .andExpect(jsonPath("description").value(registerMentorRequest.getDescription()))
                .andExpect(jsonPath("interests").value(registerMentorRequest.getInterests()))
                .andReturn();

        // Clean Up
        Mentor createdMentor = objectMapper.readValue(result.getResponse().getContentAsString(), Mentor.class);
        Optional<User> user = userRepository.findByEmail(createdMentor.getEmail());
        user.ifPresent(value -> userRepository.deleteById(value.getId()));
        mentorRepository.deleteById(createdMentor.getId());
    }

}
