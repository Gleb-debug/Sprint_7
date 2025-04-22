package ru.praktikum.client;

import io.restassured.response.Response;
import ru.praktikum.models.Order;

import java.util.Map;

public class OrderClient extends ApiClient {
    public Response create(Order order) {
        return post(Endpoints.ORDERS, order);
    }

    public Response getOrders() {
        return get(Endpoints.ORDERS);
    }
    public Response cancelOrder(int trackId) {
        return put(Endpoints.ORDERS_CANCEL, Map.of("track", trackId));
    }
}