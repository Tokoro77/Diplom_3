package ru.yandex.praktikum.stellar_burgers.tests;

import org.junit.rules.ExternalResource;
import org.openqa.selenium.WebDriver;
import ru.yandex.praktikum.stellar_burgers.config.WebDriverFactory;
import ru.yandex.praktikum.stellar_burgers.config.WebDriverFactory.Browser;

public class TestBase extends ExternalResource {
    private WebDriver driver;
    private Browser currentBrowser;

    @Override
    protected void before() throws Throwable {
        // Определяем браузер из системной переменной или используем Chrome по умолчанию
        String browserName = System.getProperty("browser", "chrome").toUpperCase();

        try {
            currentBrowser = Browser.valueOf(browserName);
        } catch (IllegalArgumentException e) {
            System.out.println("Неизвестный браузер: " + browserName + ". Используется Chrome.");
            currentBrowser = Browser.CHROME;
        }

        System.out.println("Запуск тестов в браузере: " + currentBrowser);
        driver = WebDriverFactory.getDriver(currentBrowser);
        driver.get("https://stellarburgers.education-services.ru/");
    }

    @Override
    protected void after() {
        if (driver != null) {
            driver.quit();
        }
    }

    public WebDriver getDriver() {
        return driver;
    }

    public Browser getCurrentBrowser() {
        return currentBrowser;
    }
}