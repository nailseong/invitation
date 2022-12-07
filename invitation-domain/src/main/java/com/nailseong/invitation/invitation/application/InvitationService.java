package com.nailseong.invitation.invitation.application;

import com.nailseong.invitation.channel.support.ChannelAndMember;
import com.nailseong.invitation.channel.support.HostOnly;
import com.nailseong.invitation.invitation.application.dto.InvitationInfo;
import com.nailseong.invitation.invitation.domain.Invitation;
import com.nailseong.invitation.invitation.domain.InvitationRepository;
import com.nailseong.invitation.util.RandomStringGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InvitationService {

    private final InvitationRepository invitationRepo;

    public InvitationService(final InvitationRepository invitationRepo) {
        this.invitationRepo = invitationRepo;
    }

    @HostOnly
    public String createInvitation(final InvitationInfo invitationInfo, final ChannelAndMember channelAndMember) {
        String code;
        do {
            code = RandomStringGenerator.get(Invitation.CODE_LENGTH);
        } while (invitationRepo.findByCode(code).isPresent());

        final Invitation invitation = invitationRepo.save(new Invitation(
                channelAndMember.channelId(),
                invitationInfo.expireAfter(),
                invitationInfo.now(),
                invitationInfo.maxUses(),
                code
        ));
        return invitation.getCode();
    }
}
