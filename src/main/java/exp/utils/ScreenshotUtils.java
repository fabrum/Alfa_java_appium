package exp.utils;

import exp.appium.Driver;
import org.openqa.selenium.*;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtils {
    private WebDriver driver;
    private String basePath;
    private static String timestamp;

    public ScreenshotUtils() {
        this.driver = Driver.getDriver();;
        this.basePath = System.getProperty("user.dir") + "/screenshots/";
        createDirectory(basePath);
        setTimestamp(basePath);
    }

    private static String getTimestamp() {
        if (timestamp == null) {
            timestamp= new SimpleDateFormat("yyyy-MM-dd_HH-mm").format(new Date());
        }
        return timestamp;
    }

    public ScreenshotUtils(WebDriver driver, String customPath) {
        this.driver = driver;
        this.basePath = customPath;
        createDirectory(basePath);
        setTimestamp(basePath);
    }

    private void setTimestamp(String path) {
        String timestampString =getTimestamp();
        this.basePath= path + timestampString + "/";
        createDirectory(basePath);
    }

    private void createDirectory(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public String captureScreenshot(String screenshotName) {
        return captureScreenshot(screenshotName, basePath);
    }

    public String captureScreenshot(String screenshotName, String directoryPath) {
        try {
            createDirectory(directoryPath);
            String fileName = screenshotName  + ".png";
            String filePath = directoryPath + fileName;

            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destFile = new File(filePath);

            FileUtils.copyFile(srcFile, destFile);

            System.out.println("Screenshot saved: " + filePath);
            return filePath;

        } catch (IOException e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
            return null;
        }
    }


    public String captureElementScreenshot(WebElement element, String elementName) {
        try {
            String fileName = elementName + ".png";
            String filePath = basePath + "elements/" + fileName;
            createDirectory(basePath + "elements/");

            File screenshot = element.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(filePath));

            System.out.println("Screenshot saved: " + filePath);

            return filePath;
        } catch (Exception e) {
            System.err.println("Failed to capture element screenshot: " + e.getMessage());
            return null;
        }
    }
}
