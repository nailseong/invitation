package com.nailseong.invitation.channel.application;

import com.nailseong.invitation.channel.application.dto.ChannelDetailResponse;
import com.nailseong.invitation.channel.application.dto.ChannelListResponse;
import com.nailseong.invitation.channel.domain.Channel;
import com.nailseong.invitation.channel.domain.ChannelRepository;
import com.nailseong.invitation.channel.support.ChannelAndMember;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ChannelQueryService {

    private final ChannelRepository channelRepo;

    public ChannelQueryService(final ChannelRepository channelRepo) {
        this.channelRepo = channelRepo;
    }

    public List<ChannelListResponse> getList(final Long memberId) {
        return channelRepo.findAllByMemberId(memberId)
                .stream()
                .map(it -> ChannelListResponse.fromEntity(it, memberId))
                .toList();
    }

    // TODO: 2022/12/14 @ChannelMemberOnly annotation
    public ChannelDetailResponse getDetail(final ChannelAndMember channelAndMember) {
        final Channel channel = channelRepo.getById(channelAndMember.channelId());
        return ChannelDetailResponse.fromEntity(channel);
    }
}
