package exp.appium;

import exp.utils.ConfigReader;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import java.net.MalformedURLException;
import java.net.URL;

public class Driver {
    private static final ThreadLocal<AppiumDriver> driverThread = new ThreadLocal<>();

    private Driver() {}

    public static AppiumDriver getDriver() {
        if (driverThread.get()== null) {
            initializeDriver();
        }
        return driverThread.get();
    }

    private static void initializeDriver() {
        try {
            UiAutomator2Options options = new UiAutomator2Options()
                    .setDeviceName(ConfigReader.getAndroidDeviceName())
                    .setPlatformVersion(ConfigReader.getAndroidVersion())
                    .setApp(System.getProperty("user.dir").replace("\\", "/") + ConfigReader.getApkPath())
                    .setAppPackage(ConfigReader.getApkPackage())
                    .setNewCommandTimeout(ConfigReader.getAppiumCommandTimeout())
                    .setFullReset(ConfigReader.getAppiumReset());

            URL url = new URL(ConfigReader.getAppiumUrl());
            driverThread.set(new AndroidDriver(url, options));
            System.out.println("Драйвер создан!");

        } catch (MalformedURLException e) {
            throw new RuntimeException("Ошибка URL Appium сервера", e);
        }
    }

    public static void quitDriver() {
        if (driverThread.get() != null) {
            driverThread.get().quit();
            driverThread.set(null);
            System.out.println("Драйвер закрыт");
        }
    }

    public static void getAllElementConsole(){
        System.out.println(driverThread.get().getPageSource());
    }
}
