package ru.praktikum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.models.Courier;
import ru.praktikum.steps.CourierSteps;
import ru.praktikum.utils.DataGenerator;
import ru.praktikum.utils.TestUtils;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.CoreMatchers.equalTo;

public class CourierLoginTest {
    private CourierSteps courierSteps;
    private Courier courier;
    private int createdCourierId;

    @Before
    public void setUp() {
        TestUtils.configureRestAssured();
        courierSteps = new CourierSteps();
        courier = DataGenerator.randomCourier();

        Response createResponse = courierSteps.createCourier(courier);
        if (createResponse.statusCode() == SC_CREATED) {
            Response loginResponse = courierSteps.loginCourier(courier);
            createdCourierId = courierSteps.verifyLoginSuccess(loginResponse);
        }
    }

    @Test
    @DisplayName("Успешная авторизация курьера")
    @Description("Проверка авторизации с валидными данными")
    public void testLoginSuccess() {
        Response response = courierSteps.loginCourier(courier);
        courierSteps.verifyLoginSuccess(response);
    }

    @Test
    @DisplayName("Авторизация с неверным паролем")
    @Description("Проверка ошибки при авторизации с неверным паролем")
    public void testLoginWithWrongPassword() {
        courier.setPassword("wrong_password");
        Response response = courierSteps.loginCourier(courier);
        courierSteps.verifyLoginError(response);
    }

    @Test
    @DisplayName("Авторизация с неверным логином")
    @Description("Проверка ошибки при авторизации с неверным логином")
    public void testLoginWithWrongLogin() {
        courier.setLogin("wrong_login");
        Response response = courierSteps.loginCourier(courier);
        courierSteps.verifyLoginError(response);
    }

    @Test
    @DisplayName("Авторизация без пароля")
    @Description("Проверка ошибки при авторизации без пароля")
    public void testLoginWithoutPassword() {
        courier.setPassword("");
        Response response = courierSteps.loginCourier(courier);
        response.then()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация без логина")
    @Description("Проверка ошибки при авторизации без логина")
    public void testLoginWithoutLogin() {
        courier.setLogin(null);
        Response response = courierSteps.loginCourier(courier);
        response.then()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @After
    @DisplayName("Удаление тестовых данных")
    public void tearDown() {
        if (createdCourierId != 0) {
            courierSteps.deleteCourier(createdCourierId);
        }
    }
}