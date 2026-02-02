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

@DisplayName("Тесты регистрации")
public class RegistrationTest {

    @Rule
    public TestBase rule = new TestBase();

    private UserAPI userAPI = new UserAPI();
    private TestUser testUser;
    private String accessToken;

    @After
    public void cleanup() {
        // Удаляем пользователя через API после теста (если был создан)
        if (accessToken != null) {
            try {
                userAPI.deleteUser(accessToken);
                System.out.println("Тестовый пользователь удален через API: " + testUser.getEmail());
            } catch (Exception e) {
                System.out.println("Не удалось удалить пользователя: " + e.getMessage());
            }
        }
    }

    @Test
    @DisplayName("Успешная регистрация")
    @Description("Регистрация с валидными данными (пароль от 6 символов)")
    public void successfulRegistration() {
        // Генерируем случайного пользователя
        testUser = UserGenerator.generateRandomUser();

        System.out.println("Создаем пользователя через API перед UI регистрацией: " + testUser.getEmail());

        // 1. Сначала создаем пользователя через API (для удаления после теста)
        userAPI.createUser(testUser)
                .statusCode(200);

        // Получаем токен для удаления
        var loginResponse = userAPI.loginUser(testUser);
        accessToken = userAPI.extractAccessToken(loginResponse);

        // 2. Теперь удаляем его, чтобы можно было зарегистрировать заново через UI
        userAPI.deleteUser(accessToken);
        System.out.println("Пользователь удален, чтобы освободить email для UI регистрации");

        // 3. Регистрируем того же пользователя через UI
        System.out.println("Регистрируем пользователя через UI: " + testUser.getEmail());

        MainPage mainPage = new MainPage(rule.getDriver());
        LoginPage loginPage = new LoginPage(rule.getDriver());
        RegistrationPage regPage = new RegistrationPage(rule.getDriver());

        mainPage.clickLoginButton();
        loginPage.clickRegisterLink();
        regPage.register(testUser.getName(), testUser.getEmail(), testUser.getPassword());

        // Ждем перехода на страницу входа
        loginPage.waitForLoad();

        assertTrue("После регистрации должна открыться страница входа",
                rule.getDriver().getCurrentUrl().contains("/login"));

        System.out.println("Пользователь успешно зарегистрирован через UI");

        // 4. Снова получаем токен для удаления (после UI регистрации)
        var newLoginResponse = userAPI.loginUser(testUser);
        if (newLoginResponse.extract().statusCode() == 200) {
            accessToken = userAPI.extractAccessToken(newLoginResponse);
        } else {
            accessToken = null;
            System.out.println("Не удалось получить токен после UI регистрации");
        }
    }

    @Test
    @DisplayName("Ошибка при регистрации с коротким паролем")
    @Description("Регистрация с паролем менее 6 символов должна показывать ошибку")
    public void registrationWithShortPassword() {
        // Генерируем пользователя с коротким паролем
        testUser = UserGenerator.generateUserWithShortPassword();

        System.out.println("Пробуем зарегистрироваться с коротким паролем (" +
                testUser.getPassword().length() + " символов): " +
                testUser.getEmail());

        MainPage mainPage = new MainPage(rule.getDriver());
        LoginPage loginPage = new LoginPage(rule.getDriver());
        RegistrationPage regPage = new RegistrationPage(rule.getDriver());

        mainPage.clickLoginButton();
        loginPage.clickRegisterLink();
        regPage.register(testUser.getName(), testUser.getEmail(), testUser.getPassword());

        assertTrue("Должна отображаться ошибка 'Некорректный пароль'",
                regPage.isPasswordErrorDisplayed());

        // Не создаем пользователя через API, так как регистрация не удалась
        accessToken = null;
    }
}