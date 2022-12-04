package com.nailseong.invitation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({
        "com.nailseong.invitation.member",
        "com.nailseong.invitation.member.application"
})
public class InvitationApplication {

    public static void main(final String[] args) {
        SpringApplication.run(InvitationApplication.class, args);
    }
}
