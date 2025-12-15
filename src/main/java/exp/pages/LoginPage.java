package exp.pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;


public class LoginPage extends BasePage {

    @AndroidFindBy(xpath = "//android.widget.EditText[@resource-id='com.alfabank.qapp:id/etUsername' and @enabled='true']")
    private WebElement loginField;

    @AndroidFindBy(xpath = "//android.widget.EditText[@resource-id='com.alfabank.qapp:id/etPassword' and @enabled='true']")
    private WebElement passwordField;

    @AndroidFindBy(className = "android.widget.Button")
    private WebElement loginButton;

    @AndroidFindBy( xpath = "//android.widget.TextView[@resource-id='com.alfabank.qapp:id/tvError' and @displayed='true']")
    private WebElement errorMessage;

    @AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='com.alfabank.qapp:id/tvTitle']")
    private WebElement titleLogo;

    @AndroidFindBy(xpath = "//android.widget.ImageButton[@resource-id='com.alfabank.qapp:id/text_input_end_icon' and @enabled='true']")
    private WebElement showPassword;

    @AndroidFindBy(xpath = "//android.widget.FrameLayout[@resource-id='com.alfabank.qapp:id/loader']")
    private WebElement loader;

    public LoginPage enterUsername(String username) {
        typeText(loginField, username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        typeText(passwordField, password);
        return this;
    }

    public void clickLogin() {
        clickElement(loginButton);
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

    public void clickShowPassword() {
        clickElement(showPassword);
    }

    public boolean isUsernameFieldDisplayed() {
        return isElementDisplayed(loginField);
    }

    public boolean isPasswordFieldDisplayed() {
        return isElementDisplayed(passwordField);
    }

    public boolean isLoginButtonDisplayed() {
        return isElementDisplayed(loginButton);
    }

    public void clearFields() {
        loginField.clear();
        passwordField.clear();
    }

    public String passwordIsHidden() {
        return  getAttribute(passwordField,"password");
    }

    public String getPasswordText() {
        return  getText(passwordField);
    }

    public String getLoginFieldText() {
        return  getText(loginField);
    }

    public void waitLoader() {
        waitUntilElementIsVisible(loader);
    }

    public String getTitleLogo() {
        return getText(titleLogo);
    }

    public void ScreenshotPasswordField(String name) {
        setElementScreenshot(passwordField, name);
    }
}