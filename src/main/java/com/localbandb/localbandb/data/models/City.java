package com.localbandb.localbandb.data.models;



import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;


@Entity
@Table(name = "cities")
public class City extends BaseEntity{
  @NotEmpty
  @Size(min = 3, max = 25)
  @Column(name = "name")
  private String name;

  @ManyToOne(targetEntity = Country.class, cascade = CascadeType.ALL)
  @JoinColumn(name = "country_id", referencedColumnName = "id")
  private Country country;

  @OneToMany(targetEntity = Property.class, mappedBy = "city", cascade = CascadeType.ALL)
  private List<Property> properties;

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

  public List<Property> getProperties() {
    return properties;
  }

  public void setProperties(List<Property> properties) {
    this.properties = properties;
  }
}
