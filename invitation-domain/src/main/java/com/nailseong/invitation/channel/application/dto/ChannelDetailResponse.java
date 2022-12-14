package com.nailseong.invitation.channel.application.dto;

import com.nailseong.invitation.channel.domain.Channel;
import com.nailseong.invitation.channel.domain.ChannelMember;
import com.nailseong.invitation.channel.domain.MemberRole;
import java.util.List;

public record ChannelDetailResponse(
        Long channelId,
        int maxPeople,
        int numberOfPeople,
        List<ChannelMemberResponse> channelMembers
) {

    public static ChannelDetailResponse fromEntity(final Channel channel) {
        final List<ChannelMemberResponse> channelMembers = channel.getChannelMembers()
                .stream()
                .map(ChannelMemberResponse::fromEntity)
                .toList();
        return new ChannelDetailResponse(
                channel.getId(),
                channel.getMaxPeople(),
                channel.getNumberOfPeople(),
                channelMembers
        );
    }

    private record ChannelMemberResponse(
            Long memberId,
            MemberRole role,
            String nickname
    ) {

        private static ChannelMemberResponse fromEntity(final ChannelMember channelMember) {
            return new ChannelMemberResponse(
                    channelMember.getMemberId(),
                    channelMember.getRole(),
                    channelMember.getNickname()
            );
        }
    }
}
