package com.nailseong.invitation.acceptance;

import static io.restassured.http.Method.POST;
import static org.springframework.http.HttpHeaders.LOCATION;

import com.nailseong.invitation.authentication.presentation.dto.LoginRequest;
import com.nailseong.invitation.channel.dto.CreateChannelRequest;
import com.nailseong.invitation.invitation.dto.CreateInvitationRequest;
import com.nailseong.invitation.invitation.dto.UseInvitationRequest;
import com.nailseong.invitation.member.dto.SignupRequest;
import com.nailseong.invitation.util.DatabaseCleaner;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.ValidatableResponse;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class AcceptanceTest {

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        databaseCleaner.clean();
    }

    protected void signup(final String username) {
        final var request = new SignupRequest(username);
        url("/api/members")
                .body(request)
                .method(POST)
                .sendWithoutLog();
    }

    protected String login(final String username) {
        final var request = new LoginRequest(username);
        return url("/api/auth/login")
                .body(request)
                .method(POST)
                .sendWithoutLog()
                .extract()
                .cookie("SESSION");
    }

    protected Long createChannel(final String sessionId) {
        return createChannel(sessionId, 2);
    }

    protected Long createChannel(final String sessionId, final int maxPeople) {
        final var request = new CreateChannelRequest("rick", maxPeople);
        return Long.valueOf(url("/api/channels")
                .body(request)
                .method(POST)
                .sendWithoutLog(sessionId)
                .extract()
                .header(LOCATION)
                .split("/")[3]);
    }

    protected String createInvitation(final String sessionId, final Long channelId, final LocalDateTime expireAfter) {
        return createInvitation(sessionId, channelId, expireAfter, 1);
    }

    protected String createInvitation(final String sessionId, final Long channelId, final LocalDateTime expireAfter,
                                      final int maxUses) {
        final var request = new CreateInvitationRequest(channelId, expireAfter, maxUses);
        return url("/api/invitations")
                .body(request)
                .method(POST)
                .sendWithoutLog(sessionId)
                .extract()
                .<String>path("invitationUrl")
                .split("https://invitation.nailseong.com/")[1];
    }

    protected void useInvitation(final String sessionId, final String invitationCode, final String nickname) {
        final var request = new UseInvitationRequest(nickname);
        url("/api/invitations/" + invitationCode)
                .body(request)
                .method(POST)
                .sendWithoutLog(sessionId);

    }

    protected UrlBuilder url(final String url) {
        return new UrlBuilder(url);
    }

    protected record UrlBuilder(String url) {

        public BodyBuilder body(final Object body) {
            return new BodyBuilder(url, body);
        }
    }

    protected record BodyBuilder(String url, Object body) {

        public MethodBuilder method(final Method method) {
            return new MethodBuilder(url, body, method);
        }
    }

    protected record MethodBuilder(String url, Object body, Method method) {

        public ValidatableResponse send() {
            return RestAssured.given().log().all()
                    .body(body)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when()
                    .request(method, url)
                    .then().log().all();
        }

        public ValidatableResponse send(final String sessionId) {
            return RestAssured.given().log().all()
                    .body(body)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .cookie("SESSION", sessionId)
                    .when()
                    .request(method, url)
                    .then().log().all();
        }

        public ValidatableResponse sendWithoutLog() {
            return RestAssured.given()
                    .body(body)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when()
                    .request(method, url)
                    .then();
        }

        public ValidatableResponse sendWithoutLog(final String sessionId) {
            return RestAssured.given()
                    .body(body)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .cookie("SESSION", sessionId)
                    .when()
                    .request(method, url)
                    .then();
        }
    }
}
