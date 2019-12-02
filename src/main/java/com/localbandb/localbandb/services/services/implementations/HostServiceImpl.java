package com.localbandb.localbandb.services.services.implementations;

import com.localbandb.localbandb.data.models.Host;
import com.localbandb.localbandb.data.repositories.HostRepository;
import com.localbandb.localbandb.services.models.HostCheckServiceModel;
import com.localbandb.localbandb.services.models.HostServiceModel;
import com.localbandb.localbandb.services.services.HostService;
import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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
      Host host = mapper.map(model, Host.class);
      if(host.getProperties() == null) {
        host.setProperties(new ArrayList<>());
      }
      if(host.getPayments() == null) {
        host.setPayments(new ArrayList<>());
      }
      hostRepository.save(host);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public boolean login(String username, String password) {
    System.out.println();
    try {
      Host host = hostRepository.findByUsernameAndPassword(username, DigestUtils.sha256Hex(password));
      return host != null;
    } catch (Exception ex) {
      return false;
    }
  }

  @Override
  public HostCheckServiceModel findUserWithUsername(String username) {
    return this.mapper.map(hostRepository.findByUsername(username), HostCheckServiceModel.class);
  }

  @Override
  public HostCheckServiceModel findUserWitHEmail(String email) {
    return this.mapper.map(hostRepository.findByEmail(email), HostCheckServiceModel.class);
  }


}
