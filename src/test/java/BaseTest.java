import exp.appium.Driver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {
    @BeforeAll
    static void setupAll() {
        System.out.println("=== Начало тестового прогона ===");
    }

    @AfterEach
    void tearDown() {
        Driver.quitDriver();
    }
}
