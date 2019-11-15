package com.localbandb.localbandb.services.services.implementations;

import com.localbandb.localbandb.data.models.Address;
import com.localbandb.localbandb.data.repositories.AddressRepository;
import com.localbandb.localbandb.services.services.AddressService;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {
  private final AddressRepository addressRepository;

  public AddressServiceImpl(AddressRepository addressRepository) {
    this.addressRepository = addressRepository;
  }

  @Override
  public Address save(Address address) {
    return addressRepository.save(address);
  }
}
