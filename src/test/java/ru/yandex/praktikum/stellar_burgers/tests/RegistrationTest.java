package ru.yandex.praktikum.stellar_burgers.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Rule;
import org.junit.Test;
import ru.yandex.praktikum.stellar_burgers.model.TestUser;
import ru.yandex.praktikum.stellar_burgers.pages.*;
import ru.yandex.praktikum.stellar_burgers.util.UserGenerator;
import static org.junit.Assert.assertTrue;

@DisplayName("Тесты регистрации")
public class RegistrationTest {

    @Rule
    public TestBase rule = new TestBase();

    @Test
    @DisplayName("Успешная регистрация")
    @Description("Регистрация с валидными данными (пароль от 6 символов)")
    public void successfulRegistration() {
        // Генерируем случайного пользователя для регистрации
        TestUser newUser = UserGenerator.generateRandomUser();

        System.out.println("Регистрируем нового пользователя: " + newUser.getEmail());

        MainPage mainPage = new MainPage(rule.getDriver());
        LoginPage loginPage = new LoginPage(rule.getDriver());
        RegistrationPage regPage = new RegistrationPage(rule.getDriver());

        mainPage.clickLoginButton();
        loginPage.clickRegisterLink();
        regPage.register(newUser.getName(), newUser.getEmail(), newUser.getPassword());

        // Ждем перехода на страницу входа
        loginPage.waitForLoad();

        assertTrue("После регистрации должна открыться страница входа",
                rule.getDriver().getCurrentUrl().contains("/login"));

        System.out.println("Пользователь успешно зарегистрирован. Email: " + newUser.getEmail());
        System.out.println("Запомни данные для ручного тестирования:");
        System.out.println("Email: " + newUser.getEmail());
        System.out.println("Пароль: " + newUser.getPassword());
    }

    @Test
    @DisplayName("Ошибка при регистрации с коротким паролем")
    @Description("Регистрация с паролем менее 6 символов должна показывать ошибку")
    public void registrationWithShortPassword() {
        // Генерируем пользователя с некорректным паролем
        TestUser invalidUser = UserGenerator.generateInvalidPasswordUser();

        System.out.println("Пробуем зарегистрироваться с коротким паролем (" +
                invalidUser.getPassword().length() + " символов): " +
                invalidUser.getEmail());

        MainPage mainPage = new MainPage(rule.getDriver());
        LoginPage loginPage = new LoginPage(rule.getDriver());
        RegistrationPage regPage = new RegistrationPage(rule.getDriver());

        mainPage.clickLoginButton();
        loginPage.clickRegisterLink();
        regPage.register(invalidUser.getName(), invalidUser.getEmail(), invalidUser.getPassword());

        assertTrue("Должна отображаться ошибка 'Некорректный пароль'",
                regPage.isPasswordErrorDisplayed());
    }
}