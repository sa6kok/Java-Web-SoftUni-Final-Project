package com.localbandb.localbandb.services.services;

import com.localbandb.localbandb.data.models.Role;
import com.localbandb.localbandb.services.models.RoleServiceModel;

import java.util.Set;

public interface RoleService {
  Role findByAuthority(String authority);

  Set<Role> getListOfRoles(Set<RoleServiceModel> authorities);

  Set<Role> findAll();
}
