package org.delivery.order.domain.repository;

import java.util.List;
import java.util.Optional;
import org.delivery.order.domain.entity.Cart;
import org.delivery.order.domain.entity.CartItem;
import org.delivery.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

  Optional<Cart> findByUser(User user);

}
