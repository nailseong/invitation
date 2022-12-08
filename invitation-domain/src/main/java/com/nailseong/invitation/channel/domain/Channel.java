package com.nailseong.invitation.channel.domain;

import com.nailseong.invitation.channel.exception.InvalidMaxPeopleException;
import com.nailseong.invitation.channel.exception.NoLeftPeopleException;
import com.nailseong.invitation.config.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Channel extends BaseEntity {

    private static final int INITIAL_NUMBER_OF_PEOPLE = 1;

    @Column(nullable = false)
    private Long hostId;

    @Column(nullable = false)
    private int maxPeople;

    @Column(nullable = false)
    private int numberOfPeople;

    protected Channel() {
    }

    private Channel(final Long hostId, final int maxPeople, final int numberOfPeople) {
        this.hostId = hostId;
        this.maxPeople = maxPeople;
        this.numberOfPeople = numberOfPeople;
    }

    public static Channel ofNew(final Long hostId, final int maxPeople) {
        if (maxPeople < 2) {
            throw new InvalidMaxPeopleException();
        }
        return new Channel(
                hostId,
                maxPeople,
                INITIAL_NUMBER_OF_PEOPLE
        );
    }

    public void join() {
        if (!hasLeftPeople()) {
            throw new NoLeftPeopleException();
        }
        numberOfPeople++;
    }

    private boolean hasLeftPeople() {
        return maxPeople > numberOfPeople;
    }

    public boolean isHost(final Long memberId) {
        return hostId.equals(memberId);
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
