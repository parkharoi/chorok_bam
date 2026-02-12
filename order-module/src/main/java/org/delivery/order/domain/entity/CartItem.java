package org.delivery.order.domain.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.delivery.product.domain.entity.Product;
import org.delivery.user.domain.BaseEntity;

@Entity
@Getter
@Setter
@ToString(exclude = {"cart", "product"})
@Table(name = "cart_item")
public class CartItem extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="cart_id")
  private Cart cart;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id")
  private Product product;

  private Long quantity;

  private BigDecimal price;

  public static CartItem createCartItem(Cart cart, Product product, Long quantity, BigDecimal price ) {
    CartItem item = new CartItem();
    item.setCart(cart);
    item.setProduct(product);
    item.setQuantity(quantity);
    item.setPrice(price);
    return item;
  }

  public void addCount(Long count) {
    this.quantity += count;
  }

  public BigDecimal getTotalPrice(){
    return price.multiply(BigDecimal.valueOf(quantity)) ;
  }

}
