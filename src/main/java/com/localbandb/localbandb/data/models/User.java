package com.localbandb.localbandb.data.models;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;


@Entity
@Table(name = "users")
public class User extends BaseEntity {

  @NotEmpty
  @Size(min = 3, max = 25)
  @Column(name = "username", unique = true, nullable = false)
  private String username;

  @Size(min = 4)
  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "email", nullable = false, unique = true)
  @Email
  private String email;

  @NotEmpty
  @Size(min = 2, max = 15)
  @Column(name = "first_name", nullable = false)
  private String firstName;

  @NotEmpty
  @Size(min = 2, max = 15)
  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Min(18)
  @Column(name = "age", nullable = false)
  private String age;

  @OneToMany(targetEntity = Reservation.class, mappedBy = "user", cascade = CascadeType.ALL)
  private List<Reservation> reservations;

  @OneToMany(targetEntity = Payment.class, mappedBy = "user", cascade = CascadeType.ALL)
  private List<Payment> payments;


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

  public List<Reservation> getReservations() {
    return reservations;
  }

  public void setReservations(List<Reservation> reservations) {
    this.reservations = reservations;
  }

  public List<Payment> getPayments() {
    return payments;
  }

  public void setPayments(List<Payment> payments) {
    this.payments = payments;
  }
}
