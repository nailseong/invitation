package com.nailseong.invitation.invitation.application;

import com.nailseong.invitation.channel.domain.Channel;
import com.nailseong.invitation.channel.domain.ChannelRepository;
import com.nailseong.invitation.channel.exception.ChannelNotFoundException;
import com.nailseong.invitation.channel.exception.NotHostException;
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
    private final ChannelRepository channelRepository;

    public InvitationService(final InvitationRepository invitationRepo,
                             final ChannelRepository channelRepository) {
        this.invitationRepo = invitationRepo;
        this.channelRepository = channelRepository;
    }

    public String createInvitation(final InvitationInfo invitationInfo) {
        // TODO: 2022/12/08 사용자가 호스트인지 검증하는 로직을 분리한다.
        final Channel channel = channelRepository.findById(invitationInfo.channelId())
                .orElseThrow(ChannelNotFoundException::new);
        if (!channel.isHost(invitationInfo.hostId())) {
            throw new NotHostException();
        }

        String code;
        do {
            code = RandomStringGenerator.get(Invitation.CODE_LENGTH);
        } while (invitationRepo.findByCode(code).isPresent());

        final Invitation invitation = invitationRepo.save(new Invitation(
                invitationInfo.channelId(),
                invitationInfo.expireAfter(),
                invitationInfo.now(),
                invitationInfo.maxUses(),
                code
        ));
        return invitation.getCode();
    }
}
