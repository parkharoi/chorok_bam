package org.delivery.order.domain.dto;

import lombok.Getter;

@Getter
public class CartRequestDto {
  private Long userId;
  private int count;

}
