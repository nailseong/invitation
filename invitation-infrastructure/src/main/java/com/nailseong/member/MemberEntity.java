package com.nailseong.member;

import com.nailseong.config.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "member")
public class MemberEntity extends BaseEntity {

    @Column(nullable = false)
    private String username;

    protected MemberEntity() {
    }

    public MemberEntity(final String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
