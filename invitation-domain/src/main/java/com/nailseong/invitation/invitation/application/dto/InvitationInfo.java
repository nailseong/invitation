package com.nailseong.invitation.invitation.application.dto;

import java.time.LocalDateTime;

public record InvitationInfo(
        LocalDateTime expireAfter,
        LocalDateTime now,
        int maxUses
) {
}
