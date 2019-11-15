package com.localbandb.localbandb.web.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HostRegisterModel {
  private String username;
  private String password;
  private String confirmPassword;
  private String firstName;
  private String lastName;
}
