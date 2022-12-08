package com.nailseong.invitation.channel.domain;

import com.nailseong.invitation.channel.exception.AlreadyJoinException;
import com.nailseong.invitation.channel.exception.DuplicateNicknameException;
import com.nailseong.invitation.channel.exception.InvalidMaxPeopleException;
import com.nailseong.invitation.channel.exception.NoLeftPeopleException;
import com.nailseong.invitation.config.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import java.util.List;

@Entity
public class Channel extends BaseEntity {

    private static final int INITIAL_NUMBER_OF_PEOPLE = 1;

    @Column(nullable = false)
    private Long hostId;

    @Column(nullable = false)
    private int maxPeople;

    @Column(nullable = false)
    private int numberOfPeople;

    @Transient
    private List<ChannelMember> channelMembers;

    protected Channel() {
    }

    public Channel(final Long hostId, final int maxPeople, final int numberOfPeople,
                   final List<ChannelMember> channelMembers) {
        this.hostId = hostId;
        this.maxPeople = maxPeople;
        this.numberOfPeople = numberOfPeople;
        this.channelMembers = channelMembers;
    }

    public static Channel ofNew(final Long hostId, final int maxPeople, final ChannelMember host) {
        if (maxPeople < 2) {
            throw new InvalidMaxPeopleException();
        }
        return new Channel(
                hostId,
                maxPeople,
                INITIAL_NUMBER_OF_PEOPLE,
                List.of(host)
        );
    }

    public ChannelMember join(final Long guestId, final String nickname) {
        if (!hasLeftPeople()) {
            throw new NoLeftPeopleException();
        }
        if (isJoinedGuest(guestId)) {
            throw new AlreadyJoinException();
        }
        if (isDuplicatedNickname(nickname)) {
            throw new DuplicateNicknameException();
        }
        numberOfPeople++;
        return ChannelMember.ofGuest(getId(), guestId, nickname);
    }

    private boolean hasLeftPeople() {
        return maxPeople > numberOfPeople;
    }

    private boolean isJoinedGuest(final Long guestId) {
        return channelMembers.stream()
                .anyMatch(it -> it.isSameMember(guestId));
    }

    private boolean isDuplicatedNickname(final String nickname) {
        return channelMembers.stream()
                .anyMatch(it -> it.isSameNickname(nickname));
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

    public List<ChannelMember> getChannelMembers() {
        return channelMembers;
    }

    void setChannelMembers(final List<ChannelMember> channelMembers) {
        this.channelMembers = channelMembers;
    }
}
