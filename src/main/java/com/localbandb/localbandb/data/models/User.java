package com.localbandb.localbandb.data.models;


import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.List;


@Entity
@Table(name = "users")
public class User extends BaseEntity {

  @Column(name = "username", unique = true, nullable = false)
  private String username;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "email", nullable = false)
  @Email
  private String email;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(name = "age", nullable = false)
  private String age;

  @ManyToMany(targetEntity = Property.class)
  @JoinTable( name = "visitor_property",
      joinColumns = @JoinColumn(name = "user_id"),
  inverseJoinColumns = @JoinColumn(name = "property_id"))
  private List<Property> visitedProperties;


  public User() {
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

  public String getAge() {
    return age;
  }

  public void setAge(String age) {
    this.age = age;
  }

  public List<Property> getVisitedProperties() {
    return visitedProperties;
  }

  public void setVisitedProperties(List<Property> visitedProperties) {
    this.visitedProperties = visitedProperties;
  }
}
