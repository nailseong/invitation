package com.nailseong.invitation.channel.application;

import com.nailseong.invitation.channel.domain.ChannelMember;
import com.nailseong.invitation.channel.domain.ChannelMemberRepository;
import com.nailseong.invitation.channel.exception.AlreadyJoinException;
import com.nailseong.invitation.channel.exception.DuplicateNicknameException;
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
        // TODO: 2022/12/09 if not enough left people in channel throw exception 
        final List<ChannelMember> channelMembers = channelMemberRepo.findAllByChannelId(event.getChannelId());
        if (isJoinedGuest(channelMembers, event.getGuestId())) {
            throw new AlreadyJoinException();
        }

        if (isDuplicatedNickname(channelMembers, event.getNickname())) {
            throw new DuplicateNicknameException();
        }

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

    private boolean isDuplicatedNickname(final List<ChannelMember> channelMembers, final String nickname) {
        return channelMembers
                .stream()
                .anyMatch(it -> it.isSameNickname(nickname));
    }
}
