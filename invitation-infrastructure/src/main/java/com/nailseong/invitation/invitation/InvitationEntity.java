package com.nailseong.invitation.invitation;

import com.nailseong.invitation.config.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "invitation")
public class InvitationEntity extends BaseEntity {

    private static final int INITIAL_NUMBER_OF_USES = 0;

    @Column(nullable = false)
    private Long channelId;

    @Column(columnDefinition = "DATETIME(6)", nullable = false)
    private LocalDateTime expireAfter;

    @Column(nullable = false)
    private int maxUses;

    @Column(nullable = false)
    private int numberOfUses;

    protected InvitationEntity() {
    }

    private InvitationEntity(final Long channelId, final LocalDateTime expireAfter, final int maxUses,
                             final int numberOfUses) {
        this.channelId = channelId;
        this.expireAfter = expireAfter;
        this.maxUses = maxUses;
        this.numberOfUses = numberOfUses;
    }

    public static InvitationEntity of(final Long channelId, final LocalDateTime expireAfter, final int maxUses) {
        return new InvitationEntity(
                channelId,
                expireAfter,
                maxUses,
                INITIAL_NUMBER_OF_USES
        );
    }

    public Long getChannelId() {
        return channelId;
    }

    public LocalDateTime getExpireAfter() {
        return expireAfter;
    }

    public int getMaxUses() {
        return maxUses;
    }

    public int getNumberOfUses() {
        return numberOfUses;
    }
}
