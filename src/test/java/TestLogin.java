import exp.appium.Driver;
import exp.pages.LoginPage;
import exp.pages.FirstPage;

import io.appium.java_client.AppiumDriver;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
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

    @DisplayName("Неверный пароль/логин")
    @Tag("negative")
    @Tag("login")
    @Test
    void testInvalidPassword() {
        loginPage.login("Login","pass");
        loginPage.waitLoader();

        loginPage.setScreenshot("InvalidPassword");

        assertEquals("Введены неверные данные",loginPage.getErrorMessage());

        loginPage.clearFields();

        loginPage.login("Log","Password");
        loginPage.waitLoader();

        loginPage.setScreenshot("InvalidLogin");

        assertEquals("Введены неверные данные",loginPage.getErrorMessage());
    }

    @DisplayName("проверка максимвльной длинны для пароль/логин")
    @Tag("negative")
    @Tag("login")
    @Test
    void textFieldMaximumLength() {
        String string51="qewrkljhewrjklhqejklerjkerwjhklewrjkhqewkjhlqerwjlq";
        loginPage.setUsername(string51);
        loginPage.setScreenshot("UserName51Symbol");
        assertEquals(string51.replaceFirst(".$", ""),loginPage.getLoginFieldText());

        loginPage.clearFields();

        loginPage.setPassword(string51);
        loginPage.setScreenshot("Password51Symbol");
        assertEquals(string51.replaceFirst(".$", ""),loginPage.getPasswordText());
    }

    @DisplayName("проверка допуступных сивалов пароль/логин")
    @Tag("negative")
    @Tag("positive")
    @Tag("login")
    @Test
    void textFieldValidation() {
        String stringEng="qazeqSDQWEFA";
        String stringRus="фвййцвЯУКУЙц";
        String stringSpecialCharacterAvailable=" . , / ' _ -";
        String stringSpecialCharacterNotAvailable="\\|$%&?+#";
        String stringNumbers="1234567890";

        //Login
        loginPage.setUsername(stringEng);
        loginPage.setScreenshotLoginField("LoginFieldTextStringEng");
        assertEquals(stringEng,loginPage.getLoginFieldText());
        loginPage.clearFields();

        loginPage.setUsername(stringRus);
        loginPage.setScreenshotLoginField("LoginFieldTextStringRus");
        assertEquals("",loginPage.getLoginFieldText());
        loginPage.clearFields();

        loginPage.setUsername(stringSpecialCharacterAvailable);
        loginPage.setScreenshotLoginField("LoginFieldTextStringSpecialCharacterAvailable");
        assertEquals(stringSpecialCharacterAvailable,loginPage.getLoginFieldText());
        loginPage.clearFields();

        loginPage.setUsername(stringSpecialCharacterNotAvailable);
        loginPage.setScreenshotLoginField("LoginFieldTextStringSpecialCharacterNotAvailable");
        assertEquals("",loginPage.getLoginFieldText());
        loginPage.clearFields();

        loginPage.setUsername(stringNumbers);
        loginPage.setScreenshotLoginField("LoginFieldTextStringNumbers");
        assertEquals("",loginPage.getLoginFieldText());
        loginPage.clearFields();

        //Password
        loginPage.clickShowPassword();

        loginPage.setPassword(stringEng);
        loginPage.setScreenshotPasswordField("PasswordFieldStringEng");
        assertEquals(stringEng,loginPage.getPasswordText());
        loginPage.clearFields();

        loginPage.setPassword(stringRus);
        loginPage.setScreenshotPasswordField("PasswordFieldStringRus");
        assertEquals(stringRus,loginPage.getPasswordText());
        loginPage.clearFields();

        loginPage.setPassword(stringSpecialCharacterAvailable);
        loginPage.setScreenshotPasswordField("PasswordFieldStringSpecialCharacterAvailable");
        assertEquals(stringSpecialCharacterAvailable,loginPage.getPasswordText());
        loginPage.clearFields();

        loginPage.setPassword(stringSpecialCharacterNotAvailable);
        loginPage.setScreenshotPasswordField("PasswordFieldStringSpecialCharacterNotAvailable");
        assertEquals(stringSpecialCharacterNotAvailable,loginPage.getPasswordText());
        loginPage.clearFields();

        loginPage.setPassword(stringNumbers);
        loginPage.setScreenshotPasswordField("PasswordFieldStringNumbers");
        assertEquals(stringNumbers,loginPage.getPasswordText());
        loginPage.clearFields();
    }

    @DisplayName("Показать пароль")
    @Tag("positive")
    @Tag("login")
    @Test
    void testShowPassword() {
        loginPage.setPassword("Password");

        assertEquals("true",loginPage.passwordIsHidden());
        loginPage.setScreenshotPasswordField("passwordHidden");

        loginPage.clickShowPassword();

        assertEquals("false",loginPage.passwordIsHidden());
        loginPage.setScreenshotPasswordField("passwordShow");

        loginPage.clickShowPassword()
        ;
        assertEquals("true",loginPage.passwordIsHidden());
        loginPage.setScreenshotPasswordField("passwordHiddenAgain");
    }

    @DisplayName("Успешный вход")
    @Tag("positive")
    @Tag("login")
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
