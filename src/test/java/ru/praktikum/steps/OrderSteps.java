package ru.praktikum.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import ru.praktikum.client.OrderClient;
import ru.praktikum.models.Order;

import static org.hamcrest.Matchers.*;

public class OrderSteps {
    private final OrderClient client = new OrderClient();

    @Step("Создать заказ")
    public Response createOrder(Order order) {
        return client.create(order);
    }

    @Step("Получить список заказов")
    public Response getOrderList() {
        return client.getOrders();
    }

    @Step("Проверить успешное создание заказа")
    public void verifyCreationSuccess(Response response) {
        response.then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("track", notNullValue());
    }

    @Step("Проверить список заказов")
    public void verifyOrderList(Response response) {
        response.then()
                .statusCode(HttpStatus.SC_OK)
                .body("orders", notNullValue())
                .body("orders.size()", greaterThan(0));
    }
}