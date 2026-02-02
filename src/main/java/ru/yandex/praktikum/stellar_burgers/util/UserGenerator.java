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

    public static TestUser generateUserWithShortPassword() {
        String name = faker.name().firstName();
        String email = "test_short_" + System.currentTimeMillis() + "@yandex.ru";
        String password = RandomStringUtils.randomNumeric(5); // 5 символов - меньше минимального

        return new TestUser(name, email, password);
    }
}