package com.nailseong.member;

import com.nailseong.config.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "member")
public class MemberEntity extends BaseEntity {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private Long channelId;

    protected MemberEntity() {
    }

    private MemberEntity(final MemberRole role, final String nickname, final Long channelId) {
        this.role = role;
        this.nickname = nickname;
        this.channelId = channelId;
    }

    public static MemberEntity ofHost(final String nickname, final Long channelId) {
        return new MemberEntity(
                MemberRole.HOST,
                nickname,
                channelId
        );
    }

    public static MemberEntity ofGuest(final String nickname, final Long channelId) {
        return new MemberEntity(
                MemberRole.GUEST,
                nickname,
                channelId
        );
    }

    public MemberRole getRole() {
        return role;
    }

    public String getNickname() {
        return nickname;
    }

    public Long getChannelId() {
        return channelId;
    }
}
