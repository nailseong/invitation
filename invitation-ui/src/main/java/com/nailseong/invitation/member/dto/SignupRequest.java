package com.nailseong.invitation.member.dto;

import jakarta.validation.constraints.NotEmpty;

public record SignupRequest(
        @NotEmpty
        String username
) {
}
