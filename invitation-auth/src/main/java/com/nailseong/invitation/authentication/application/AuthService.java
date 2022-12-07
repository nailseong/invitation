package com.nailseong.invitation.authentication.application;

import com.nailseong.invitation.authentication.support.LoginSession;
import com.nailseong.invitation.member.domain.Member;
import com.nailseong.invitation.member.domain.MemberRepository;
import com.nailseong.invitation.member.exception.MemberNotFoundException;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final MemberRepository memberRepo;

    public AuthService(final MemberRepository memberRepo) {
        this.memberRepo = memberRepo;
    }

    public LoginSession login(final String username) {
        final Member member = memberRepo.findByUsername(username)
                .orElseThrow(MemberNotFoundException::new);
        return new LoginSession(member.getId(), member.getUsername(), LocalDateTime.now());
    }
}
