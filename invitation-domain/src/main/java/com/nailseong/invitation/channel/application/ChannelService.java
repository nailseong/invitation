package com.nailseong.invitation.channel.application;

import com.nailseong.invitation.channel.domain.Channel;
import com.nailseong.invitation.channel.domain.ChannelMember;
import com.nailseong.invitation.channel.domain.ChannelMemberRepository;
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
    private final ChannelMemberRepository channelMemberRepo;

    public ChannelService(final MemberRepository memberRepo,
                          final ChannelRepository channelRepo,
                          final ChannelMemberRepository channelMemberRepo) {
        this.memberRepo = memberRepo;
        this.channelRepo = channelRepo;
        this.channelMemberRepo = channelMemberRepo;
    }

    public Long createChannel(final Long memberId, final String nickname, final int maxPeople) {
        memberRepo.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
        final Channel channel = channelRepo.save(Channel.ofNew(memberId, maxPeople));

        final ChannelMember host = ChannelMember.ofHost(channel.getId(), memberId, nickname);
        channelMemberRepo.save(host);

        return channel.getId();
    }
}
