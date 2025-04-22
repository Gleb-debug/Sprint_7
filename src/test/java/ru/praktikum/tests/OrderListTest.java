package ru.praktikum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.steps.OrderSteps;
import ru.praktikum.utils.TestUtils;

public class OrderListTest {
    private OrderSteps orderSteps;

    @Before
    public void setUp() {
        TestUtils.configureRestAssured();
        orderSteps = new OrderSteps();
    }

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Проверка получения списка заказов")
    public void testGetOrderList() {
        Response response = orderSteps.getOrderList();
        orderSteps.verifyOrderList(response);
    }
}