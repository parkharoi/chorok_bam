package org.delivery.api.dto;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ApiErrorResponse {
    private final LocalDateTime timestemp = LocalDateTime.now();
    private final int status;
    private final String error;
    private final String message;
    private final String path;
}
