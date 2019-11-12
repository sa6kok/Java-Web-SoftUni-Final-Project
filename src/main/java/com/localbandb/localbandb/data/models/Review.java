package com.localbandb.localbandb.data.models;



import javax.persistence.*;


@Entity
@Table(name = "reviews")
public class Review extends BaseEntity {

  @Column(name = "level")
  private Integer level;

  @Column(name = "description")
  private String description;

  @ManyToOne(targetEntity = Property.class)
  @JoinColumn(name = "property_id", referencedColumnName = "id")
  private Property property;

  public Review() {
  }

  public Integer getLevel() {
    return level;
  }

  public void setLevel(Integer level) {
    this.level = level;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Property getProperty() {
    return property;
  }

  public void setProperty(Property property) {
    this.property = property;
  }
}
