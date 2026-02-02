package ru.yandex.praktikum.stellar_burgers.util;

import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;
import ru.yandex.praktikum.stellar_burgers.model.TestUser;

public class UserGenerator {
    private static final Faker faker = new Faker();

    public static TestUser generateRandomUser() {
        String name = faker.name().firstName();
        String email = "test_" + System.currentTimeMillis() + "_" +
                RandomStringUtils.randomAlphanumeric(5) + "@yandex.ru";
        String password = faker.internet().password(6, 10);

        return new TestUser(name, email, password);
    }

    public static TestUser generateSpecificUser(String email, String password) {
        String name = "TestUser";
        return new TestUser(name, email, password);
    }

    public static TestUser generateInvalidPasswordUser() {
        String name = faker.name().firstName();
        String email = "test_invalid_" + System.currentTimeMillis() + "@yandex.ru";
        String password = faker.internet().password(1, 5); // Менее 6 символов

        return new TestUser(name, email, password);
    }
}