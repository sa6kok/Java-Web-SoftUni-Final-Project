package com.localbandb.localbandb.services.services;

import com.localbandb.localbandb.data.models.Reservation;
import com.localbandb.localbandb.data.models.User;
import com.localbandb.localbandb.services.models.UserCheckServiceModel;
import com.localbandb.localbandb.services.models.UserServiceModel;
import com.localbandb.localbandb.web.view.models.UserRegisterModel;
import com.localbandb.localbandb.web.view.models.UserViewModel;
import javassist.NotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

  boolean saveUser(UserRegisterModel userRegisterModel, String role);

  User findByUsername(String username) throws NotFoundException;

  UserCheckServiceModel checkIfUserExist(String username);

  UserCheckServiceModel checkIfUserWithEmailExist(String email);

    void addUserToReservation(Reservation reservation) throws NotFoundException;

  User findById(String id) throws NotFoundException;

  List<UserViewModel> findAllUsersWithoutTheLoggedIn();

  boolean changeUserStatus(String id) throws NotFoundException;
}
