package org.delivery.api.controller;

import org.delivery.order.domain.service.CartService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
public class CartController {

  private final CartService cartService;



}
