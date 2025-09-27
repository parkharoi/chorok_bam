package org.delivery.api.sequrity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.delivery.user.domain.User;
import org.delivery.user.domain.UserStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String authorityName = "ROLE_" + user.getRole().name();

        return Collections.singleton(new SimpleGrantedAuthority(authorityName));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    // 아래 4개 메소드는 계정의 상태를 나타냅니다.
    // User 엔티티의 status 필드와 연동하여 실제 비즈니스 로직을 구현할 수 있습니다.
    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정 만료 여부 (true: 만료 안됨)
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정 잠김 여부 (true: 잠기지 않음)
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 비밀번호 만료 여부 (true: 만료 안됨)
    }

    @Override
    public boolean isEnabled() {
        return user.getStatus() == UserStatus.REGISTERED;
    }
}