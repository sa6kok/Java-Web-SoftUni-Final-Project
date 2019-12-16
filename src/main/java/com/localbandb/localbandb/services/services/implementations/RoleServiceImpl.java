package com.localbandb.localbandb.services.services.implementations;

import com.localbandb.localbandb.data.models.Role;
import com.localbandb.localbandb.data.repositories.RoleRepository;
import com.localbandb.localbandb.services.models.RoleServiceModel;
import com.localbandb.localbandb.services.services.RoleService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
  private final RoleRepository roleRepository;

  public RoleServiceImpl(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  @Override
  @Secured({"ROLE_ADMIN", "ROLE_GUEST","ROLE_HOST"})
  public Role findByAuthority(String authority) {
    return roleRepository.findByAuthority(authority);
  }


  @Override
  @PreAuthorize("permitAll")
  public Set<Role> findAll() {
    return new HashSet<>(roleRepository.findAll());
  }
}
