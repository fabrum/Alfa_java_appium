import exp.appium.Driver;
import exp.pages.LoginPage;
import exp.pages.FirstPage;

import io.appium.java_client.AppiumDriver;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;



import static org.junit.jupiter.api.Assertions.*;

public class TestLogin {
    private AppiumDriver driver;
    private LoginPage loginPage;
    private FirstPage firstPage;

    @BeforeAll
    static void setupAll() {
        System.out.println("=== Начало тестового прогона ===");
    }

    @BeforeEach
    void setUp() throws Exception {
      driver= Driver.getDriver();
      loginPage = new LoginPage();
      firstPage = new FirstPage();
    }

    @DisplayName("Запуск приложения")
    @Tag("positive")
    @Tag("login")
    @Order(1)
    @Test
    void testAppOpens() {
        loginPage.setScreenshot("OpenLoginPage");

        assertEquals("Вход в Alfa-Test",loginPage.getTitleLogo());
        assertTrue(loginPage.isUsernameFieldDisplayed());
        assertTrue(loginPage.isPasswordFieldDisplayed());
        assertTrue(loginPage.isLoginButtonDisplayed());

        assertEquals("Логин",loginPage.getLoginFieldText());
        assertEquals("Пароль",loginPage.getPasswordText());
        assertEquals("",loginPage.getErrorMessage());
    }

    @DisplayName("Неверный пароль")
    @Tag("negative")
    @Tag("login")
    @Order(2)
    @Test
    void testInvalidPassword() {
        loginPage.login("Login","123");
        loginPage.waitLoader();

        loginPage.setScreenshot("InvalidPassword");

        assertEquals("Введены неверные данные",loginPage.getErrorMessage());
    }

    @DisplayName("Неверный логин")
    @Tag("negative")
    @Tag("login")
    @Order(3)
    @Test
    void testInvalidLogin() {
        loginPage.login("123","Password");
        loginPage.waitLoader();

        loginPage.setScreenshot("InvalidLogin");

        assertEquals("Введены неверные данные",loginPage.getErrorMessage());
    }

    @DisplayName("Показать пароль")
    @Tag("positive")
    @Tag("login")
    @Order(4)
    @Test
    void testShowPassword() {
        loginPage.enterPassword("Password");

        assertEquals("true",loginPage.passwordIsHidden());
        loginPage.ScreenshotPasswordField("passwordHidden");

        loginPage.clickShowPassword();

        assertEquals("false",loginPage.passwordIsHidden());
        loginPage.ScreenshotPasswordField("passwordShow");
    }

    @DisplayName("Успершный вход")
    @Tag("positive")
    @Tag("login")
    @Order(5)
    @Test
    void testSuccessfulLogin() {
        loginPage.login("Login","Password");
        loginPage.waitLoader();

        loginPage.setScreenshot("SuccessfulLogin");

        assertTrue(firstPage.isLogoDisplayed());
        assertEquals("Вход в Alfa-Test выполнен", firstPage.getTitleLogo());
    }

    @AfterEach
    void tearDown() {
       Driver.quitDriver();
    }
}
