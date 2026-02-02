package ru.yandex.praktikum.stellar_burgers.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.io.File;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class WebDriverFactory {

    public enum Browser {
        CHROME,
        YANDEX
    }

    public static WebDriver getDriver(Browser browser) {
        WebDriver driver;

        switch (browser) {
            case YANDEX:
                driver = createYandexDriver();
                break;
            case CHROME:
            default:
                driver = createChromeDriver();
                break;
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        return driver;
    }

    private static WebDriver createChromeDriver() {
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver();
    }

    private static WebDriver createYandexDriver() {
        // Пути к Яндекс.Браузеру для Windows
        String yandexBinaryPath = findYandexBrowserPath();

        if (yandexBinaryPath == null) {
            throw new RuntimeException("Yandex Browser не найден. Установите Яндекс.Браузер или укажите путь вручную.");
        }

        System.out.println("Используется Яндекс.Браузер: " + yandexBinaryPath);

        ChromeOptions options = new ChromeOptions();
        options.setBinary(yandexBinaryPath);

        // Для Яндекс.Браузера используем ChromeDriver
        WebDriverManager.chromedriver().setup();

        return new ChromeDriver(options);
    }

    private static String findYandexBrowserPath() {
        // Проверяем системную переменную
        String customPath = System.getProperty("yandex.browser.path");
        if (customPath != null && new File(customPath).exists()) {
            return customPath;
        }

        // Стандартные пути для Windows
        List<String> possiblePaths = Arrays.asList(
                "C:\\Program Files\\Yandex\\YandexBrowser\\Application\\browser.exe",
                "C:\\Program Files (x86)\\Yandex\\YandexBrowser\\Application\\browser.exe",
                System.getProperty("user.home") + "\\AppData\\Local\\Yandex\\YandexBrowser\\Application\\browser.exe"
        );

        for (String path : possiblePaths) {
            File file = new File(path);
            if (file.exists()) {
                return path;
            }
        }

        return null;
    }

    // Метод для получения драйвера по названию из системной переменной
    public static WebDriver getDriver() {
        String browserName = System.getProperty("browser", "chrome").toUpperCase();

        try {
            Browser browser = Browser.valueOf(browserName);
            return getDriver(browser);
        } catch (IllegalArgumentException e) {
            System.out.println("Неизвестный браузер: " + browserName + ". Используется Chrome.");
            return getDriver(Browser.CHROME);
        }
    }
}