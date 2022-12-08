package com.nailseong.invitation.channel.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelMemberRepository extends JpaRepository<ChannelMember, Long> {

    List<ChannelMember> findAllByChannelId(final Long channelId);
}
