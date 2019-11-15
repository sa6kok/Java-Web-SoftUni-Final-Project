package com.localbandb.localbandb.services.services;

import com.localbandb.localbandb.services.models.HostServiceModel;

public interface HostService {
  boolean save(HostServiceModel model);
  boolean login(String username, String password);
}
