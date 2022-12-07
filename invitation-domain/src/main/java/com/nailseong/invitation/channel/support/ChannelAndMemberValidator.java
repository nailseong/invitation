package com.nailseong.invitation.channel.support;

import com.nailseong.invitation.channel.domain.Channel;
import com.nailseong.invitation.channel.domain.ChannelRepository;
import com.nailseong.invitation.channel.exception.ChannelNotFoundException;
import com.nailseong.invitation.channel.exception.NotHostException;
import java.util.Arrays;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ChannelAndMemberValidator {

    private final ChannelRepository channelRepo;

    public ChannelAndMemberValidator(final ChannelRepository channelRepo) {
        this.channelRepo = channelRepo;
    }

    @Before("@annotation(com.nailseong.invitation.channel.support.HostOnly)")
    public void validate(final JoinPoint joinPoint) {
        final ChannelAndMember channelAndMember = getChannelAndMember(joinPoint);
        final Channel channel = channelRepo.findById(channelAndMember.channelId())
                .orElseThrow(ChannelNotFoundException::new);
        if (!channel.isHost(channelAndMember.memberId())) {
            throw new NotHostException();
        }
    }

    private ChannelAndMember getChannelAndMember(final JoinPoint joinPoint) {
        return Arrays.stream(joinPoint.getArgs())
                .filter(this::isTarget)
                .map(this::toTarget)
                .findFirst()
                .orElseThrow();
    }

    private boolean isTarget(final Object parameter) {
        return parameter instanceof ChannelAndMember;
    }

    private ChannelAndMember toTarget(final Object parameter) {
        return (ChannelAndMember) parameter;
    }
}
