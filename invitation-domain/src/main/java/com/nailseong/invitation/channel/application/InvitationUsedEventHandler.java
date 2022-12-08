package com.nailseong.invitation.channel.application;

import com.nailseong.invitation.channel.domain.ChannelMember;
import com.nailseong.invitation.channel.domain.ChannelMemberRepository;
import com.nailseong.invitation.channel.exception.AlreadyJoinException;
import com.nailseong.invitation.invitation.domain.InvitationUsedEvent;
import java.util.List;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class InvitationUsedEventHandler {

    private final ChannelMemberRepository channelMemberRepo;

    public InvitationUsedEventHandler(final ChannelMemberRepository channelMemberRepo) {
        this.channelMemberRepo = channelMemberRepo;
    }

    @EventListener(InvitationUsedEvent.class)
    public void handle(final InvitationUsedEvent event) {
        final List<ChannelMember> channelMembers = channelMemberRepo.findAllByChannelId(event.getChannelId());
        if (isJoinedGuest(channelMembers, event.getGuestId())) {
            throw new AlreadyJoinException();
        }

        // TODO: 2022/12/09 닉네임 중복 검사

        final ChannelMember guest = ChannelMember.ofGuest(
                event.getChannelId(),
                event.getGuestId(),
                event.getNickname()
        );
        channelMemberRepo.save(guest);
    }

    private boolean isJoinedGuest(final List<ChannelMember> channelMembers, final Long guestId) {
        return channelMembers
                .stream()
                .anyMatch(it -> it.isSameMember(guestId));
    }
}
