package com.nailseong.invitation.channel.infrastructure;

import com.nailseong.invitation.channel.domain.ChannelMember;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelMemberRepository extends JpaRepository<ChannelMember, Long> {

    List<ChannelMember> findAllByChannelId(final Long channelId);

    List<ChannelMember> findAllByMemberId(final Long memberId);
}
