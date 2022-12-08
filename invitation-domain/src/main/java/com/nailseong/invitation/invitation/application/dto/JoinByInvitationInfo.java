package com.nailseong.invitation.invitation.application.dto;

import java.time.LocalDateTime;

public record JoinByInvitationInfo(
        String invitationCode,
        String nickname,
        Long memberId,
        LocalDateTime now) {
}
