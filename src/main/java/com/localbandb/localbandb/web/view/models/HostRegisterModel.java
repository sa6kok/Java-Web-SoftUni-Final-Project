package com.localbandb.localbandb.web.view.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class HostRegisterModel {

  @Size(min = 3, max = 15, message = "should be between 3 and 15 symbols!")
  private String username;

  @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
      , message = " is not valid!")
  private String email;

  @Size(min = 4, message = "should be at least 4 symbols!")
  private String password;

  @Size(min = 4, message = "should be at least 4 symbols!")
  private String confirmPassword;

  @Size(min = 3, max = 15, message = "should be between 3 and 15 symbols!")
  private String firstName;

  @Size(min = 3, max = 15, message = "should be between 3 and 15 symbols!")
  private String lastName;
}
