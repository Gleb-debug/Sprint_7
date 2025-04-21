package ru.praktikum.client;

import io.restassured.response.Response;
import ru.praktikum.models.Order;

public class OrderClient extends ApiClient {
    public Response create(Order order) {
        return post(Endpoints.ORDERS, order);
    }

    public Response getOrders() {
        return get(Endpoints.ORDERS);
    }
}