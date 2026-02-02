package ru.yandex.praktikum.stellar_burgers.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Rule;
import org.junit.Test;
import ru.yandex.praktikum.stellar_burgers.pages.MainPage;
import static org.junit.Assert.assertTrue;

@DisplayName("Тесты раздела 'Конструктор'")
public class ConstructorTest {

    @Rule
    public TestBase rule = new TestBase();

    @Test
    @DisplayName("Переключение на раздел 'Соусы'")
    @Description("Проверка переключения таба конструктора на 'Соусы' в браузере: {browser}")
    public void checkSaucesTab() {
        System.out.println("Тест выполняется в браузере: " + rule.getCurrentBrowser());

        MainPage mainPage = new MainPage(rule.getDriver());
        mainPage.clickSaucesTab();
        assertTrue("Таб 'Соусы' должен быть активным в браузере " + rule.getCurrentBrowser(),
                mainPage.isSaucesTabActive());
    }

    @Test
    @DisplayName("Переключение на раздел 'Начинки'")
    @Description("Проверка переключения таба конструктора на 'Начинки' в браузере: {browser}")
    public void checkFillingsTab() {
        System.out.println("Тест выполняется в браузере: " + rule.getCurrentBrowser());

        MainPage mainPage = new MainPage(rule.getDriver());
        mainPage.clickFillingsTab();
        assertTrue("Таб 'Начинки' должен быть активным в браузере " + rule.getCurrentBrowser(),
                mainPage.isFillingsTabActive());
    }

    @Test
    @DisplayName("Возврат к разделу 'Булки'")
    @Description("Проверка возврата к табу 'Булки' после переключения в браузере: {browser}")
    public void checkBunsTab() {
        System.out.println("Тест выполняется в браузере: " + rule.getCurrentBrowser());

        MainPage mainPage = new MainPage(rule.getDriver());
        mainPage.clickSaucesTab(); // Сначала переключаемся
        mainPage.clickBunsTab();   // Затем возвращаемся
        assertTrue("Таб 'Булки' должен быть активным в браузере " + rule.getCurrentBrowser(),
                mainPage.isBunsTabActive());
    }
}