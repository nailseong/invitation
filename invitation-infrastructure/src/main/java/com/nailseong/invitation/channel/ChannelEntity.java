package com.nailseong.invitation.channel;

import com.nailseong.invitation.config.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "channel")
public class ChannelEntity extends BaseEntity {

    private static final int INITIAL_NUMBER_OF_PEOPLE = 1;

    @Column(nullable = false)
    private Long hostId;

    @Column(nullable = false)
    private int maxPeople;

    @Column(nullable = false)
    private int numberOfPeople;

    protected ChannelEntity() {
    }

    private ChannelEntity(final Long hostId, final int maxPeople, final int numberOfPeople) {
        this.hostId = hostId;
        this.maxPeople = maxPeople;
        this.numberOfPeople = numberOfPeople;
    }

    public static ChannelEntity ofNew(final Long hostId, final int maxPeople) {
        return new ChannelEntity(
                hostId,
                maxPeople,
                INITIAL_NUMBER_OF_PEOPLE
        );
    }

    public Long getHostId() {
        return hostId;
    }

    public int getMaxPeople() {
        return maxPeople;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }
}
