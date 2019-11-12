package com.localbandb.localbandb.data.models;



import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "cities")
public class City extends BaseEntity{
  @Column(name = "name")
  private String name;

  @ManyToOne(targetEntity = Country.class, cascade = CascadeType.ALL)
  @JoinColumn(name = "country_id", referencedColumnName = "id")
  private Country country;

  @OneToMany(targetEntity = Address.class, mappedBy = "city", cascade = CascadeType.ALL)
  private List<Address> addresses;

  public City() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Country getCountry() {
    return country;
  }

  public void setCountry(Country country) {
    this.country = country;
  }

  public List<Address> getAddresses() {
    return addresses;
  }

  public void setAddresses(List<Address> addresses) {
    this.addresses = addresses;
  }
}
