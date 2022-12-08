package com.nailseong.invitation.channel.application;

import com.nailseong.invitation.channel.domain.Channel;
import com.nailseong.invitation.channel.domain.ChannelMember;
import com.nailseong.invitation.channel.domain.ChannelRepository;
import com.nailseong.invitation.invitation.domain.InvitationUsedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class InvitationUsedEventHandler {

    private final ChannelRepository channelRepo;

    public InvitationUsedEventHandler(final ChannelRepository channelRepo) {
        this.channelRepo = channelRepo;
    }

    @EventListener(InvitationUsedEvent.class)
    public void handle(final InvitationUsedEvent event) {
        final Channel channel = channelRepo.getById(event.getChannelId());
        channel.join(event.getGuestId(), event.getNickname());

        channelRepo.saveMember(ChannelMember.ofGuest(
                event.getChannelId(),
                event.getGuestId(),
                event.getNickname()
        ));
    }
}
