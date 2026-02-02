package ru.yandex.praktikum.stellar_burgers.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class MainPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By loginButton = By.xpath("//button[text()='Войти в аккаунт']");
    private final By personalAccountButton = By.xpath("//p[text()='Личный Кабинет']");
    private final By orderButton = By.xpath("//button[text()='Оформить заказ']");
    private final By bunsTab = By.xpath("//span[text()='Булки']/parent::div");
    private final By saucesTab = By.xpath("//span[text()='Соусы']/parent::div");
    private final By fillingsTab = By.xpath("//span[text()='Начинки']/parent::div");

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Клик на 'Войти в аккаунт'")
    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    @Step("Клик на 'Личный кабинет'")
    public void clickPersonalAccount() {
        driver.findElement(personalAccountButton).click();
    }

    @Step("Клик на таб 'Булки'")
    public void clickBunsTab() {
        driver.findElement(bunsTab).click();
    }

    @Step("Клик на таб 'Соусы'")
    public void clickSaucesTab() {
        driver.findElement(saucesTab).click();
    }

    @Step("Клик на таб 'Начинки'")
    public void clickFillingsTab() {
        driver.findElement(fillingsTab).click();
    }

    @Step("Проверка активности таба 'Булки'")
    public boolean isBunsTabActive() {
        return isTabActive(bunsTab);
    }

    @Step("Проверка активности таба 'Соусы'")
    public boolean isSaucesTabActive() {
        return isTabActive(saucesTab);
    }

    @Step("Проверка активности таба 'Начинки'")
    public boolean isFillingsTabActive() {
        return isTabActive(fillingsTab);
    }

    private boolean isTabActive(By tab) {
        try {
            wait.until(ExpectedConditions.attributeContains(tab, "class", "current"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Проверка видимости кнопки 'Оформить заказ'")
    public boolean isOrderButtonVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(orderButton));
            return driver.findElement(orderButton).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}