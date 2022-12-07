package com.nailseong.invitation.invitation.application.dto;

import java.time.LocalDateTime;

public record InvitationInfo(
        Long channelId,
        LocalDateTime expireAfter,
        int maxUses,
        Long hostId
) {
}
