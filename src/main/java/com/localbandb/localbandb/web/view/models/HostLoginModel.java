package com.localbandb.localbandb.web.view.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class HostLoginModel {
  private final static String ERROR_MESSAGE =  " should be at least 3 symbols!";

  @Size(min = 3, message = ERROR_MESSAGE)
  private String username;

  @Size(min = 3, message = ERROR_MESSAGE)
  private String password;

  public HostLoginModel() {
  }

  public HostLoginModel(@NotEmpty(message = ERROR_MESSAGE) String username, @NotEmpty(message = ERROR_MESSAGE) String password) {
    this.username = username;
    this.password = password;
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
