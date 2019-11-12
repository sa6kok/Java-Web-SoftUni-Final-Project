package com.localbandb.localbandb.data.models;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "reservations")
public class Reservation extends BaseEntity {
  @Column(name = "start_date")
  private Date startDate;

  @Column(name = "end_date")
  private Date endDate;

  @ManyToOne(targetEntity = Property.class)
  @JoinColumn(name = "property_id")
  private Property property;

  @OneToOne(targetEntity = Review.class, cascade = CascadeType.ALL)
  @JoinColumn(name = "review_id", referencedColumnName = "id")
  private Review review;

  @ManyToOne(targetEntity = User.class)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  @Column(name = "total_price")
  private BigDecimal totalPrice;

  public Reservation() {
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public Property getProperty() {
    return property;
  }

  public void setProperty(Property property) {
    this.property = property;
  }

  public Review getReview() {
    return review;
  }

  public void setReview(Review review) {
    this.review = review;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public BigDecimal getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(BigDecimal totalPrice) {
    this.totalPrice = totalPrice;
  }
}
