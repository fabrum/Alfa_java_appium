package exp.pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class FirstPage extends BasePage {
    @AndroidFindBy(xpath = "//android.widget.TextView[@class='android.widget.TextView']")
    private WebElement titleLogo;

    public String getTitleLogo() {
        return getText(titleLogo);
    }

    public boolean isLogoDisplayed() {
        return isElementDisplayed(titleLogo);
    }
}
