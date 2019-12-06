package com.localbandb.localbandb.web.view.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class HostRegisterModel {
    public final static String SHOULD_BE_BETWEEN_3_15 = "should be between 3 and 15 symbols!";
  @Size(min = 3, max = 15, message = SHOULD_BE_BETWEEN_3_15)
  private String username;

  @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
      , message = " is not valid!")
  private String email;

  @Size(min = 4, message = "should be at least 4 symbols!")
  private String password;

  @Size(min = 4, message = "should be at least 4 symbols!")
  private String confirmPassword;

  @Size(min = 3, max = 15, message = SHOULD_BE_BETWEEN_3_15)
  private String firstName;

  @Size(min = 3, max = 15, message = SHOULD_BE_BETWEEN_3_15)
  private String lastName;

  public HostRegisterModel() {
  }

  public HostRegisterModel(@Size(min = 3, max = 15, message = SHOULD_BE_BETWEEN_3_15) String username, @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
      , message = " is not valid!") String email, @Size(min = 4, message = "should be at least 4 symbols!") String password, @Size(min = 4, message = "should be at least 4 symbols!") String confirmPassword, @Size(min = 3, max = 15, message = SHOULD_BE_BETWEEN_3_15) String firstName, @Size(min = 3, max = 15, message = SHOULD_BE_BETWEEN_3_15) String lastName) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.confirmPassword = confirmPassword;
    this.firstName = firstName;
    this.lastName = lastName;
  }
}
