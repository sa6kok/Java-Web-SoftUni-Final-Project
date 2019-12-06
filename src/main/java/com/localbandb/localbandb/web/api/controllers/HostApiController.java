package com.localbandb.localbandb.web.api.controllers;

import com.localbandb.localbandb.services.services.HostService;
import com.localbandb.localbandb.web.api.models.HostCheckModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HostApiController {
  private final HostService hostService;
  private final ModelMapper mapper;

  @Autowired
  public HostApiController(HostService hostService, ModelMapper mapper) {
    this.hostService = hostService;
    this.mapper = mapper;
  }


  @GetMapping("host/register/username/{username}")
  public HostCheckModel checkIfUserWithUsernameExist(@PathVariable String username) {
    return this.mapper.map( hostService.findUserWithUsername(username), HostCheckModel.class);
  }

  @GetMapping("host/register/email/{email}")
  public HostCheckModel checkIfUserWithEmailExist(@PathVariable String email) {
    return this.mapper.map( hostService.findUserWithEmail(email), HostCheckModel.class);
  }
}
