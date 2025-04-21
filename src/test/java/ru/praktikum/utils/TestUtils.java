package ru.praktikum.utils;

import io.restassured.RestAssured;

public class TestUtils {
    public static void configureRestAssured() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }
}