package com.pathways.app.util;

import com.pathways.app.dto.StudentProfileDTO;
import com.pathways.app.model.Student;
import com.pathways.app.model.User;
import com.pathways.app.payload.UpdateStudentProfileRequest;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Date;

public class StudentProfileMapper {
    public static StudentProfileDTO toDTO(UpdateStudentProfileRequest studentUser, Student student, User user) {
        StudentProfileDTO studentProfileDTO = new StudentProfileDTO();

        // Update User Fields
        if (studentUser.getEmail() != null) user.setEmail(studentUser.getEmail());
        if (studentUser.getOldPassword() != null && studentUser.getNewPassword() != null) {
            String hashedPassword = BCrypt.hashpw(studentUser.getNewPassword(), BCrypt.gensalt());
            user.setPassword(hashedPassword);
        }

        // Update Student Fields
        if (studentUser.getName() != null) student.setName(studentUser.getName());
        if (studentUser.getPhone() != null) student.setPhone(studentUser.getPhone());
        if (studentUser.getGender() != null) student.setGender(studentUser.getGender());
        if (studentUser.getFieldOfStudy() != null) student.setFieldOfStudy(studentUser.getFieldOfStudy());
        if (studentUser.getInstitution() != null) student.setInstitution(studentUser.getInstitution());
        if (studentUser.getGpa() != null) student.setGpa(studentUser.getGpa());
        if (studentUser.getGraduationDate() != null) student.setGraduationDate(studentUser.getGraduationDate());
        if (studentUser.isHasResearch() != student.isHasResearch()) student.setHasResearch(studentUser.isHasResearch());
        if (studentUser.getProfilePicture() != null) student.setProfilePicture(studentUser.getProfilePicture());
        student.setLastModified(new Date()); // Set last modified date to today.

        studentProfileDTO.setUser(user);
        studentProfileDTO.setStudent(student);
        return studentProfileDTO;
    }
}
