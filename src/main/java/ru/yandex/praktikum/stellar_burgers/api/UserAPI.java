package ru.yandex.praktikum.stellar_burgers.api;

import io.restassured.response.ValidatableResponse;
import ru.yandex.praktikum.stellar_burgers.model.TestUser;

import static io.restassured.RestAssured.given;

public class UserAPI {
    private static final String BASE_URI = "https://stellarburgers.education-services.ru";
    private static final String API_PREFIX = "/api/auth/";

    public ValidatableResponse createUser(TestUser user) {
        return given()
                .log().all()
                .contentType("application/json")
                .baseUri(BASE_URI)
                .body(user)
                .when()
                .post(API_PREFIX + "register")
                .then()
                .log().all();
    }

    public ValidatableResponse loginUser(TestUser user) {
        return given()
                .log().all()
                .contentType("application/json")
                .baseUri(BASE_URI)
                .body(user)
                .when()
                .post(API_PREFIX + "login")
                .then()
                .log().all();
    }

    public ValidatableResponse deleteUser(String accessToken) {
        return given()
                .log().all()
                .contentType("application/json")
                .baseUri(BASE_URI)
                .header("Authorization", accessToken)
                .when()
                .delete(API_PREFIX + "user")
                .then()
                .log().all();
    }

    public String extractAccessToken(ValidatableResponse response) {
        return response.extract().path("accessToken");
    }
}