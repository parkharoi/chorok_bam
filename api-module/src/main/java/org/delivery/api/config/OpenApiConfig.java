package org.delivery.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

  // API 전체 정보 설정
  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(new Info().title("통합 서비스 API 문서")
            .version("v1.0")
            .description("주요 모듈(Order, Product, User)별로 분류된 API 목록입니다."));
  }

  @Bean
  public GroupedOpenApi userApi() {
    return GroupedOpenApi.builder()
        .group("0. 사용자 관리")
        .pathsToMatch("/api/users/**")
        .build();
  }

  // 일반 사용자용
  @Bean
  public GroupedOpenApi publicProductApi() {
    return GroupedOpenApi.builder()
        .group("1. 상품 (Public)")
        .pathsToMatch("/api/products/**")
        .build();
  }

  // 상품 (관리자용)
  @Bean
  public GroupedOpenApi adminProductApi() {
    return GroupedOpenApi.builder()
        .group("2. 상품 (Admin)")
        .pathsToMatch("/api/admin/products/**")
        .build();
  }

}
