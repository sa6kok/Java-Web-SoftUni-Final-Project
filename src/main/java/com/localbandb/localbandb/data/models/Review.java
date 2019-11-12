package com.localbandb.localbandb.data.models;



import javax.persistence.*;


@Entity
@Table(name = "reviews")
public class Review extends BaseEntity {

  @Column(name = "level")
  private Integer level;

  @Column(name = "description")
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
