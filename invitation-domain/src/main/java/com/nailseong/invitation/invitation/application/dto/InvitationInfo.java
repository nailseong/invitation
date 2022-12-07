package com.nailseong.invitation.invitation.application.dto;

import java.time.LocalDateTime;

public record InvitationInfo(
        Long channelId,
        LocalDateTime expireAfter,
        LocalDateTime now,
        int maxUses,
        Long hostId
) {
}
