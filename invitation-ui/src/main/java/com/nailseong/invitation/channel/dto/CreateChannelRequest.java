package com.nailseong.invitation.channel.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

public record CreateChannelRequest(
        @NotEmpty
        String nickname,

        @Positive
        int maxPeople
) {
}
