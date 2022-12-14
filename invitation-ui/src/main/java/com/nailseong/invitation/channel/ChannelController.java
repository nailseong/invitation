package com.nailseong.invitation.channel;

import com.nailseong.invitation.authentication.support.LoginSession;
import com.nailseong.invitation.authentication.support.Verified;
import com.nailseong.invitation.channel.application.ChannelQueryService;
import com.nailseong.invitation.channel.application.ChannelService;
import com.nailseong.invitation.channel.application.dto.ChannelDetailResponse;
import com.nailseong.invitation.channel.application.dto.ChannelListResponse;
import com.nailseong.invitation.channel.dto.CreateChannelRequest;
import com.nailseong.invitation.channel.support.ChannelAndMember;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/channels")
public class ChannelController {

    private final ChannelService channelService;
    private final ChannelQueryService channelQueryService;

    public ChannelController(final ChannelService channelService,
                             final ChannelQueryService channelQueryService) {
        this.channelService = channelService;
        this.channelQueryService = channelQueryService;
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

    @GetMapping
    public List<ChannelListResponse> getList(@Verified final LoginSession loginSession) {
        return channelQueryService.getList(loginSession.memberId());
    }

    @GetMapping("/{channelId}")
    public ChannelDetailResponse getDetail(@PathVariable final Long channelId,
                                           @Verified final LoginSession loginSession) {
        final ChannelAndMember channelAndMember = new ChannelAndMember(channelId, loginSession.memberId());
        return channelQueryService.getDetail(channelAndMember);
    }
}
