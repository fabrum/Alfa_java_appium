import exp.appium.Driver;
import exp.pages.LoginPage;
import exp.pages.FirstPage;
import exp.utils.RegexSearch;
import exp.utils.ConfigReader;

import io.appium.java_client.AppiumDriver;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.provider.CsvSource;


import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SoftAssertionsExtension.class)
public class TestLogin {
    private LoginPage loginPage;
    private FirstPage firstPage;


    @BeforeAll
    static void setupAll() {
        System.out.println("=== Начало тестового прогона ===");
    }

    @BeforeEach
    void setUp() throws Exception {
        loginPage = new LoginPage();
        firstPage = new FirstPage();
    }

    @DisplayName("Запуск приложения")
    @Tag("positive")
    @Tag("login")
    @Test
    void testAppOpens() {
        loginPage.setScreenshot("OpenLoginPage");
        assertAll("проверка всех елементов",
                () -> assertEquals("Вход в Alfa-Test", loginPage.getTitleLogo(),"текст Title"),
                () -> assertEquals("Alfa", RegexSearch.findFirstAlfa(loginPage.getTitleLogo())),
                () -> assertTrue(loginPage.isUsernameFieldDisplayed()),
                () -> assertTrue(loginPage.isPasswordFieldDisplayed()),
                () -> assertTrue(loginPage.isLoginButtonDisplayed()),

                () -> assertEquals("Логин", loginPage.getLoginFieldText(),"значение пароля"),
                () -> assertEquals("Пароль", loginPage.getPasswordText()),
                () -> assertEquals("", loginPage.getErrorMessage())
        );
    }

    @DisplayName("Неверный пароль/логин")
    @Tag("negative")
    @Tag("login")
    @Test
    void testInvalidPasswordAndLogin(SoftAssertions softly) {
        String login = ConfigReader.getLogin();
        String password =ConfigReader.getPassword();
        loginPage.login(login, "pass");
        loginPage.waitLoader();

        loginPage.setScreenshot("InvalidPassword");

        softly.assertThat(loginPage.getErrorMessage())
                .isEqualTo("Введены неверные данные");

        loginPage.clearFields();

        loginPage.login("Log", password);
        loginPage.waitLoader();

        loginPage.setScreenshot("InvalidLogin");
        softly.assertThat(loginPage.getErrorMessage())
                .isEqualTo("Введены неверные данные");
    }

    @DisplayName("проверка максимвльной длинны для пароль/логин")
    @Tag("negative")
    @Tag("login")
    @Test
    void textFieldMaximumLength(SoftAssertions softly) {
        String string51 = "qewrkljhewrjklhqejklerjkerwjhklewrjkhqewkjhlqerwjlq";
        loginPage.setUsername(string51).setScreenshot("UserName51Symbol");

        softly.assertThat(loginPage.getLoginFieldText())
                .isEqualTo(string51.replaceFirst(".$", ""));
        loginPage.clearFields();

        loginPage.setPassword(string51);
        loginPage.setScreenshot("Password51Symbol");
        softly.assertThat(loginPage.getPasswordText())
                .isEqualTo(string51.replaceFirst(".$", ""));
    }

    @ParameterizedTest(name = "{index} - пароль проверка {2} ")
    @Tag("negative")
    @Tag("positive")
    @Tag("login")
    @CsvSource(value = {
            "qazeqSDQWEFA, PasswordFieldStringEng, латиницы",
            "фвййцвЯУКУЙц, PasswordFieldStringRus, русский",
            " . / ' _ -\\|$%&?+#, PasswordFieldStringSpecialCharacterAvailable, спец симвалы",
            "1234567890, PasswordFieldStringNumbers, цифры"
    })
    void textFieldValidationPassword(String string, String screenName, String name) {
        loginPage.setPassword(string);
        loginPage.setScreenshotPasswordField(screenName);
        assertEquals(string, loginPage.getPasswordText());
    }

    @ParameterizedTest(name = "{index} - логин проверка {3} ")
    @Tag("negative")
    @Tag("positive")
    @Tag("login")
    @CsvSource(value = {
            "qazeqSDQWEFA, LoginFieldTextStringEng, qazeqSDQWEFA, латиницы",
            "фвййцвЯУКУЙц, LoginFieldTextStringRus, , русский",
            " . / ' _ -, LoginFieldTextStringSpecialCharacterAvailable, . / ' _ -, спец симвалы разрешенные",
            "\\|$%&?+#, LoginFieldTextStringSpecialCharacterNotAvailable, , спец симвалы не разрешенные",
            "1234567890, LoginFieldTextStringNumbers, , цифры"
    })
    void textFieldValidationLogin(String string, String screenName,String rez ,String name) {
        loginPage.setUsername(string);
        loginPage.setScreenshotLoginField(screenName);
        rez = (rez==null)?"":rez;
        assertEquals(rez, loginPage.getLoginFieldText());
    }

    @DisplayName("Показать пароль")
    @Tag("positive")
    @Tag("login")
    @Test
    void testShowPassword(SoftAssertions softly) {
        loginPage.setPassword("Password");

        softly.assertThat(loginPage.passwordIsHidden()).isTrue();
        loginPage.setScreenshotPasswordField("passwordHidden");

        loginPage.clickShowPassword();
        softly.assertThat(loginPage.passwordIsHidden()).isFalse();
        loginPage.setScreenshotPasswordField("passwordShow");

        loginPage.clickShowPassword()        ;
        softly.assertThat(loginPage.passwordIsHidden()).isTrue();
        loginPage.setScreenshotPasswordField("passwordHiddenAgain");
    }

    @DisplayName("Успешный вход")
    @Tag("positive")
    @Tag("login")
    @Test
    void testSuccessfulLogin() {
        loginPage.login(ConfigReader.getLogin(), ConfigReader.getPassword());
        loginPage.waitLoader();

        loginPage.setScreenshot("SuccessfulLogin");

        assertAll("успешный вход",
                () -> assertTrue(firstPage.isLogoDisplayed()),
                () -> assertEquals("Вход в Alfa-Test выполнен", firstPage.getTitleLogo())
        );
    }

    @AfterEach
    void tearDown() {
        Driver.quitDriver();
    }
}
