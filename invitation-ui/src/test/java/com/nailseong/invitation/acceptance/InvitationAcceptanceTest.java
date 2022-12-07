package com.nailseong.invitation.acceptance;

import static io.restassured.http.Method.POST;
import static org.hamcrest.Matchers.hasLength;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

import com.nailseong.invitation.invitation.dto.CreateInvitationRequest;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("초대장 인수 테스트")
class InvitationAcceptanceTest extends AcceptanceTest {

    @Nested
    @DisplayName("초대장 생성 기능이")
    class Create {

        private String sessionId;
        private Long channelId;

        @BeforeEach
        void setUp() {
            signup(USERNAME);
            sessionId = login(USERNAME);
            channelId = createChannel(sessionId);
        }

        private static final String URL = "/api/invitations";
        private static final String USERNAME = "ilseong";
        private static final String HOST = "https://invitation.nailseong.com/";

        @Test
        @DisplayName("성공한다.")
        void success() {
            // given
            final var request = new CreateInvitationRequest(channelId, LocalDateTime.now().plusDays(1L), 2);

            // when
            final var response = url(URL)
                    .body(request)
                    .method(POST)
                    .send(sessionId);

            // then
            response.statusCode(OK.value())
                    .body("invitationUrl", startsWith(HOST))
                    .body("invitationUrl", hasLength(HOST.length() + 6));
        }

        @Nested
        @DisplayName("실패하는 경우는")
        class Exception {

            @Test
            @DisplayName("최대 사용 횟수가 1미만인 경우이다.")
            void invalidMaxUses() {
                // given
                final var request = new CreateInvitationRequest(channelId, LocalDateTime.now().plusDays(1L), 0);

                // when
                final var response = url(URL)
                        .body(request)
                        .method(POST)
                        .send(sessionId);

                // then
                response.statusCode(BAD_REQUEST.value());
            }

            @Test
            @DisplayName("만료 기간이 현재보다 과거인 경우이다.")
            void invalidExpireAfter() {
                // given
                final var request = new CreateInvitationRequest(channelId, LocalDateTime.now().minusDays(1L), 2);

                // when
                final var response = url(URL)
                        .body(request)
                        .method(POST)
                        .send(sessionId);

                // then
                response.statusCode(BAD_REQUEST.value());
            }

            @Test
            @DisplayName("채널에 방장이 아닌 사용자가 요청한 경우이다.")
            void notHost() {
                // given
                final String guestUsername = "not-host";
                signup(guestUsername);
                final String guestSessionId = login(guestUsername);
                final var request = new CreateInvitationRequest(channelId, LocalDateTime.now().plusDays(1L), 2);

                // when
                final var response = url(URL)
                        .body(request)
                        .method(POST)
                        .send(guestSessionId);

                // then
                response.statusCode(BAD_REQUEST.value());
            }
        }
    }
}
