package com.localbandb.localbandb.services.services.implementations;

import com.localbandb.localbandb.data.models.Role;
import com.localbandb.localbandb.data.repositories.RoleRepository;
import com.localbandb.localbandb.services.models.RoleServiceModel;
import com.localbandb.localbandb.services.services.RoleService;
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
  public Role findByAuthority(String authority) {
    return roleRepository.findByAuthority(authority);
  }

  @Override
  public Set<Role> getListOfRoles(Set<RoleServiceModel> authorities) {
    return authorities.stream().map(a -> roleRepository.findByAuthority(a.getAuthority())).collect(Collectors.toSet());
  }

  @Override
  public Set<Role> findAll() {
    return new HashSet<>(roleRepository.findAll());
  }
}
