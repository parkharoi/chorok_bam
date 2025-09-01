package org.delivery.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserStatus {
    REGISTERED,
    UNREGISTERED,
    PENDING;
}
