package com.localbandb.localbandb.data.models;



import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "cities")
public class City extends BaseEntity{
  @Column(name = "name")
  private String name;

  @Column(name = "country" )
  private String country;

  @OneToMany(targetEntity = Address.class, mappedBy = "id")
  private List<Address> addresses;

  public City() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public List<Address> getAddresses() {
    return addresses;
  }

  public void setAddresses(List<Address> addresses) {
    this.addresses = addresses;
  }
}
