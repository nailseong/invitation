package com.nailseong.invitation.authentication.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("LoginSession")
public class LoginSession implements Serializable {

    @Id
    private final Long memberId;
    private final String username;
    private final LocalDateTime createdAt;

    public LoginSession(final Long memberId, final String username, final LocalDateTime createdAt) {
        this.memberId = memberId;
        this.username = username;
        this.createdAt = createdAt;
    }

    public Long getMemberId() {
        return memberId;
    }

    public String getUsername() {
        return username;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
