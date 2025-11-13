package org.delivery.order.domain.service;

import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.delivery.common.exception.CustomException;
import org.delivery.common.exception.ErrorCode;
import org.delivery.order.domain.dto.CartRequestDto;
import org.delivery.order.domain.entity.Cart;
import org.delivery.order.domain.repository.CartRepository;
import org.delivery.product.domain.entity.Product;
import org.delivery.product.domain.repository.ProductRepository;
import org.delivery.user.domain.User;
import org.delivery.user.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
  private final UserRepository userRepository;
  public final ProductRepository productRepository;
  public final CartRepository cartRepository;

  public Long addCart(Long userId, Long productId, Long quantity) {

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    var product = productRepository.findById(productId)
        .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

    Cart cart = cartRepository.findByUser(user)
        .orElseGet(() -> {
          Cart newCart = new Cart();
          newCart.setUser(user);
          return cartRepository.save(newCart);
        });

    cart.addItem(product, quantity);

    Cart savedCart = cartRepository.save(cart);

    return savedCart.getId();
  }

  public void removeFromCart(Long userId, Long productId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

    Cart cart = cartRepository.findByUser(user)
        .orElseThrow(() -> new CustomException(ErrorCode.CART_NOT_FOUND));

    cart.removeItem(product);
  }

}
