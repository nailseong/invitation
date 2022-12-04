package com.nailseong.invitation.acceptance;

import com.nailseong.invitation.util.DatabaseCleaner;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.ValidatableResponse;
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
    }
}
