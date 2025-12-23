package exp.utils;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;


public class ConfigReader {


    private static Properties properties = new Properties();

    static {
        loadProperties();
    }

    private static void loadProperties() {
        try (InputStream input = ConfigReader.class.getClassLoader()
                .getResourceAsStream("config.properties")) {

            if (input == null) {
                throw new RuntimeException("Не найден config.properties файл");
            }

            properties.load(input);

        } catch (IOException e) {
            throw new RuntimeException("Ошибка загрузки config.properties", e);
        }
    }

    public static String getPassword() {
     return properties.getProperty("user.password");
    }

    public static String getLogin() {
        return properties.getProperty("user.login");
    }

    public static String getApkPath() {
        return properties.getProperty("app.path");
    }

    public static String getApkPackage() {
        return properties.getProperty("com.alfabank.qapp");
    }

    public static String getAndroidVersion() {
        return properties.getProperty("android.platform.version");
    }

    public static String getAndroidDeviceName() {
        return properties.getProperty("android.device.name");
    }

    public static  String getAppiumUrl() {
        return properties.getProperty("appium.server.url");
    }

    public static Duration getAppiumCommandTimeout() {
        String timeoutStr = properties.getProperty("appium.command.timeout");
        return Duration.ofMillis(Integer.parseInt(timeoutStr));

    }
    public static Boolean getAppiumReset() {
        return Boolean.parseBoolean(properties.getProperty("appium.full.reset"));
    }

}
