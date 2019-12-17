package com.localbandb.localbandb.services.services.implementations;


import com.localbandb.localbandb.base.TestBase;
import com.localbandb.localbandb.config.authentication.AuthenticationFacade;
import com.localbandb.localbandb.data.models.Reservation;
import com.localbandb.localbandb.data.models.User;
import com.localbandb.localbandb.data.repositories.UserRepository;
import com.localbandb.localbandb.services.models.UserCheckServiceModel;
import com.localbandb.localbandb.services.services.RoleService;
import javassist.NotFoundException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class UserServiceImplTest extends TestBase {

    static User testUser;

    @MockBean
    UserRepository userRepository;

    @MockBean
    RoleService roleService;

    @MockBean
    AuthenticationFacade facade;

    @MockBean
    ModelMapper modelMapper;

    @Autowired
    UserServiceImpl userService;


    @BeforeClass
    public static void init() {
        testUser = new User();
        testUser.setId("id");
        testUser.setUsername("username");
        testUser.setPassword("password");
        testUser.setEmail("email");
    }

    @Test(expected = NotFoundException.class)
    public void findByUsername_whitWrongName_shouldThrow() throws NotFoundException {
        when(userRepository.findByUsername("wrong"))
                .thenReturn(Optional.empty());
        userService.findByUsername("wrong");
    }

    @Test(expected = NullPointerException.class)
    public void findByUsername_whitNullUser_shouldThrow() throws NotFoundException {
        when(userRepository.findByUsername("wrong"))
                .thenReturn(null);
        userService.findByUsername("wrong");
    }

    @Test
    public void findByUsername_whitCorrectName_shouldReturnUser() throws NotFoundException {
        when(userRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));
        User actual = userService.findByUsername(testUser.getUsername());
        assertEquals(actual.getUsername(), testUser.getUsername());
    }


    @Test
    public void checkIfUserExist_whenEverythingIsCorrect_shouldReturnUser() {
        UserCheckServiceModel userServiceModel = new UserCheckServiceModel();
        userServiceModel.setUsername(testUser.getUsername());
        when(userRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));
        when(modelMapper.map(testUser, UserCheckServiceModel.class)).thenReturn(userServiceModel);

        UserCheckServiceModel actual = userService.checkIfUserExist(testUser.getUsername());
        assertEquals(actual.getUsername(), testUser.getUsername());
    }

    @Test
    public void checkIfUserExist_whenNoUserInDB_shouldReturnEmptyUser() {
        UserCheckServiceModel userServiceModel = new UserCheckServiceModel();
        userServiceModel.setUsername(testUser.getUsername());
        when(userRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.empty());
        when(modelMapper.map(testUser, UserCheckServiceModel.class)).thenReturn(userServiceModel);
        UserCheckServiceModel actual = userService.checkIfUserExist(testUser.getUsername());
        assertNull(actual.getUsername());
    }

    @Test
    public void checkIfEmailForUserExist_whenEverythingIsCorrect_shouldReturnUser() {
        UserCheckServiceModel userServiceModel = new UserCheckServiceModel();
        userServiceModel.setUsername(testUser.getUsername());
        when(userRepository.findByEmail(testUser.getEmail())).thenReturn(testUser);
        when(modelMapper.map(testUser, UserCheckServiceModel.class)).thenReturn(userServiceModel);

        UserCheckServiceModel actual = userService.checkIfUserWithEmailExist(testUser.getEmail());
        assertEquals(actual.getUsername(), testUser.getUsername());
    }

    @Test
    public void checkIfEmailForUserExist_whenNoUserForEmail_shouldReturnEmptyUser() {
        UserCheckServiceModel userServiceModel = new UserCheckServiceModel();
        userServiceModel.setUsername(testUser.getUsername());
        when(userRepository.findByEmail(testUser.getEmail())).thenReturn(null);
        when(modelMapper.map(testUser, UserCheckServiceModel.class)).thenReturn(userServiceModel);

        UserCheckServiceModel actual = userService.checkIfUserWithEmailExist(testUser.getEmail());
        assertNull(actual.getUsername());
    }

    @Test(expected = NotFoundException.class)
    public void findById_whitWithBoUser_shouldReturnNull() throws NotFoundException {
        when(userRepository.findById(testUser.getId()))
                .thenReturn(Optional.empty());
        userService.findById(testUser.getId());
    }

    @Test
    public void loadUserByUsername_whenUserFound_shouldReturnSameUser() {
        when(userRepository.findByUsername(testUser.getUsername()))
                .thenReturn(Optional.of(testUser));
        UserDetails actual = userService.loadUserByUsername(testUser.getUsername());

        assertEquals(testUser.getUsername(), actual.getUsername());
        assertEquals(testUser.getPassword(), actual.getPassword());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsername_whenUserNotFound_shouldThrow() {
        when(userRepository.findByUsername(testUser.getUsername()))
                .thenReturn(Optional.empty());
        userService.loadUserByUsername(testUser.getUsername());
    }

    @Test
    public void changeUserStatus_withCorrectUserWithTrue_shouldReturnFalse() {
        User user = new User();
        user.setEnabled(true);
        when(userRepository.findById(testUser.getId()))
                .thenReturn(Optional.of(testUser));
         userService.changeUserStatus(testUser.getId());

        assertFalse(testUser.isEnabled());
    }
    @Test
    public void changeUserStatus_withCorrectUserWithFalse_shouldReturnTrue() {
        String id = "newId";
        User user = new User();
        user.setEnabled(false);
        user.setId(id);
        when(userRepository.findById(testUser.getId()))
                .thenReturn(Optional.of(user));
        userService.changeUserStatus(testUser.getId());
        assertTrue(user.isEnabled());
    }

}