package ru.praktikum.client;

import io.restassured.response.Response;
import ru.praktikum.models.Courier;

public class CourierClient extends ApiClient {
    public Response create(Courier courier) {
        return post(Endpoints.COURIER, courier);
    }

    public Response login(Courier courier) {
        return post(Endpoints.COURIER_LOGIN, courier);
    }

    public Response delete(int courierId) {
        return delete(Endpoints.COURIER_DELETE + courierId);
    }
}