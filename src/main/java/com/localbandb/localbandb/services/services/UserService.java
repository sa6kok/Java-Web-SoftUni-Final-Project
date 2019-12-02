package com.localbandb.localbandb.services.services;

import com.localbandb.localbandb.services.models.UserCheckServiceModel;
import com.localbandb.localbandb.services.models.UserServiceModel;

public interface UserService {

  boolean save(UserServiceModel user);

  boolean login(String username, String password);

  UserCheckServiceModel checkIfUserExist(String username);

  UserCheckServiceModel checkIfUserWithEmailExist(String email);
}
