package ru.praktikum.client;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ApiClient {
    protected Response post(String path, Object body) {
        return given()
                .header("Content-type", "application/json")
                .body(body)
                .post(path);
    }

    protected Response get(String path) {
        return given()
                .get(path);
    }

    protected Response delete(String path) {
        return given()
                .delete(path);
    }

    protected Response put(String path, Object body) {
        return given()
                .header("Content-Type","application/json")
                .body(body)
                .put(path);
    }
}