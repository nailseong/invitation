package com.nailseong.invitation.acceptance;

import static io.restassured.http.Method.POST;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.nailseong.invitation.channel.dto.CreateChannelRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

@DisplayName("채널 인수 테스트")
class ChannelAcceptanceTest extends AcceptanceTest {

    private static final String URL = "/api/channels";

    @Nested
    @DisplayName("채널 생성 기능이")
    class Create {

        @Test
        @DisplayName("성공한다.")
        void success() {
            // given
            signup("ilseong");
            final var request = new CreateChannelRequest(1L, "rick", 2);

            // when
            final var response = url(URL)
                    .body(request)
                    .method(POST)
                    .send();

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
                final var request = new CreateChannelRequest(1L, "rick", 1);

                // when
                final var response = url(URL)
                        .body(request)
                        .method(POST)
                        .send();

                // then
                response.statusCode(BAD_REQUEST.value());
            }

            @Test
            @DisplayName("사용자가 존재하지 않는 경우이다.")
            void notExistMember() {
                // given
                final var request = new CreateChannelRequest(999L, "rick", 2);

                // when
                final var response = url(URL)
                        .body(request)
                        .method(POST)
                        .send();

                // then
                response.statusCode(NOT_FOUND.value());
            }
        }
    }
}
