package com.nailseong.invitation.invitation.dto;

import jakarta.validation.constraints.NotEmpty;

public record JoinByInvitationRequest(
        @NotEmpty
        String nickname
) {
}
