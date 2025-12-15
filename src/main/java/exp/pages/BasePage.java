package exp.pages;

import exp.appium.Driver;
import exp.utils.WaitUtils;
import exp.utils.ScreenshotUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;
import java.time.Duration;

public class BasePage {
    protected AppiumDriver driver;
    protected WaitUtils waitUtils;
    protected ScreenshotUtils screenshotUtils;

    public BasePage() {
        this.driver = Driver.getDriver();
        this.waitUtils = new WaitUtils();
        this.screenshotUtils = new ScreenshotUtils();

        PageFactory.initElements(
                new AppiumFieldDecorator(driver, Duration.ofSeconds(10)),
                this
        );
    }

    protected void clickElement(org.openqa.selenium.WebElement element) {
        waitUtils.waitForElementToBeClickable(element);
        element.click();
    }

    protected void typeText(org.openqa.selenium.WebElement element, String text) {
        waitUtils.waitForElementVisible(element);
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(org.openqa.selenium.WebElement element) {
        waitUtils.waitForElementVisible(element);
        return element.getText();
    }

    protected boolean isElementDisplayed(org.openqa.selenium.WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected void waitUntilElementIsVisible(org.openqa.selenium.WebElement element) {
         waitUtils.waitForElementInvisible(element);
    }

    protected String getAttribute(org.openqa.selenium.WebElement element, String attribute) {
        waitUtils.waitForElementVisible(element);
        return element.getAttribute(attribute);
    }

    protected void  setElementScreenshot(org.openqa.selenium.WebElement element,String name) {
        screenshotUtils.captureElementScreenshot(element, name);
    }

    public void setScreenshot(String name) {
        screenshotUtils.captureScreenshot(name);
    }


    public void goBack() {
        driver.navigate().back();
    }
}
