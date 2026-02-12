package org.delivery.api.config; // api 모듈 내의 config 패키지에 추가

import org.delivery.api.exception.RequestLoggingFilter; // 경로에 맞게 수정
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import jakarta.servlet.Filter;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<Filter> loggingFilter() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new RequestLoggingFilter());

        // 모든 URL에 적용
        registrationBean.addUrlPatterns("/*");

        // ⭐️ 가장 중요: 필터 순서를 1번으로 지정 (최우선 실행)
        registrationBean.setOrder(1);

        return registrationBean;
    }
}