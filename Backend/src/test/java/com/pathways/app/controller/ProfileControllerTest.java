package com.pathways.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pathways.app.model.Mentor;
import com.pathways.app.model.Student;
import com.pathways.app.model.User;
import com.pathways.app.payload.UpdateAdminProfileRequest;
import com.pathways.app.repository.MentorRepository;
import com.pathways.app.repository.StudentRepository;
import com.pathways.app.repository.UserRepository;
import com.pathways.app.service.ProfileService;
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

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
public class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private MentorRepository mentorRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAdminProfile() throws Exception {
        User user = new User();
        user.setId("Test1");
        user.setEmail("test@gmail.com");
        String hashedPassword = BCrypt.hashpw("TestPassword1234", BCrypt.gensalt());
        user.setPassword(hashedPassword);
        user.setApproved(true);
        user.setRole("Admin");
        User savedUser = userRepository.save(user);

        // Act & Assert
        mockMvc.perform(get("/api/profile/admin/{id}", savedUser.getId())
                .contentType(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("email").value("test@gmail.com"));

        // Clean Up
        userRepository.deleteById(savedUser.getId());
    }

    @Test
    public void testUpdateAdminProfile() throws Exception {
        // Construct Request
        User user = new User();
        user.setId("Test1");
        user.setEmail("test@gmail.com");
        String hashedPassword = BCrypt.hashpw("TestPassword1234", BCrypt.gensalt());
        user.setPassword(hashedPassword);
        user.setApproved(true);
        user.setRole("Admin");
        User savedUser = userRepository.save(user);

        UpdateAdminProfileRequest updatedUser = new UpdateAdminProfileRequest();
        updatedUser.setId(savedUser.getId());
        updatedUser.setEmail("NewAdmin@gmail.com");
        updatedUser.setNewPassword("ChangedPassword1234");
        updatedUser.setOldPassword("TestPassword1234");


        // Act & Assert
        mockMvc.perform(put("/api/profile/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("email").value("NewAdmin@gmail.com"));

        // Clean Up
        userRepository.deleteById(savedUser.getId());
    }

    @Test
    public void testGetStudentProfile() throws Exception {
        // Construct Request
        User user = new User();
        user.setId("Test1");
        user.setEmail("test@gmail.com");
        String hashedPassword = BCrypt.hashpw("TestPassword1234", BCrypt.gensalt());
        user.setPassword(hashedPassword);
        user.setApproved(true);
        user.setRole("Student");
        User savedUser = userRepository.save(user);

        Student student = new Student();
        student.setUser(savedUser);
        student.setEmail("student@gmail.com");
        student.setName("Jan Montalvo");
        student.setPhone("7877101074");
        student.setGender("Male");
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        student.setGraduationDate(localDate);
        student.setGpa("3.25");
        student.setInstitution("Poly Technic of Puerto Rico");
        student.setFieldOfStudy("CS");
        student.setHasResearch(true);
        student.setProfilePicture("Test Photo");
        Student savedStudent = studentRepository.save(student);

        // Act & Assert
        mockMvc.perform(get("/api/profile/student/{id}", savedStudent.getId())
                .contentType(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("email").value("student@gmail.com"))
                .andExpect(jsonPath("name").value("Jan Montalvo"));

        // Clean Up
        userRepository.deleteById(savedUser.getId());
        studentRepository.deleteById(savedStudent.getId());
    }

    @Test
    public void testGetMentorProfile() throws Exception {
        User user = new User();
        user.setId("Test1");
        user.setEmail("test@gmail.com");
        String hashedPassword = BCrypt.hashpw("TestPassword1234", BCrypt.gensalt());
        user.setPassword(hashedPassword);
        user.setApproved(true);
        user.setRole("Mentor");
        User savedUser = userRepository.save(user);

        Mentor mentor = new Mentor();
        mentor.setUser(savedUser);
        mentor.setId(123L);
        mentor.setEmail("mentor@gmail.com");
        mentor.setName("Jan Montalvo");
        mentor.setPhone("7877101074");
        mentor.setGender("Male");
        mentor.setDepartment("CS");
        mentor.setAcademicDegree("COE");
        mentor.setFacultyStatus("Professor");
        mentor.setOffice("302P");
        mentor.setOfficeHours("12:00PM");
        mentor.setInterests("AI");
        mentor.setDescription("A passionate professor");
        mentor.setProfilePicture("Test Photo");
        Mentor savedMentor = mentorRepository.save(mentor);

        // Act & Assert
        mockMvc.perform(get("/api/profile/mentor/{id}", savedMentor.getId())
                        .contentType(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("email").value("mentor@gmail.com"))
                .andExpect(jsonPath("name").value("Jan Montalvo"));

        // Clean Up
        userRepository.deleteById(savedUser.getId());
        mentorRepository.deleteById(savedMentor.getId());
    }
}
