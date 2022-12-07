package com.nailseong.invitation.channel.application;

import com.nailseong.invitation.channel.domain.Channel;
import com.nailseong.invitation.channel.domain.ChannelRepository;
import com.nailseong.invitation.member.MemberRepository;
import com.nailseong.invitation.member.exception.MemberNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ChannelService {

    private final MemberRepository memberRepo;
    private final ChannelRepository channelRepo;

    public ChannelService(final MemberRepository memberRepo, final ChannelRepository channelRepo) {
        this.memberRepo = memberRepo;
        this.channelRepo = channelRepo;
    }

    public Long createChannel(final Long memberId, final String nickname, final int maxPeople) {
        memberRepo.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
        final Channel channel = channelRepo.save(Channel.ofNew(memberId, maxPeople));

        // TODO: 2022/12/07 채널에 방장 저장 

        return channel.getId();
    }
}
