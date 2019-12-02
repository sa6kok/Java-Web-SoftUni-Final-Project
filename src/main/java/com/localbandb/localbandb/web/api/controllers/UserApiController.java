package com.localbandb.localbandb.web.api.controllers;

import com.localbandb.localbandb.services.models.UserCheckServiceModel;
import com.localbandb.localbandb.services.models.UserServiceModel;
import com.localbandb.localbandb.services.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserApiController {
 private final  UserService userService;

 @Autowired
  public UserApiController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("user/register/{username}")
  public UserCheckServiceModel checkIfUSerExist(@PathVariable String username) {


    UserCheckServiceModel user = userService.checkIfUserExist(username);
    return user;
  }

  @GetMapping("user/registerWithEmail/{email}")
  public UserCheckServiceModel checkIfEmailExist(@PathVariable String email) {


    UserCheckServiceModel user = userService.checkIfUserWithEmailExist(email);
    return user;
  }
}
