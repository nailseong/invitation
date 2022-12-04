package com.nailseong.invitation;

import com.nailseong.invitation.presentation.member.dto.SignupRequest;
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

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping
    public void signup(@RequestBody @Valid final SignupRequest request) {
        // TODO: 2022/12/04 memberService 호출 
    }
}
