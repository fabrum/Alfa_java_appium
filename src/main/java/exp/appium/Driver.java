package exp.appium;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class Driver {
    private static AppiumDriver driver;

    private Driver() {}

    public static AppiumDriver getDriver() {
        if (driver == null) {
            initializeDriver();
        }
        return driver;
    }

    private static void initializeDriver() {
        try {
            UiAutomator2Options options = new UiAutomator2Options()
                    .setDeviceName("emulator-5554")
                    .setPlatformVersion("16.0")
                    .setApp(System.getProperty("user.dir").replace("\\", "/") + "/test-apps/app-debug.apk")
                    .setAppPackage("com.alfabank.qapp")
                    .setNewCommandTimeout(Duration.ofMillis(300))
                    .setFullReset(true);

            URL url = new URL("http://127.0.0.1:4723");

            driver = new AndroidDriver(url, options);
            System.out.println("Драйвер создан!");

        } catch (MalformedURLException e) {
            throw new RuntimeException("Ошибка URL Appium сервера", e);
        }
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
            System.out.println("Драйвер закрыт");
        }
    }

    public static void getAllElementConsole(){
        System.out.println(driver.getPageSource());
    }
}
