package com.nailseong.invitation.member.application;

import com.nailseong.invitation.member.domain.Member;
import com.nailseong.invitation.member.domain.MemberRepository;
import com.nailseong.invitation.member.domain.LoginSession;
import com.nailseong.invitation.member.domain.LoginSessionRepository;
import com.nailseong.invitation.member.exception.DuplicateUsernameException;
import com.nailseong.invitation.member.exception.MemberNotFoundException;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepo;
    private final LoginSessionRepository loginSessionRepo;

    public MemberService(final MemberRepository memberRepo, final LoginSessionRepository loginSessionRepo) {
        this.memberRepo = memberRepo;
        this.loginSessionRepo = loginSessionRepo;
    }

    public void signup(final String username) {
        memberRepo.findByUsername(username)
                .ifPresent(it -> {
                    throw new DuplicateUsernameException();
                });
        final Member entity = new Member(username);
        memberRepo.save(entity);
    }

    public void login(final String username) {
        memberRepo.findByUsername(username)
                .orElseThrow(MemberNotFoundException::new);
        final LoginSession loginSession = new LoginSession(username, LocalDateTime.now());
        loginSessionRepo.save(loginSession);
    }
}
