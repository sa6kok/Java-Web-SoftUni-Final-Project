package com.localbandb.localbandb.services.services;

import com.localbandb.localbandb.data.models.Reservation;
import com.localbandb.localbandb.data.models.User;
import com.localbandb.localbandb.services.models.UserCheckServiceModel;
import com.localbandb.localbandb.services.models.UserServiceModel;
import javassist.NotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

  boolean saveUser(UserServiceModel user);

  User findByUsername(String username) throws NotFoundException;

  UserCheckServiceModel checkIfUserExist(String username);

  UserCheckServiceModel checkIfUserWithEmailExist(String email);

    void addUserToReservation(Reservation reservation) throws NotFoundException;

  User findById(String id);
}
