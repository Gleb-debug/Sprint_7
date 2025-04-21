package ru.praktikum.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import ru.praktikum.client.CourierClient;
import ru.praktikum.models.Courier;

import static org.hamcrest.Matchers.*;

public class CourierSteps {
    private final CourierClient client = new CourierClient();

    @Step("Создать курьера")
    public Response createCourier(Courier courier) {
        return client.create(courier);
    }

    @Step("Авторизовать курьера")
    public Response loginCourier(Courier courier) {
        return client.login(courier);
    }

    @Step("Удалить курьера")
    public Response deleteCourier(int courierId) {
        return client.delete(courierId);
    }

    @Step("Проверить успешное создание курьера")
    public void verifyCreationSuccess(Response response) {
        response.then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("ok", is(true));
    }

    @Step("Проверить ошибку дублирования курьера")
    public void verifyDuplicateError(Response response) {
        response.then()
                .statusCode(HttpStatus.SC_CONFLICT)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Step("Проверить ошибку при отсутствии обязательного поля")
    public void verifyMissingFieldError(Response response) {
        response.then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Step("Проверить успешную авторизацию")
    public int verifyLoginSuccess(Response response) {
        return response.then()
                .statusCode(HttpStatus.SC_OK)
                .body("id", notNullValue())
                .extract().path("id");
    }

    @Step("Проверить ошибку авторизации")
    public void verifyLoginError(Response response) {
        response.then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }
}