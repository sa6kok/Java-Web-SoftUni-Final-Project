package com.localbandb.localbandb.web.view.models;


import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserRegisterModel {

  @Size(min = 3, max = 25, message = "should be between 3 and 15 symbols!")
  private String username;

  @Size(min = 4, message = "should be at least 4 symbols!")
  private String password;

  @Size(min = 4, message = "should be at least 4 symbols!")
  private String confirmPassword;

  @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
      , message = " is not valid!")
  private String email;

  @Size(min = 2, max = 15, message = "should be between 2 and 15 symbols!")
  private String firstName;

  @Size(min = 2, max = 15, message = "should be between 2 and 15 symbols!")
  private String lastName;

  @Min(value = 18, message = " should be at least 18 years old!")
  private Integer age;


  public UserRegisterModel() {
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getConfirmPassword() {
    return confirmPassword;
  }

  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }
}
