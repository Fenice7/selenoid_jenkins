import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class TestClass {

    protected WebDriver driver;

    @BeforeEach
    void setUp() throws MalformedURLException {
//        String selenoidURL = "http://195.178.20.182:4444/wd/hub";
//        DesiredCapabilities caps = new DesiredCapabilities();
//        caps.setBrowserName("chrome");
//        caps.setVersion("88.0");
//        caps.setCapability("enableVNC", true);
//        caps.setCapability("screenResolution", "1920x1080");
//        caps.setCapability("enableVideo", false);
//        caps.setCapability("enableLog", true);
//
//        driver = new RemoteWebDriver(new URL(selenoidURL), caps);

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

    }

    @Test
    void openPage() {
        driver.get("https://otus.ru/");
        String title = driver.getTitle();
        assertEquals(title, "Онлайн‑курсы для профессионалов, дистанционное обучение современным профессиям");


    }

    @AfterEach
    void setDown() {
        if (driver != null) {
            driver.quit();
        }

    }

}
