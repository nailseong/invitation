package com.nailseong.invitation.channel.application;

import com.nailseong.invitation.channel.domain.Channel;
import com.nailseong.invitation.channel.domain.ChannelMember;
import com.nailseong.invitation.channel.domain.ChannelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ChannelService {

    private final ChannelRepository channelRepository;

    public ChannelService(final ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    public Long createChannel(final Long memberId, final String nickname, final int maxPeople) {
        final ChannelMember host = ChannelMember.ofHost(memberId, nickname);
        final Channel channel = Channel.ofNew(memberId, maxPeople, host);
        return channelRepository.saveChannel(channel)
                .getId();
    }
}
