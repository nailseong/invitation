package com.nailseong.acceptance;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

import com.nailseong.invitation.presentation.member.dto.SignupRequest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

@DisplayName("회원 인수 테스트")
class MemberAcceptanceTest extends AcceptanceTest {

    @Nested
    @DisplayName("회원가입 기능이")
    class Signup {

        @Nested
        @DisplayName("성공하는 경우는")
        class Success {

            @Test
            @DisplayName("사용자 이름이 유효한 경우이다.")
            void validUsername() {
                // given
                final var request = new SignupRequest("ilseong");

                // when
                final var response = RestAssured.given().log().all()
                        .body(request)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .when()
                        .post("/api/members")
                        .then().log().all();

                // then
                response.statusCode(CREATED.value());
            }
        }

        @Nested
        @DisplayName("실패하는 경우는")
        class Exception {

            @Test
            @DisplayName("사용자 이름이 중복되는 경우이다.")
            void duplicateUsername() {
                // given
                final var username = "ilseong";
                signup(username);

                final var request = new SignupRequest(username);

                // when
                final var response = RestAssured.given().log().all()
                        .body(request)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .when()
                        .post("/api/members")
                        .then().log().all();

                // then
                response.statusCode(BAD_REQUEST.value());
            }
        }

        private void signup(final String username) {
            final var request = new SignupRequest(username);

            RestAssured.given().log().all()
                    .body(request)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when()
                    .post("/api/members")
                    .then().log().all();
        }
    }
}
