package ru.yandex.praktikum.stellar_burgers.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class RegistrationPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By nameField = By.xpath("//label[text()='Имя']/following-sibling::input");
    private final By emailField = By.xpath("//label[text()='Email']/following-sibling::input");
    private final By passwordField = By.xpath("//label[text()='Пароль']/following-sibling::input");
    private final By registerButton = By.xpath("//button[text()='Зарегистрироваться']");
    private final By errorMessage = By.xpath("//p[text()='Некорректный пароль']");
    private final By loginLink = By.xpath("//a[text()='Войти']");

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Ввод имени: {name}")
    public RegistrationPage enterName(String name) {
        driver.findElement(nameField).sendKeys(name);
        return this;
    }

    @Step("Ввод email: {email}")
    public RegistrationPage enterEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
        return this;
    }

    @Step("Ввод пароля")
    public RegistrationPage enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
        return this;
    }

    @Step("Клик на кнопку 'Зарегистрироваться'")
    public void clickRegisterButton() {
        driver.findElement(registerButton).click();
    }

    @Step("Полная регистрация пользователя")
    public void register(String name, String email, String password) {
        enterName(name)
                .enterEmail(email)
                .enterPassword(password)
                .clickRegisterButton();
    }

    @Step("Клик на ссылку 'Войти'")
    public void clickLoginLink() {
        driver.findElement(loginLink).click();
    }

    @Step("Проверка отображения ошибки пароля")
    public boolean isPasswordErrorDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
            return driver.findElement(errorMessage).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}