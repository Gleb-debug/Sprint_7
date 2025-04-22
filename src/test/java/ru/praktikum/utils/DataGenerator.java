package ru.praktikum.utils;

import net.datafaker.Faker;
import ru.praktikum.models.Courier;
import ru.praktikum.models.Order;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class DataGenerator {
    private static final Faker faker = new Faker();

    public static Courier randomCourier() {
        return new Courier(
                faker.name().username(),
                faker.internet().password(8, 16),
                faker.name().firstName()
        );
    }

    public static Order randomOrder(List<String> colors) {
        return new Order(
                faker.name().firstName(),
                faker.name().lastName(),
                faker.address().fullAddress(),
                ThreadLocalRandom.current().nextInt(1, 10),
                faker.phoneNumber().phoneNumber(),
                ThreadLocalRandom.current().nextInt(1, 7),
                LocalDate.now().plusDays(3).format(DateTimeFormatter.ISO_DATE),
                faker.lorem().sentence(),
                colors
        );
    }

    public static List<String> randomColorCombination() {
        String[] colors = {"BLACK", "GREY"};
        int count = ThreadLocalRandom.current().nextInt(0, 3);
        return count == 0 ? List.of() :
                count == 1 ? List.of(colors[ThreadLocalRandom.current().nextInt(0, 2)]) :
                        List.of(colors);
    }
}