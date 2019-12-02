package com.localbandb.localbandb.data.models;



import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;


@Entity
@Table(name = "reviews")
public class Review extends BaseEntity {

  @Min(0)
  @Max(10)
  @Column(name = "level")
  private Integer level;

  @Size(min = 15)
  @Column(name = "description", columnDefinition = "TEXT", nullable = false)
  private String description;

  @OneToOne(targetEntity = Reservation.class)
  @JoinColumn(name = "reservation_id", referencedColumnName = "id")
  private Reservation reservation;

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

  public Reservation getReservation() {
    return reservation;
  }

  public void setReservation(Reservation reservation) {
    this.reservation = reservation;
  }
}
