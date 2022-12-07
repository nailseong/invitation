package com.nailseong.invitation.invitation;

import com.nailseong.invitation.authentication.support.LoginSession;
import com.nailseong.invitation.authentication.support.Verified;
import com.nailseong.invitation.invitation.application.InvitationService;
import com.nailseong.invitation.invitation.application.dto.InvitationInfo;
import com.nailseong.invitation.invitation.dto.CreateInvitationRequest;
import com.nailseong.invitation.invitation.dto.CreateInvitationResponse;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public CreateInvitationResponse createInvitation(
            @RequestBody @Valid final CreateInvitationRequest request,
            @Verified final LoginSession loginSession) {
        final InvitationInfo invitationInfo = new InvitationInfo(
                request.channelId(),
                request.expireAfter(),
                LocalDateTime.now(),
                request.maxUses(),
                loginSession.memberId()
        );
        final String invitationCode = invitationService.createInvitation(invitationInfo);
        return new CreateInvitationResponse(HOST + invitationCode);
    }
}
