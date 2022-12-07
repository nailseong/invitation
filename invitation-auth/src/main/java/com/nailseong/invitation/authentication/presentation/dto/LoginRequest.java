package com.nailseong.invitation.authentication.presentation.dto;

import jakarta.validation.constraints.NotEmpty;

public record LoginRequest(
        @NotEmpty
        String username
) {
}
