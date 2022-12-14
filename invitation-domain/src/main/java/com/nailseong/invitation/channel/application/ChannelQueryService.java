package com.nailseong.invitation.channel.application;

import com.nailseong.invitation.channel.application.dto.ChannelListResponse;
import com.nailseong.invitation.channel.domain.ChannelRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ChannelQueryService {

    private final ChannelRepository channelRepo;

    public ChannelQueryService(final ChannelRepository channelRepo) {
        this.channelRepo = channelRepo;
    }

    public List<ChannelListResponse> getList(final Long memberId) {
        return channelRepo.findAllByMemberId(memberId)
                .stream()
                .map(it -> ChannelListResponse.fromEntity(it, memberId))
                .toList();
    }
}
