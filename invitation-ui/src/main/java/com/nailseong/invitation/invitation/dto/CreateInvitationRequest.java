package com.nailseong.invitation.invitation.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;

public record CreateInvitationRequest(
        @NotNull
        Long channelId,

        @Future
        LocalDateTime expireAfter,

        @Positive
        int maxUses
) {
}
