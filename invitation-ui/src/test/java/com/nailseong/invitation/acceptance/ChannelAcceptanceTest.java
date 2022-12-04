package com.nailseong.invitation.acceptance;

import static io.restassured.http.Method.POST;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.nailseong.invitation.channel.dto.CreateChannelRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("채널 인수 테스트")
class ChannelAcceptanceTest extends AcceptanceTest {

    @Nested
    @DisplayName("채널 생성 기능이")
    class Create {

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
                final var response = url("/api/channels")
                        .body(request)
                        .method(POST)
                        .send();

                // then
                response.statusCode(BAD_REQUEST.value());
            }
        }
    }
}
