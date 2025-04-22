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


public class CourierCreationTest {
    private CourierSteps courierSteps;
    private Courier courier;
    private int createdCourierId;

    @Before
    public void setUp() {
        TestUtils.configureRestAssured();
        courierSteps = new CourierSteps();
        courier = DataGenerator.randomCourier();
    }

    @Test
    @DisplayName("Создание курьера с валидными данными")
    @Description("Проверка успешного создания курьера")
    public void testCreateCourierSuccess() {
        Response createResponse = courierSteps.createCourier(courier);
        courierSteps.verifyCreationSuccess(createResponse);
    }

    @Test
    @DisplayName("Создание курьера без имени")
    @Description("Проверка создания курьера без необязательного поля firstName")
    public void testCreateCourierWithoutFirstName() {
        courier.setFirstName(null);
        Response createResponse = courierSteps.createCourier(courier);
        courierSteps.verifyCreationSuccess(createResponse);
    }

    @Test
    @DisplayName("Создание дубликата курьера")
    @Description("Проверка ошибки при создании курьера с существующим логином")
    public void testCreateDuplicateCourier() {
        courierSteps.createCourier(courier);
        Response duplicateResponse = courierSteps.createCourier(courier);
        courierSteps.verifyDuplicateError(duplicateResponse);
    }

    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Проверка ошибки при создании курьера без обязательного поля login")
    public void testCreateCourierWithoutLogin() {
        courier.setLogin(null);
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("Проверка ошибки при создании курьера без обязательного поля password")
    public void testCreateCourierWithoutPassword() {
        courier.setPassword(null);
    }

    @After
    @DisplayName("Удаление тестовых данных")
    public void tearDown() {
        if (createdCourierId != 0) {
            courierSteps.deleteCourier(createdCourierId);
        }
        if (courier.getLogin() == null|| courier.getPassword() == null) {
            Response response = courierSteps.createCourier(courier);
            courierSteps.verifyMissingFieldError(response);
        }else {
            Response loginResponse = courierSteps.loginCourier(courier);
            createdCourierId = courierSteps.verifyLoginSuccess(loginResponse);
        }
    }
}