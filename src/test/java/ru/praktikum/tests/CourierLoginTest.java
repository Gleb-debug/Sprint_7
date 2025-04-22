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

import static org.apache.http.HttpStatus.*;

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
    }

    @Test
    @DisplayName("Авторизация с неверным логином")
    @Description("Проверка ошибки при авторизации с неверным логином")
    public void testLoginWithWrongLogin() {
        courier.setLogin("wrong_login");
    }

    @Test
    @DisplayName("Авторизация без пароля")
    @Description("Проверка ошибки при авторизации без пароля")
    public void testLoginWithoutPassword() {
        courier.setPassword("");
    }

    @Test
    @DisplayName("Авторизация без логина")
    @Description("Проверка ошибки при авторизации без логина")
    public void testLoginWithoutLogin() {
        courier.setLogin(null);
    }

    @After
    @DisplayName("Удаление тестовых данных")
    public void tearDown() {
        if (createdCourierId != 0) {
            courierSteps.deleteCourier(createdCourierId);
        }
        Response response = courierSteps.loginCourier(courier);
        if (response.statusCode() == SC_BAD_REQUEST) {
            courierSteps.verifyMissingFieldInLoginError(response);
        }else if (response.statusCode() == SC_NOT_FOUND) {
            courierSteps.verifyLoginError(response);
        }
    }
}