import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class TestClass {

    protected WebDriver driver;

    @BeforeEach
    public void setUp() throws MalformedURLException {
        String selenoidURL = "https://selenoid.teletrade-dj.com/wd/hub";
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setBrowserName("chrome");
        caps.setVersion("88.0");
        caps.setCapability("enableVNC", true);
        caps.setCapability("screenResolution", "1920x1080");
        caps.setCapability("enableVideo", false);
        caps.setCapability("enableLog", true);

        driver = new RemoteWebDriver(new URL(selenoidURL), caps);

    }
    @Execution(CONCURRENT)
    @Test
    public void openPage() {
        driver.get("https://otus.ru/");
        String title = driver.getTitle();
        assertEquals(title, "Онлайн‑курсы для профессионалов, дистанционное обучение современным профессиям");
    }

    @Execution(CONCURRENT)
    @Test
    public void otherTest() {
        driver.get("https://habr.com/ru/");
        String title = driver.getTitle();
        assertNotEquals(title, "Онлайн‑курсы для профессионалов, дистанционное обучение современным профессиям");
    }

    @AfterEach
    public void setDown() {
        if (driver != null) {
            driver.quit();
        }

    }

}
