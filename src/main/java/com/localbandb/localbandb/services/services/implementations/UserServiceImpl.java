package com.localbandb.localbandb.services.services.implementations;

import com.localbandb.localbandb.data.models.User;
import com.localbandb.localbandb.data.repositories.UserRepository;
import com.localbandb.localbandb.services.models.UserServiceModel;
import com.localbandb.localbandb.services.services.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
  public boolean save(UserServiceModel user) {
    if (!user.getPassword().equals(user.getConfirmPassword())) {
      return false;
    }
    try {
      user.setPassword(DigestUtils.sha256Hex(user.getPassword()));
      userRepository.save(modelMapper.map(user, User.class));
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
}
