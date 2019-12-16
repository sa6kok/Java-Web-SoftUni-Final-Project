package com.localbandb.localbandb.services.services.implementations;

import com.localbandb.localbandb.config.authentication.AuthenticationFacade;
import com.localbandb.localbandb.data.models.Reservation;
import com.localbandb.localbandb.data.models.Role;
import com.localbandb.localbandb.data.models.User;
import com.localbandb.localbandb.data.repositories.UserRepository;
import com.localbandb.localbandb.services.models.UserCheckServiceModel;
import com.localbandb.localbandb.services.models.UserServiceModel;
import com.localbandb.localbandb.services.services.RoleService;
import com.localbandb.localbandb.services.services.UserService;
import com.localbandb.localbandb.web.view.models.UserViewModel;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final RoleService roleService;
  private final AuthenticationFacade facade;
  private final ModelMapper modelMapper;
  private final BCryptPasswordEncoder encoder;

  @Autowired
  public UserServiceImpl(UserRepository userRepository, RoleService roleService, AuthenticationFacade facade, ModelMapper modelMapper, BCryptPasswordEncoder encoder) {
    this.userRepository = userRepository;
    this.roleService = roleService;
    this.facade = facade;
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
        switch (userServiceModel.getRole()) {
          case "GUEST":
            guest.setReservations(new ArrayList<>());
            rolesToSave.add(roleService.findByAuthority("ROLE_GUEST"));
            break;
          case "HOST":
            guest.setProperties(new ArrayList<>());
            rolesToSave.add(roleService.findByAuthority("ROLE_HOST"));
            break;
        }
        guest.setPayments(new ArrayList<>());
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
  @Secured("ROLE_GUEST")
  public void addUserToReservation(Reservation reservation) throws NotFoundException {
    User user = this.findByUsername(facade.getAuthentication().getName());
    reservation.setGuest(user);
  }

  @Override
  @Secured({"ROLE_ADMIN", "ROLE_GUEST","ROLE_HOST"})
  public User findById(String id) throws NotFoundException {
    User user = userRepository.findById(id).orElseThrow(()-> new NotFoundException("Username not found!"));
    return user;
  }

  @Override
  @Secured("ROLE_ADMIN")
  public List<UserViewModel> findAllUsersWithoutTheLoggedIn() {
    String username = this.facade.getAuthentication().getName();
    return userRepository.findAllByUsernameNot(username).stream()
            .map(u -> {
              UserViewModel userViewModel = modelMapper.map(u, UserViewModel.class);
              if(u.isEnabled()) {
                userViewModel.setActive(true);
              }
              return userViewModel;
            }).collect(Collectors.toList());
  }

  @Override
  @Secured("ROLE_ADMIN")
  public boolean changeUserStatus(String id) {

    try {
      User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
      if(user.isEnabled()) {
        user.setEnabled(false);
      } else if (!user.isEnabled()) {
        user.setEnabled(true);
      }
      userRepository.saveAndFlush(user);
      return true;
    } catch (Exception ex) {
      return false;
    }

  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return this.userRepository
        .findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
  }
}
