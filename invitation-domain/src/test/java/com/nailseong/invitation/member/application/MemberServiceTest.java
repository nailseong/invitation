package com.nailseong.invitation.member.application;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.nailseong.member.MemberEntity;
import com.nailseong.member.MemberRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class MemberServiceTest {

    private static final String USERNAME = "ilseong";
    private static final MemberEntity MEMBER_ENTITY = new MemberEntity(USERNAME);

    @Mock
    private MemberRepository memberRepo;

    private MemberService memberService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        memberService = new MemberService(memberRepo);
    }

    @Test
    @DisplayName("회원가입 성공")
    void signup() {
        given(memberRepo.findByUsername(USERNAME))
                .willReturn(Optional.empty());

        memberService.signup(USERNAME);

        verify(memberRepo, times(1)).findByUsername(eq(USERNAME));
        verify(memberRepo, times(1)).save(eq(MEMBER_ENTITY));
    }

    @Test
    @DisplayName("회원가입 실패 - 사용자 이름 중복")
    void signup_duplicate_username() {
        given(memberRepo.findByUsername(USERNAME))
                .willReturn(Optional.of(MEMBER_ENTITY));

        assertThatThrownBy(() -> memberService.signup(USERNAME))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 사용중인 사용자 이름입니다.");
    }
}
