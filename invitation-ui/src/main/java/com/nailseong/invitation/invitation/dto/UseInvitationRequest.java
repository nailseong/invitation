package com.nailseong.invitation.invitation.dto;

import jakarta.validation.constraints.NotEmpty;

public record UseInvitationRequest(
        @NotEmpty
        String nickname
) {
}
