package ru.praktikum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikum.models.Order;
import ru.praktikum.steps.OrderSteps;
import ru.praktikum.utils.DataGenerator;
import ru.praktikum.utils.TestUtils;

import java.util.List;

@RunWith(Parameterized.class)
public class OrderCreationTest {
    private OrderSteps orderSteps;
    private final List<String> colors;
    private Integer track;

    public OrderCreationTest(List<String> colors, String description) {
        this.colors = colors;
    }

    @Parameterized.Parameters(name = "{1}")
    public static Object[][] getTestData() {
        return new Object[][] {
                {List.of("BLACK"), "Только BLACK цвет"},
                {List.of("GREY"), "Только GREY цвет"},
                {List.of("BLACK", "GREY"), "Оба цвета"},
                {List.of(), "Без указания цвета"}
        };
    }

    @Before
    public void setUp() {
        TestUtils.configureRestAssured();
        orderSteps = new OrderSteps();
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Проверка создания заказа с разными вариантами цветов")
    public void testCreateOrderWithDifferentColors() {
        Order order = DataGenerator.randomOrder(colors);
        Response response = orderSteps.createOrder(order);
        orderSteps.verifyCreationSuccess(response);
        track = response.then().extract().body().jsonPath().get("track");
    }

    @After
    public void tearDown() {
        orderSteps.cancelOrder(track);
    }
}