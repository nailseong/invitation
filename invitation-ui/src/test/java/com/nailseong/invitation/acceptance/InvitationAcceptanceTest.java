package com.nailseong.invitation.acceptance;

import static io.restassured.http.Method.POST;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

import com.nailseong.invitation.invitation.dto.CreateInvitationRequest;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("초대장 인수 테스트")
class InvitationAcceptanceTest extends AcceptanceTest {

    @Nested
    @DisplayName("초대장 생성 기능이")
    class Create {

        private static final String URL = "/api/invitations";
        private static final String USERNAME = "ilseong";

        @Test
        @DisplayName("성공한다.")
        void success() {
            // given
            signup(USERNAME);
            final String sessionId = login(USERNAME);
            final var request = "";

            // when
            final var response = url(URL)
                    .body(request)
                    .method(POST)
                    .send(sessionId);

            // then
            response.statusCode(CREATED.value())
                    .header(LOCATION, "/api/invitations/1");
        }

        @Nested
        @DisplayName("실패하는 경우는")
        class Exception {

            @Test
            @DisplayName("최대 사용 횟수가 1미만인 경우이다.")
            void invalidMaxUses() {
                // given
                signup(USERNAME);
                final String sessionId = login(USERNAME);
                final Long channelId = createChannel(sessionId);

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
            @DisplayName("만료 기간이 현재보다 과건인 경우이다.")
            void invalidExpireAfter() {
                // given
                signup(USERNAME);
                final String sessionId = login(USERNAME);
                final Long channelId = createChannel(sessionId);

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
                signup(USERNAME);
                final String sessionId = login(USERNAME);
                final Long channelId = createChannel(sessionId);

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
