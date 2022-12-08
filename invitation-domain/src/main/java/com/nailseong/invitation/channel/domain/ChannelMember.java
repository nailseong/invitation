package com.nailseong.invitation.channel.domain;

import com.nailseong.invitation.config.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
public class ChannelMember extends BaseEntity {

    @Column(nullable = false)
    private Long channelId;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Column(nullable = false)
    private String nickname;

    protected ChannelMember() {
    }

    private ChannelMember(final Long channelId, final Long memberId, final MemberRole role, final String nickname) {
        this.channelId = channelId;
        this.memberId = memberId;
        this.role = role;
        this.nickname = nickname;
    }

    public static ChannelMember ofHost(final Long channelId, final Long hostId, final String nickname) {
        return new ChannelMember(
                channelId,
                hostId,
                MemberRole.HOST,
                nickname
        );
    }

    public static ChannelMember ofGuest(final Long channelId, final Long guestId, final String nickname) {
        return new ChannelMember(
                channelId,
                guestId,
                MemberRole.GUEST,
                nickname
        );
    }

    public boolean isSameMember(final Long memberId) {
        return this.memberId.equals(memberId);
    }

    public Long getChannelId() {
        return channelId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public MemberRole getRole() {
        return role;
    }

    public String getNickname() {
        return nickname;
    }
}
