package com.nailseong.invitation.member.domain;

import com.nailseong.invitation.config.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Member extends BaseEntity {

    @Column(nullable = false)
    private String username;

    protected Member() {
    }

    public Member(final String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
