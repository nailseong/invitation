package com.nailseong.invitation.channel.domain;

import com.nailseong.invitation.channel.exception.ChannelNotFoundException;
import com.nailseong.invitation.channel.infrastructure.ChannelEntityRepository;
import com.nailseong.invitation.channel.infrastructure.ChannelMemberRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ChannelRepository {

    private final ChannelEntityRepository channelRepo;
    private final ChannelMemberRepository channelMemberRepo;

    public ChannelRepository(final ChannelEntityRepository channelRepo,
                             final ChannelMemberRepository channelMemberRepo) {
        this.channelRepo = channelRepo;
        this.channelMemberRepo = channelMemberRepo;
    }

    public Channel getById(final Long channelId) {
        final Channel channel = channelRepo.findById(channelId)
                .orElseThrow(ChannelNotFoundException::new);
        final List<ChannelMember> channelMembers = channelMemberRepo.findAllByChannelId(channelId);
        channel.setChannelMembers(channelMembers);
        return channel;
    }

    public Channel saveChannel(final Channel channel) {
        final Channel savedChannel = channelRepo.save(channel);
        savedChannel.getChannelMembers()
                .forEach(it -> persistMember(savedChannel.getId(), it));
        return savedChannel;
    }

    private void persistMember(final Long channelId, final ChannelMember channelMember) {
        channelMember.setChannelId(channelId);
        saveMember(channelMember);
    }

    public ChannelMember saveMember(final ChannelMember channelMember) {
        return channelMemberRepo.save(channelMember);
    }

    public List<Channel> findAllByMemberId(final Long memberId) {
        final List<Long> channelIds = channelMemberRepo.findAllByMemberId(memberId)
                .stream()
                .map(ChannelMember::getChannelId)
                .toList();
        return channelRepo.findAllById(channelIds);
    }
}
