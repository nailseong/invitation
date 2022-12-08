package com.nailseong.invitation.invitation;

import com.nailseong.invitation.authentication.support.LoginSession;
import com.nailseong.invitation.authentication.support.Verified;
import com.nailseong.invitation.channel.support.ChannelAndMember;
import com.nailseong.invitation.invitation.application.InvitationService;
import com.nailseong.invitation.invitation.application.dto.InvitationInfo;
import com.nailseong.invitation.invitation.application.dto.JoinByInvitationInfo;
import com.nailseong.invitation.invitation.dto.CreateInvitationRequest;
import com.nailseong.invitation.invitation.dto.CreateInvitationResponse;
import com.nailseong.invitation.invitation.dto.JoinByInvitationRequest;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/invitations")
public class InvitationController {

    private static final String HOST = "https://invitation.nailseong.com/";

    private final InvitationService invitationService;

    public InvitationController(final InvitationService invitationService) {
        this.invitationService = invitationService;
    }

    @PostMapping
    public CreateInvitationResponse createInvitation(@RequestBody @Valid final CreateInvitationRequest request,
                                                     @Verified final LoginSession loginSession) {
        final InvitationInfo invitationInfo = new InvitationInfo(
                request.expireAfter(),
                LocalDateTime.now(),
                request.maxUses()
        );
        final ChannelAndMember channelAndMember = new ChannelAndMember(request.channelId(), loginSession.memberId());
        final String invitationCode = invitationService.createInvitation(invitationInfo, channelAndMember);
        return new CreateInvitationResponse(HOST + invitationCode);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/{invitationCode}")
    public void joinByInvitation(@RequestBody @Valid final JoinByInvitationRequest request,
                                 @PathVariable final String invitationCode,
                                 @Verified final LoginSession loginSession) {
        final JoinByInvitationInfo joinByInvitationInfo = new JoinByInvitationInfo(
                invitationCode,
                request.nickname(),
                loginSession.memberId(),
                LocalDateTime.now()
        );
        invitationService.joinByInvitation(joinByInvitationInfo);
    }
}
