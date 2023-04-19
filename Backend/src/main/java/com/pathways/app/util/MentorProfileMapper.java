package com.pathways.app.util;

import com.pathways.app.dto.MentorProfileDTO;
import com.pathways.app.dto.StudentProfileDTO;
import com.pathways.app.model.Mentor;
import com.pathways.app.model.Student;
import com.pathways.app.model.User;
import com.pathways.app.payload.UpdateMentorProfileRequest;
import com.pathways.app.payload.UpdateStudentProfileRequest;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Date;

public class MentorProfileMapper {


    public static MentorProfileDTO toDTO(UpdateMentorProfileRequest mentorUser, Mentor mentor, User user) {
        MentorProfileDTO mentorProfileDTO = new MentorProfileDTO();

        // Update User Fields
        if (mentorUser.getEmail() != null) user.setEmail(mentorUser.getEmail());
        if (mentorUser.getOldPassword() != null && mentorUser.getNewPassword() != null) {
            String hashedPassword = BCrypt.hashpw(mentorUser.getNewPassword(), BCrypt.gensalt());
            user.setPassword(hashedPassword);
        }

        // Update Mentor Fields
        if (mentorUser.getName() != null) mentor.setName(mentorUser.getName());
        if (mentorUser.getPhone() != null) mentor.setPhone(mentorUser.getPhone());
        if (mentorUser.getGender() != null) mentor.setGender(mentorUser.getGender());
        if (mentorUser.getDepartment() != null) mentor.setDepartment(mentorUser.getDepartment());
        if (mentorUser.getAcademicDegree() != null) mentor.setAcademicDegree(mentorUser.getAcademicDegree());
        if (mentorUser.getOffice() != null) mentor.setOffice(mentorUser.getOffice());
        if (mentorUser.getOfficeHours() != null) mentor.setOfficeHours(mentorUser.getOfficeHours());
        if (mentorUser.getFacultyStatus() != null) mentor.setFacultyStatus(mentorUser.getFacultyStatus());
        if (mentorUser.getInterests() != null) mentor.setInterests(mentorUser.getInterests());
        if (mentorUser.getDescription() != null) mentor.setDescription(mentorUser.getDescription());
        if (mentorUser.getProfilePicture() != null) mentor.setProfilePicture(mentorUser.getProfilePicture());
        mentor.setLastModified(new Date()); // Set last modified date to today.

        mentorProfileDTO.setUser(user);
        mentorProfileDTO.setMentor(mentor);
        return mentorProfileDTO;
    }
}
