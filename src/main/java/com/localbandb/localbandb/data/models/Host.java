package com.localbandb.localbandb.data.models;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;


@Table(name = "hosts")
public class Host extends BaseEntity {

  @NotEmpty
  @Size(min = 3, max = 25)
  @Column(name = "username", nullable = false, unique = true)
  private String username;

  @Size(min = 3, max = 25)
  @Email
  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @NotEmpty
  @Column(name = "password" , nullable = false)
  private String password;

  @NotEmpty
  @Size(min = 3, max = 25)
  @Column(name = "first_name", nullable = false)
  private String firstName;

  @NotEmpty
  @Size(min = 3, max = 25)
  @Column(name = "last_name", nullable = false)
  private String lastName;

  @OneToMany(targetEntity = Property.class, mappedBy = "host", cascade = CascadeType.ALL)
  private List<Property> properties;

  @OneToMany(targetEntity = Payment.class, mappedBy = "host", cascade = CascadeType.ALL)
  private List<Payment> payments;

  public Host() {
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

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
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

  public List<Property> getProperties() {
    return properties;
  }

  public void setProperties(List<Property> properties) {
    this.properties = properties;
  }

  public List<Payment> getPayments() {
    return payments;
  }

  public void setPayments(List<Payment> payments) {
    this.payments = payments;
  }
}
