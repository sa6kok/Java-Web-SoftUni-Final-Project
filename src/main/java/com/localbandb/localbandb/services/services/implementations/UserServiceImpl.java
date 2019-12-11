package com.localbandb.localbandb.services.services.implementations;

import com.localbandb.localbandb.data.models.Role;
import com.localbandb.localbandb.data.models.User;
import com.localbandb.localbandb.data.repositories.UserRepository;
import com.localbandb.localbandb.services.models.UserCheckServiceModel;
import com.localbandb.localbandb.services.models.UserServiceModel;
import com.localbandb.localbandb.services.services.RoleService;
import com.localbandb.localbandb.services.services.UserService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final RoleService roleService;
  private final ModelMapper modelMapper;
  private final BCryptPasswordEncoder encoder;

  @Autowired
  public UserServiceImpl(UserRepository userRepository, RoleService roleService, ModelMapper modelMapper, BCryptPasswordEncoder encoder) {
    this.userRepository = userRepository;
    this.roleService = roleService;
    this.modelMapper = modelMapper;
    this.encoder = encoder;
  }

  @Override
  @PreAuthorize("isAnonymous()")
  public boolean saveUser(UserServiceModel userServiceModel) {
    if (!userServiceModel.getPassword().equals(userServiceModel.getConfirmPassword())) {
      return false;
    }
    try {
      userServiceModel.setPassword(encoder.encode(userServiceModel.getPassword()));
      User guest = modelMapper.map(userServiceModel, User.class);
      Set<Role> rolesToSave = new HashSet<>();

      if(userRepository.count() == 0) {
         rolesToSave = roleService.findAll();
      } else {
        rolesToSave.add(roleService.findByAuthority(userServiceModel.getRole()));
        guest.setAuthorities(rolesToSave);
        guest.setPayments(new ArrayList<>());

        switch (userServiceModel.getRole()) {
          case "GUEST":
            guest.setReservations(new ArrayList<>());
            break;
          case "HOST":
            guest.setProperties(new ArrayList<>());
            break;
        }
      }


      guest.setAuthorities(rolesToSave);
      userRepository.saveAndFlush(guest);
      return true;
    } catch (Exception ex) {
      return false;
    }
  }

  @Override
  public User findByUsername(String username) throws NotFoundException {
    return userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("Username not found!"));
  }


  @Override
  @PreAuthorize("isAnonymous()")
  public UserCheckServiceModel checkIfUserExist(String username) {
    UserCheckServiceModel user = new UserCheckServiceModel();
    Optional<User> byUsername = userRepository.findByUsername(username);
    if (byUsername.isPresent()) {
      user = modelMapper.map(byUsername.get(), UserCheckServiceModel.class);
    }
    return user;
  }

  @Override
  @PreAuthorize("isAnonymous()")
  public UserCheckServiceModel checkIfUserWithEmailExist(String email) {
    UserCheckServiceModel user = new UserCheckServiceModel();
    User byEmail = userRepository.findByEmail(email);
    if (byEmail != null) {
      user = modelMapper.map(byEmail, UserCheckServiceModel.class);
    }
    return user;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User guest = this.userRepository
        .findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
    return guest;
  }
}
