package com.localbandb.localbandb.web.api.controllers;

import com.localbandb.localbandb.services.models.UserCheckServiceModel;
import com.localbandb.localbandb.services.models.UserServiceModel;
import com.localbandb.localbandb.services.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserApiController {
  private final UserService userService;

  @Autowired
  public UserApiController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/user/api/register/{username}")
  @PreAuthorize("isAnonymous()")
  public UserCheckServiceModel checkIfUSerExist(@PathVariable String username) {
    return userService.checkIfUserExist(username);
  }

  @GetMapping("/user/api/registerWithEmail/{email}")
  @PreAuthorize("isAnonymous()")
  public UserCheckServiceModel checkIfEmailExist(@PathVariable String email) {
    return userService.checkIfUserWithEmailExist(email);
  }
}
