package org.delivery.api.exception;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;

@Component
public class RequestLoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // ⭐️ 필수 수정: 요청을 래핑된 객체로 받음
        ContentCachingRequestWrapper wrappedRequest =
                new ContentCachingRequestWrapper((HttpServletRequest) request);

        // ⭐️ 필수 수정: 래핑된 요청을 다음 체인으로 전달
        chain.doFilter(wrappedRequest, response);

        // 2. 오류 발생 후에도 캐시된 본문을 읽음
        if ("POST".equalsIgnoreCase(wrappedRequest.getMethod()) ||
                "PUT".equalsIgnoreCase(wrappedRequest.getMethod())) {

            // 캐시된 본문 데이터를 String으로 변환
            byte[] content = wrappedRequest.getContentAsByteArray();
            String requestBody = new String(content, wrappedRequest.getCharacterEncoding());

            // ⭐️ 콘솔에 요청 본문 전체를 출력
            System.out.println("--- Request Body Captured ---");
            System.out.println("Method: " + wrappedRequest.getMethod());
            System.out.println("Body: " + requestBody);
            System.out.println("-----------------------------");
        }
    }
}