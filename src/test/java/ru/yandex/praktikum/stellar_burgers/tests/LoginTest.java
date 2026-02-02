package ru.yandex.praktikum.stellar_burgers.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import ru.yandex.praktikum.stellar_burgers.api.UserAPI;
import ru.yandex.praktikum.stellar_burgers.model.TestUser;
import ru.yandex.praktikum.stellar_burgers.pages.*;
import ru.yandex.praktikum.stellar_burgers.util.UserGenerator;

import static org.junit.Assert.assertTrue;

@DisplayName("Тесты авторизации")
public class LoginTest {

    @Rule
    public TestBase rule = new TestBase();

    private UserAPI userAPI = new UserAPI();
    private TestUser testUser;
    private String accessToken;

    // Создаем пользователя через API перед каждым тестом
    private void createTestUserForLogin() {
        // Генерируем случайного пользователя
        testUser = UserGenerator.generateRandomUser();

        System.out.println("Создаем пользователя через API для теста входа: " + testUser.getEmail());

        // Создаем пользователя через API
        userAPI.createUser(testUser)
                .statusCode(200);

        // Получаем токен для последующего удаления
        var loginResponse = userAPI.loginUser(testUser);
        accessToken = userAPI.extractAccessToken(loginResponse);

        System.out.println("Пользователь создан и готов для входа через UI");
    }

    @After
    public void cleanup() {
        // Удаляем пользователя через API после теста
        if (accessToken != null) {
            try {
                userAPI.deleteUser(accessToken);
                System.out.println("Пользователь удален через API: " + testUser.getEmail());
            } catch (Exception e) {
                System.out.println("Не удалось удалить пользователя: " + e.getMessage());
            }
        }
    }

    @Test
    @DisplayName("Вход через кнопку 'Войти в аккаунт' на главной")
    @Description("Проверка входа через основную кнопку на главной странице")
    public void loginFromMainPageButton() {
        createTestUserForLogin();

        MainPage mainPage = new MainPage(rule.getDriver());
        LoginPage loginPage = new LoginPage(rule.getDriver());

        mainPage.clickLoginButton();
        loginPage.login(testUser.getEmail(), testUser.getPassword());

        assertTrue("После входа должна отображаться кнопка 'Оформить заказ'",
                mainPage.isOrderButtonVisible());

        System.out.println("Успешный вход пользователя через UI: " + testUser.getEmail());
    }

    @Test
    @DisplayName("Вход через кнопку 'Личный кабинет'")
    @Description("Проверка входа через кнопку личного кабинета")
    public void loginFromPersonalAccount() {
        createTestUserForLogin();

        MainPage mainPage = new MainPage(rule.getDriver());
        LoginPage loginPage = new LoginPage(rule.getDriver());

        mainPage.clickPersonalAccount();
        loginPage.login(testUser.getEmail(), testUser.getPassword());

        assertTrue("После входа должна отображаться кнопка 'Оформить заказ'",
                mainPage.isOrderButtonVisible());
    }

    @Test
    @DisplayName("Вход через ссылку в форме регистрации")
    @Description("Проверка входа через ссылку 'Войти' на странице регистрации")
    public void loginFromRegistrationPage() {
        createTestUserForLogin();

        MainPage mainPage = new MainPage(rule.getDriver());
        LoginPage loginPage = new LoginPage(rule.getDriver());
        RegistrationPage regPage = new RegistrationPage(rule.getDriver());

        mainPage.clickLoginButton();
        loginPage.clickRegisterLink();
        regPage.clickLoginLink();
        loginPage.login(testUser.getEmail(), testUser.getPassword());

        assertTrue("После входа должна отображаться кнопка 'Оформить заказ'",
                mainPage.isOrderButtonVisible());
    }

    @Test
    @DisplayName("Вход через ссылку в форме восстановления пароля")
    @Description("Проверка входа через ссылку 'Войти' на странице восстановления пароля")
    public void loginFromForgotPasswordPage() {
        createTestUserForLogin();

        MainPage mainPage = new MainPage(rule.getDriver());
        LoginPage loginPage = new LoginPage(rule.getDriver());
        ForgotPasswordPage forgotPage = new ForgotPasswordPage(rule.getDriver());

        mainPage.clickLoginButton();
        loginPage.clickForgotPasswordLink();
        forgotPage.clickLoginLink();
        loginPage.login(testUser.getEmail(), testUser.getPassword());

        assertTrue("После входа должна отображаться кнопка 'Оформить заказ'",
                mainPage.isOrderButtonVisible());
    }
}