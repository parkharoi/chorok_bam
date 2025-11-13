package org.delivery.product.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.delivery.user.domain.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.delivery.user.domain.BaseEntity;

@Entity
@Table(name="cart")
@Getter
@Setter
@ToString
public class Cart extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private User user;

  public static Cart createCart(User user) {
    Cart cart = new Cart();
    cart.setUser(user);
    return cart;
  }

}
