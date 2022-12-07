package com.nailseong.invitation.member.dto;

import jakarta.validation.constraints.NotEmpty;

public record LoginRequest(
        @NotEmpty
        String username
) {
}
