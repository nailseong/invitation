package com.nailseong.invitation.presentation.member.dto;

import jakarta.validation.constraints.NotEmpty;

public record SignupRequest(
        @NotEmpty
        String username
) {
}
