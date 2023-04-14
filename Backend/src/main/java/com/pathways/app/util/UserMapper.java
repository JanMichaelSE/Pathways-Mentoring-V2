package com.pathways.app.util;

import com.pathways.app.dto.UserDTO;
import com.pathways.app.model.User;

public class UserMapper {
    public static UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());
        userDTO.setAccessToken("AccessToken123");
        userDTO.setRefreshToken("RefreshToken123");
        return userDTO;
    }
}
