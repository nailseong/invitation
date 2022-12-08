package com.nailseong.invitation.channel.infrastructure;

import com.nailseong.invitation.channel.domain.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelEntityRepository extends JpaRepository<Channel, Long> {
}
