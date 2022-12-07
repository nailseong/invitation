package com.nailseong.invitation.authentication.application;

import com.nailseong.invitation.authentication.domain.LoginSession;
import com.nailseong.invitation.authentication.domain.LoginSessionRepository;
import com.nailseong.invitation.member.domain.Member;
import com.nailseong.invitation.member.domain.MemberRepository;
import com.nailseong.invitation.member.exception.MemberNotFoundException;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final MemberRepository memberRepo;
    private final LoginSessionRepository loginSessionRepo;

    public AuthService(final MemberRepository memberRepo, final LoginSessionRepository loginSessionRepo) {
        this.memberRepo = memberRepo;
        this.loginSessionRepo = loginSessionRepo;
    }

    public void login(final String username) {
        final Member member = memberRepo.findByUsername(username)
                .orElseThrow(MemberNotFoundException::new);
        final LoginSession loginSession = new LoginSession(member.getId(), member.getUsername(), LocalDateTime.now());
        loginSessionRepo.save(loginSession);
    }
}
