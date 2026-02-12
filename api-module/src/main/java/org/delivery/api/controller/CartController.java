package org.delivery.api.controller;

import lombok.RequiredArgsConstructor;
import org.delivery.order.domain.service.CartService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

  private final CartService cartService;

}
