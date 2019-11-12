package com.localbandb.localbandb.services.services;

import com.localbandb.localbandb.data.models.User;
import com.localbandb.localbandb.services.models.binding.UserBindingModel;

public interface UserService {
  boolean save(UserBindingModel user);
}
