package com.nailseong.invitation.channel.application.dto;

public record ChannelListResponse(
        Long channelId,
        int maxPeople,
        int numberOfPeople,
        boolean isHost
) {
}
