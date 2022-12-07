package com.nailseong.invitation.channel.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateChannelRequest(
        @NotNull
        Long memberId,

        @NotEmpty
        String nickname,

        @Positive
        int maxPeople
) {
}
