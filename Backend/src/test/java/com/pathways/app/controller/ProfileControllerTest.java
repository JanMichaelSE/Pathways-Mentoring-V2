package com.pathways.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pathways.app.dto.UserDTO;
import com.pathways.app.model.Mentor;
import com.pathways.app.model.Student;
import com.pathways.app.model.User;
import com.pathways.app.payload.UpdateAdminProfileRequest;
import com.pathways.app.service.ProfileService;
import com.pathways.app.util.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProfileService profileService;
    @Autowired
    private ObjectMapper objectMapper;

    private User user;
    @BeforeEach
    void setUp() {
        user = new User();
        user.setId("Test1");
        user.setEmail("test@gmail.com");
        user.setPassword("TestPassword1234");
        user.setApproved(true);
        user.setRole("N/A");
    }

    @Test
    public void testGetAdminProfile() throws Exception {
        user.setEmail("test@gmail.com");
        user.setRole("Admin");
        UserDTO userDTO = UserMapper.toDTO(user);

        given(profileService.getAdminProfile("Test1")).willReturn(userDTO);

        mockMvc.perform(get("/api/profile/admin/Test1")
                .contentType(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("email").value("test@gmail.com"));
    }

    @Test
    public void testUpdateAdminProfile() throws Exception {
        // Construct Request
        UpdateAdminProfileRequest updatedUser = new UpdateAdminProfileRequest();
        updatedUser.setId(user.getId());
        updatedUser.setEmail("NewAdmin@gmail.com");
        updatedUser.setNewPassword("ChangedPassword1234");
        updatedUser.setOldPassword(user.getPassword());

        // Updated User to be returned
        user.setEmail("NewAdmin@gmail.com");
        user.setPassword("ChangedPassword1234");
        UserDTO updatedUserDTO = UserMapper.toDTO(user);

        when(profileService.updateAdminProfile(any(UpdateAdminProfileRequest.class))).thenReturn(updatedUserDTO);

        mockMvc.perform(put("/api/profile/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("email").value("NewAdmin@gmail.com"));
    }

    @Test
    public void testGetStudentProfile() throws Exception {
        user.setEmail("student@gmail.com");
        user.setPassword("TestPassword1234");
        user.setRole("Student");

        Student student = new Student();
        student.setUser(user);
        student.setId(123L);
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

        given(profileService.getStudentProfile(123L)).willReturn(student);

        mockMvc.perform(get("/api/profile/student/123")
                .contentType(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("email").value("student@gmail.com"))
                .andExpect(jsonPath("name").value("Jan Montalvo"));
    }

    @Test
    public void testGetMentorProfile() throws Exception {
        user.setEmail("mentor@gmail.com");
        user.setRole("Mentor");

        Mentor mentor = new Mentor();
        mentor.setUser(user);
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

        given(profileService.getMentorProfile(123L)).willReturn(mentor);

        mockMvc.perform(get("/api/profile/mentor/123")
                        .contentType(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("email").value("mentor@gmail.com"))
                .andExpect(jsonPath("name").value("Jan Montalvo"));
    }
}
