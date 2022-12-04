package com.nailseong.invitation.member;

import com.nailseong.invitation.member.application.MemberService;
import com.nailseong.invitation.member.dto.SignupRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(final MemberService memberService) {
        this.memberService = memberService;
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping
    public void signup(@RequestBody @Valid final SignupRequest request) {
        memberService.signup(request.username());
    }
}
