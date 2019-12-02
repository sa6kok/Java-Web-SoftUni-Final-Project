package com.localbandb.localbandb.web.view.models;

import javax.validation.constraints.NotEmpty;

public class UserLoginModel {

  @NotEmpty(message = " should not be empty!")
  private String username;

  @NotEmpty(message = " should not be empty!")
  private String password;

  public UserLoginModel() {
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
