package com.nailseong.invitation.invitation.domain;

import com.nailseong.invitation.config.BaseEntity;
import com.nailseong.invitation.invitation.exception.InvalidExpireAfterException;
import com.nailseong.invitation.invitation.exception.InvalidMaxUsesException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class Invitation extends BaseEntity {

    private static final int INITIAL_NUMBER_OF_USES = 0;
    private static final int MIN_MAX_USES = 1;

    @Column(nullable = false)
    private Long channelId;

    @Column(columnDefinition = "DATETIME(6)", nullable = false)
    private LocalDateTime expireAfter;

    @Column(nullable = false)
    private int maxUses;

    @Column(nullable = false)
    private int numberOfUses;

    @Column(nullable = false)
    private String code;

    protected Invitation() {
    }

    public Invitation(final Long channelId, final LocalDateTime expireAfter, final LocalDateTime now,
                      final int maxUses) {
        this.channelId = channelId;
        setExpireAfter(expireAfter, now);
        setMaxUses(maxUses);
        this.numberOfUses = INITIAL_NUMBER_OF_USES;
        this.code = generateCode();
    }

    public String generateCode() {
        // TODO: 2022/12/08 숫자, 영어 대소문자로 구성된 6자리 문자열을 랜덤으로 생성한다.
        return "x";
    }

    public Long getChannelId() {
        return channelId;
    }

    public LocalDateTime getExpireAfter() {
        return expireAfter;
    }

    public void setExpireAfter(final LocalDateTime expireAfter, final LocalDateTime now) {
        if (expireAfter.isBefore(now)) {
            throw new InvalidExpireAfterException();
        }
        this.expireAfter = expireAfter;
    }

    public int getMaxUses() {
        return maxUses;
    }

    public void setMaxUses(final int maxUses) {
        if (maxUses < MIN_MAX_USES) {
            throw new InvalidMaxUsesException();
        }
        this.maxUses = maxUses;
    }

    public int getNumberOfUses() {
        return numberOfUses;
    }

    public String getCode() {
        return code;
    }
}
