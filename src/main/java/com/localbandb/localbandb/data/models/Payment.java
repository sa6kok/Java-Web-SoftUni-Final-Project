package com.localbandb.localbandb.data.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "payments")
public class Payment extends BaseEntity {

  @Column(name = "amount", nullable = false)
  private BigDecimal amount;

  @ManyToOne(targetEntity = User.class, cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  @ManyToOne(targetEntity = Host.class, cascade = CascadeType.ALL)
  @JoinColumn(name = "host_id", referencedColumnName = "id")
  private Host host;

  @Min(0)
  @Column(name = "payment_date", nullable = false)
  private LocalDate paymentDate;

  public Payment() {
  }


  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Host getHost() {
    return host;
  }

  public void setHost(Host host) {
    this.host = host;
  }


  public LocalDate getPaymentDate() {
    return paymentDate;
  }

  public void setPaymentDate(LocalDate paymentDate) {
    this.paymentDate = paymentDate;
  }
}
