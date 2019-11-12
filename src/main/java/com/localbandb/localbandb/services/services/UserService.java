package com.localbandb.localbandb.services.services;

import com.localbandb.localbandb.services.models.auth.UserRegisterModel;
import com.localbandb.localbandb.services.models.service.UserServiceModel;

public interface UserService {
  boolean save(UserServiceModel user);
}
