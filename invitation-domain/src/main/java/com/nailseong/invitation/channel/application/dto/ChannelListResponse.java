package com.nailseong.invitation.channel.application.dto;

import com.nailseong.invitation.channel.domain.Channel;

public record ChannelListResponse(
        Long channelId,
        int maxPeople,
        int numberOfPeople,
        boolean isHost
) {

    public static ChannelListResponse fromEntity(final Channel channel, final Long memberId) {
        return new ChannelListResponse(
                channel.getId(),
                channel.getMaxPeople(),
                channel.getNumberOfPeople(),
                channel.isHost(memberId)
        );
    }
}
