package com.nailseong.invitation.invitation.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record CreateInvitationRequest(
        @NotNull
        Long channelId,

        LocalDateTime expireAfter,

        int maxUses
) {
}
