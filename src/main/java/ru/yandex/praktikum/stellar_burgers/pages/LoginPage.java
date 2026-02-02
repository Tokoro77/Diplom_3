package ru.yandex.praktikum.stellar_burgers.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By emailField = By.xpath("//label[text()='Email']/following-sibling::input");
    private final By passwordField = By.xpath("//label[text()='Пароль']/following-sibling::input");
    private final By loginButton = By.xpath("//button[text()='Войти']");
    private final By registerLink = By.xpath("//a[text()='Зарегистрироваться']");
    private final By forgotPasswordLink = By.xpath("//a[text()='Восстановить пароль']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Ожидание загрузки страницы входа")
    public void waitForLoad() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginButton));
    }

    @Step("Ввод email: {email}")
    public void enterEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    @Step("Ввод пароля")
    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    @Step("Клик на кнопку 'Войти'")
    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    @Step("Авторизация пользователя {email}")
    public void login(String email, String password) {
        waitForLoad();
        enterEmail(email);
        enterPassword(password);
        clickLoginButton();
    }

    @Step("Клик на ссылку 'Зарегистрироваться'")
    public void clickRegisterLink() {
        driver.findElement(registerLink).click();
    }

    @Step("Клик на ссылку 'Восстановить пароль'")
    public void clickForgotPasswordLink() {
        driver.findElement(forgotPasswordLink).click();
    }
}