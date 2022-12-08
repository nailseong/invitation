package com.nailseong.invitation.acceptance;

import static io.restassured.http.Method.POST;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.nailseong.invitation.channel.dto.CreateChannelRequest;
import com.nailseong.invitation.channel.exception.InvalidMaxPeopleException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

@DisplayName("채널 인수 테스트")
class ChannelAcceptanceTest extends AcceptanceTest {

    @Nested
    @DisplayName("채널 생성 기능이")
    class Create {

        private static final String URL = "/api/channels";

        @Test
        @DisplayName("성공한다.")
        void success() {
            // given
            signup("ilseong");
            final String sessionId = login("ilseong");
            final var request = new CreateChannelRequest("rick", 2);

            // when
            final var response = url(URL)
                    .body(request)
                    .method(POST)
                    .send(sessionId);

            // then
            response.statusCode(HttpStatus.CREATED.value())
                    .header(HttpHeaders.LOCATION, "/api/channels/1");
        }

        @Nested
        @DisplayName("실패하는 경우는")
        class Exception {

            @Test
            @DisplayName("최대 인원이 2명 미만인 경우이다.")
            void invalidMaxPeople() {
                // given
                signup("ilseong");
                final String sessionId = login("ilseong");
                final var request = new CreateChannelRequest("rick", 1);

                // when
                final var response = url(URL)
                        .body(request)
                        .method(POST)
                        .send(sessionId);

                // then
                response.statusCode(BAD_REQUEST.value())
                        .body("message", is(InvalidMaxPeopleException.MESSAGE));
            }
        }
    }
}
