package com.nailseong.invitation.channel;

import com.nailseong.invitation.authentication.support.Verified;
import com.nailseong.invitation.authentication.support.LoginSession;
import com.nailseong.invitation.channel.application.ChannelService;
import com.nailseong.invitation.channel.dto.CreateChannelRequest;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/channels")
public class ChannelController {

    private final ChannelService channelService;

    public ChannelController(final ChannelService channelService) {
        this.channelService = channelService;
    }

    @PostMapping
    public ResponseEntity<Void> createChannel(@RequestBody @Valid final CreateChannelRequest request,
                                              @Verified final LoginSession loginSession) {
        final Long channelId = channelService.createChannel(
                loginSession.memberId(),
                request.nickname(),
                request.maxPeople()
        );
        return ResponseEntity.created(URI.create("/api/channels/" + channelId))
                .build();
    }
}
