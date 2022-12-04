package com.nailseong.invitation.channel.application;

import com.nailseong.invitation.channel.ChannelRepository;
import com.nailseong.invitation.channel.exception.InvalidMaxPeopleException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ChannelService {

    private final ChannelRepository channelRepo;

    public ChannelService(final ChannelRepository channelRepo) {
        this.channelRepo = channelRepo;
    }

    public Long createChannel(final Long memberId, final String nickname, final int maxPeople) {
        throw new InvalidMaxPeopleException();
    }
}
