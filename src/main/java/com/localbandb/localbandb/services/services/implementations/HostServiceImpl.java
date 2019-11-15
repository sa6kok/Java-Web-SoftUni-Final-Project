package com.localbandb.localbandb.services.services.implementations;

import com.localbandb.localbandb.data.models.Host;
import com.localbandb.localbandb.data.repositories.HostRepository;
import com.localbandb.localbandb.services.models.HostServiceModel;
import com.localbandb.localbandb.services.services.HostService;
import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HostServiceImpl implements HostService {
  private final HostRepository hostRepository;
  private final ModelMapper mapper;

  @Autowired
  public HostServiceImpl(HostRepository hostRepository, ModelMapper mapper) {
    this.hostRepository = hostRepository;
    this.mapper = mapper;
  }

  @Override
  public boolean save(HostServiceModel model) {
    if(!model.getPassword().equals(model.getConfirmPassword())) {
      return false;
    }
    try {
      model.setPassword(DigestUtils.sha256Hex(model.getPassword()));
      Host host = hostRepository.save(mapper.map(model, Host.class));
      return host != null;
    } catch (Exception e) {
      return false;
    }
  }

  @Override
  public boolean login(String username, String password) {
    System.out.println();
    try {
      hostRepository.findByUsernameAndPassword(username, DigestUtils.sha256Hex(password));
      return true;
    } catch (Exception ex) {
      return false;
    }
  }


}
