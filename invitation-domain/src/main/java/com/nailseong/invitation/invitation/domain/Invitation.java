package com.nailseong.invitation.invitation.domain;

import com.nailseong.invitation.config.BaseEntity;
import com.nailseong.invitation.invitation.exception.InvalidCodeException;
import com.nailseong.invitation.invitation.exception.InvalidExpireAfterException;
import com.nailseong.invitation.invitation.exception.InvalidMaxUsesException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class Invitation extends BaseEntity {

    private static final int INITIAL_NUMBER_OF_USES = 0;
    private static final int MIN_MAX_USES = 1;
    public static final int CODE_LENGTH = 6;

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
                      final int maxUses, final String code) {
        this.channelId = channelId;
        setExpireAfter(expireAfter, now);
        setMaxUses(maxUses);
        this.numberOfUses = INITIAL_NUMBER_OF_USES;
        setCode(code);
    }

    public Long getChannelId() {
        return channelId;
    }

    public LocalDateTime getExpireAfter() {
        return expireAfter;
    }

    private void setExpireAfter(final LocalDateTime expireAfter, final LocalDateTime now) {
        if (expireAfter.isBefore(now)) {
            throw new InvalidExpireAfterException();
        }
        this.expireAfter = expireAfter;
    }

    public int getMaxUses() {
        return maxUses;
    }

    private void setMaxUses(final int maxUses) {
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

    private void setCode(final String code) {
        if (code.length() != CODE_LENGTH) {
            throw new InvalidCodeException();
        }
        this.code = code;
    }
}
