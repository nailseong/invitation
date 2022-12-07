package com.nailseong.invitation.authentication.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

public record LoginSession(
        Long memberId,
        String username,
        LocalDateTime createdAt
) implements Serializable {
}
