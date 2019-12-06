package com.localbandb.localbandb.services.services.implementations;

import com.localbandb.localbandb.data.models.User;
import com.localbandb.localbandb.data.repositories.UserRepository;
import com.localbandb.localbandb.services.models.UserCheckServiceModel;
import com.localbandb.localbandb.services.models.UserServiceModel;
import com.localbandb.localbandb.services.services.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final ModelMapper modelMapper;

  @Autowired
  public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
    this.userRepository = userRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public boolean save(UserServiceModel userServiceModel) {
    if (!userServiceModel.getPassword().equals(userServiceModel.getConfirmPassword())) {
      return false;
    }
    try {
      userServiceModel.setPassword(DigestUtils.sha256Hex(userServiceModel.getPassword()));
      User user = modelMapper.map(userServiceModel, User.class);
      if (user.getReservations() == null) {
        user.setReservations(new ArrayList<>());
      }
      if (user.getPayments() == null) {
        user.setPayments(new ArrayList<>());
      }
      userRepository.save(user);
      return true;
    } catch (Exception ex) {
      return false;
    }
  }

  @Override
  public boolean login(String username, String password) {
    System.out.println();
    try {
      User user = userRepository.findByUsernameAndPassword(username, DigestUtils.sha256Hex(password));
      return user != null;
    } catch (Exception ex) {
      return false;
    }
  }

  @Override
  public UserCheckServiceModel checkIfUserExist(String username) {
    UserCheckServiceModel user = new UserCheckServiceModel();
    User byUsername = userRepository.findByUsername(username);
    if(byUsername != null) {
      user = modelMapper.map(byUsername, UserCheckServiceModel.class);
    }
    return user;
  }

  @Override
  public UserCheckServiceModel checkIfUserWithEmailExist(String email) {
    UserCheckServiceModel user = new UserCheckServiceModel();
    User byEmail = userRepository.findByEmail(email);
    if(byEmail != null) {
      user = modelMapper.map(byEmail, UserCheckServiceModel.class);
    }
    return user;
  }
}
