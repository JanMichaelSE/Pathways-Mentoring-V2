package com.pathways.app.service;

import com.pathways.app.model.User;
import com.pathways.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

   @Autowired
   private UserRepository userRepository;

  public List<User> findAll() {
      return userRepository.findAll();
  }

  public User save (User user) {
      return userRepository.save(user);
  }
}
