package org.delivery.product.domain.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.delivery.user.domain.BaseEntity;

@Entity
@Getter
@Setter
@ToString(exclude = {"cart", "product"})
@Table(name = "cart_item")
public class CartItem extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cart_id")
  private Cart cart;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id")
  private Product product;

  private int count;

  public static CartItem createCartItem(Cart cart, Product product, int count ) {
    CartItem cartItem = new CartItem();
    cartItem.setCart(cart);
    cartItem.setProduct(product);
    cartItem.setCount(count);
    return cartItem;
  }

  public void addCount(int count) {
    this.count += count;
  }

}
