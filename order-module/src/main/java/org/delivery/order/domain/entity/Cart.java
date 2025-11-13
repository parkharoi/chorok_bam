package org.delivery.order.domain.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.NoArgsConstructor;
import org.delivery.product.domain.entity.Product;
import org.delivery.user.domain.User;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="cart")
@Getter
@Setter
@NoArgsConstructor
public class Cart {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name="user_id", nullable = false)
  private User user;

  @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CartItem> cartItems = new ArrayList<>();

  public void addItem(Product product, Long quantity) {
    for(CartItem item : cartItems) {
      if(item.getProduct().equals(product)) {
        item.addCount(quantity);
        return;;
      }
    }
    CartItem newItem = CartItem.createCartItem(this, product, quantity, product.getPrice());
    cartItems.add(newItem);
  }

  public void removeItem(Product product) {
    cartItems.removeIf(item -> item.getProduct().equals(product));
  }

  public Long getTotalPrice() {
    return cartItems.stream()
        .mapToLong(CartItem::getTotalPrice)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }




}
