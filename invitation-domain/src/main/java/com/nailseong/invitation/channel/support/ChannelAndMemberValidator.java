package com.nailseong.invitation.channel.support;

import com.nailseong.invitation.channel.domain.Channel;
import com.nailseong.invitation.channel.domain.ChannelRepository;
import com.nailseong.invitation.channel.exception.NotChannelMemberException;
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
    public void validateHost(final JoinPoint joinPoint) {
        final ChannelAndMember channelAndMember = getChannelAndMember(joinPoint);
        final Channel channel = channelRepo.getById(channelAndMember.channelId());
        if (!channel.isHost(channelAndMember.memberId())) {
            throw new NotHostException();
        }
    }

    @Before("@annotation(com.nailseong.invitation.channel.support.ChannelMemberOnly)")
    public void validateChannelMember(final JoinPoint joinPoint) {
        final ChannelAndMember channelAndMember = getChannelAndMember(joinPoint);
        final Channel channel = channelRepo.getById(channelAndMember.channelId());
        if (!channel.isChannelMember(channelAndMember.memberId())) {
            throw new NotChannelMemberException();
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
