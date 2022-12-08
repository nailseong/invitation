package com.nailseong.invitation.channel.application;

import com.nailseong.invitation.channel.domain.Channel;
import com.nailseong.invitation.channel.domain.ChannelMember;
import com.nailseong.invitation.channel.domain.ChannelRepository;
import com.nailseong.invitation.invitation.domain.InvitationUsedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InvitationUsedEventHandler {

    private final ChannelRepository channelRepo;

    public InvitationUsedEventHandler(final ChannelRepository channelRepo) {
        this.channelRepo = channelRepo;
    }

    @EventListener(InvitationUsedEvent.class)
    public void handle(final InvitationUsedEvent event) {
        final Channel channel = channelRepo.getById(event.getChannelId());
        final ChannelMember joinedGuest = channel.join(event.getGuestId(), event.getNickname());
        channelRepo.saveMember(joinedGuest);
    }
}
