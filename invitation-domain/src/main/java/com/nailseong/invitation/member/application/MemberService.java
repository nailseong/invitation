package com.nailseong.invitation.member.application;

import com.nailseong.invitation.member.MemberEntity;
import com.nailseong.invitation.member.MemberRepository;
import com.nailseong.invitation.member.exception.DuplicateUsernameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepo;

    public MemberService(final MemberRepository memberRepo) {
        this.memberRepo = memberRepo;
    }

    public void signup(final String username) {
        memberRepo.findByUsername(username)
                .ifPresent(it -> {
                    throw new DuplicateUsernameException();
                });
        final MemberEntity entity = new MemberEntity(username);
        memberRepo.save(entity);
    }
}
