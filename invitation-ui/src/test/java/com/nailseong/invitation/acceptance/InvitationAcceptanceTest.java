package com.nailseong.invitation.acceptance;

import static io.restassured.http.Method.POST;
import static org.hamcrest.Matchers.hasLength;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import com.nailseong.invitation.channel.exception.DuplicateNicknameException;
import com.nailseong.invitation.invitation.dto.CreateInvitationRequest;
import com.nailseong.invitation.invitation.dto.UseInvitationRequest;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("초대장 인수 테스트")
class InvitationAcceptanceTest extends AcceptanceTest {

    private static final String USERNAME = "ilseong";

    private String sessionId;
    private Long channelId;

    @Override
    @BeforeEach
    void setUp() {
        super.setUp();
        signup(USERNAME);
        sessionId = login(USERNAME);
        channelId = createChannel(sessionId);
    }

    @Nested
    @DisplayName("초대장 생성 기능이")
    class Create {

        private static final String URL = "/api/invitations";
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

    @Nested
    @DisplayName("초대장 사용하기 기능이")
    class Use {

        private static final String URL_PREFIX = "/api/invitations/";

        private String guestSessionId;

        @BeforeEach
        void setUp() {
            signup("rick");
            guestSessionId = login("rick");
        }

        @Test
        @DisplayName("성공한다.")
        void success() {
            // given
            final String invitationCode = createInvitation(sessionId, channelId, LocalDateTime.now().plusDays(1));
            final var request = new UseInvitationRequest("nailseong");

            // when
            final var response = url(URL_PREFIX + invitationCode)
                    .body(request)
                    .method(POST)
                    .send(guestSessionId);

            // then
            response.statusCode(NO_CONTENT.value());
        }

        @Nested
        @DisplayName("실패하는 경우는")
        class Exception {

            @Test
            @DisplayName("초대장에 해당하는 채널이 존재하지 않는 경우이다.")
            void notExistChannel() {
                // given
                final var request = new UseInvitationRequest("nailseong");

                // when
                final var response = url(URL_PREFIX + "x0x0X0")
                        .body(request)
                        .method(POST)
                        .send(guestSessionId);

                // then
                response.statusCode(BAD_REQUEST.value());
            }

            @Test
            @DisplayName("만료 기간이 지난 경우이다.")
            void expireAfter() throws InterruptedException {
                // given
                final LocalDateTime expireAfter = LocalDateTime.now().plusSeconds(1L);
                final String invitationCode = createInvitation(sessionId, channelId, expireAfter);
                final var request = new UseInvitationRequest("nailseong");

                Thread.sleep(1100);

                // when
                final var response = url(URL_PREFIX + invitationCode)
                        .body(request)
                        .method(POST)
                        .send(guestSessionId);

                // then
                response.statusCode(BAD_REQUEST.value());
            }

            @Test
            @DisplayName("사용 가능 횟수가 남아있지 않는 경우이다.")
            void leftUses() {
                // given
                final LocalDateTime expireAfter = LocalDateTime.now().plusDays(1L);
                final String invitationCode = createInvitation(sessionId, channelId, expireAfter);
                final var request = new UseInvitationRequest("nailseong");

                useInvitation(guestSessionId, invitationCode);

                // when
                final var response = url(URL_PREFIX + invitationCode)
                        .body(request)
                        .method(POST)
                        .send(guestSessionId);

                // then
                response.statusCode(BAD_REQUEST.value());
            }

            @Test
            @DisplayName("이미 가입한 채널인 경우이다.")
            void alreadyJoin() {
                // given
                final LocalDateTime expireAfter = LocalDateTime.now().plusDays(1L);
                final String invitationCode = createInvitation(sessionId, channelId, expireAfter, 2);
                final var request = new UseInvitationRequest("nailseong");

                useInvitation(guestSessionId, invitationCode);

                // when
                final var response = url(URL_PREFIX + invitationCode)
                        .body(request)
                        .method(POST)
                        .send(guestSessionId);

                // then
                response.statusCode(BAD_REQUEST.value());
            }

            @Test
            @DisplayName("닉네임이 중복되는 경우이다.")
            void nickname() {
                // given
                final LocalDateTime expireAfter = LocalDateTime.now().plusDays(1L);
                final String invitationCode = createInvitation(sessionId, channelId, expireAfter, 2);
                final var request = new UseInvitationRequest("nailseong");

                useInvitation(guestSessionId, invitationCode);

                signup("rick2");
                final String requestSessionId = login("rick2");

                // when
                final var response = url(URL_PREFIX + invitationCode)
                        .body(request)
                        .method(POST)
                        .send(requestSessionId);

                // then
                response.statusCode(BAD_REQUEST.value())
                        .body("message", is(DuplicateNicknameException.MESSAGE));
            }
        }
    }
}
