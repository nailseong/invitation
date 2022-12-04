package com.nailseong.invitation.member.application;

import com.nailseong.invitation.member.MemberEntity;
import com.nailseong.invitation.member.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepo;

    public MemberService(final MemberRepository memberRepo) {
        this.memberRepo = memberRepo;
    }

    public void signup(final String username) {
        memberRepo.findByUsername(username)
                .ifPresent(it -> {
                    throw new IllegalArgumentException("이미 사용중인 사용자 이름입니다.");
                });
        final MemberEntity entity = new MemberEntity(username);
        memberRepo.save(entity);
    }
}
