package org.delivery.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserRole {
    GUEST,
    USER,
    ADMIN,
    STORE_OWNER;
}
