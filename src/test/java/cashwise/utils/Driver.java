package cashwise.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

public class Driver {

    protected static Logger logger = LogManager.getLogger(Driver.class);
    static WebDriver driver;


    public static WebDriver getDriver() {
        String browserType = ConfigReader.getProperty("browser");

        if (driver != null) {
            return driver;
        }

        switch (browserType.toLowerCase()) {
            case "chrome":
                driver = new ChromeDriver();
                break;
            case "firefox":
                driver = new FirefoxDriver();
                break;
            case "edge":
                driver = new EdgeDriver();
                break;
            default:
                logger.error("Unsupported browser type: " + browserType);
                throw new IllegalArgumentException("Unsupported browser type: " + browserType);
        }
        int timeInSeconds = Integer.parseInt(ConfigReader.getProperty("pageLoadTimeoutInSeconds"));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeInSeconds));

        driver.manage().window().maximize();
        return driver;
    }

    public static void closeDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}



