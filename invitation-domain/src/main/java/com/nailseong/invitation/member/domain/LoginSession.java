package com.nailseong.invitation.member.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("LoginSession")
public class LoginSession implements Serializable {

    @Id
    private final String username;
    private final LocalDateTime createdAt;

    public LoginSession(final String username, final LocalDateTime createdAt) {
        this.username = username;
        this.createdAt = createdAt;
    }

    public String getUsername() {
        return username;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
