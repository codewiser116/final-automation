package cashwise.base;

import cashwise.utils.ConfigReader;
import io.restassured.RestAssured;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.junit.Before;
import org.junit.After;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Properties;
import java.io.File;
import java.io.IOException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class BaseTest {

    protected WebDriver driver;
    protected static Logger logger = LogManager.getLogger(BaseTest.class);
    protected Properties configProperties = ConfigReader.loadConfigurations();
    protected Connection dbConnection;

    @Before
    public void setUp() {
        configProperties = ConfigReader.loadConfigurations();
        logger.info("Starting Test Setup");
        initializeWebDriver();
        initializeAPI();
        initializeDatabaseConnection();
        logger.info("Test Setup Completed");
    }

    @After
    public void tearDown() {
        logger.info("Starting Test Teardown");
        if (driver != null) {
            driver.quit();
            logger.info("WebDriver closed");
        }
        if (dbConnection != null) {
            try {
                dbConnection.close();
                logger.info("Database connection closed");
            } catch (SQLException e) {
                logger.error("Failed to close database connection", e);
            }
        }
        logger.info("Test Teardown Completed");
    }

    protected WebDriver initializeWebDriver() {
        String browserType = configProperties.getProperty("browser");
        long implicitWait = Long.parseLong(configProperties.getProperty("implicit.wait", "10"));
        switch (browserType.toLowerCase()) {
            case "chrome":
                driver = new ChromeDriver();
                logger.info("ChromeDriver initialized");
                break;
            case "firefox":
                driver = new FirefoxDriver();
                logger.info("FirefoxDriver initialized");
                break;
            case "edge":
                driver = new EdgeDriver();
                logger.info("EdgeDriver initialized");
                break;
            default:
                logger.error("Unsupported browser type: " + browserType);
                throw new IllegalArgumentException("Unsupported browser type: " + browserType);
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
        driver.manage().window().maximize();
        return driver;
    }

    protected void initializeAPI() {
        RestAssured.baseURI = configProperties.getProperty("api.base.uri");
        logger.info("RestAssured initialized with base URI: " + RestAssured.baseURI);
    }

    protected void initializeDatabaseConnection() {
        String dbUrl = configProperties.getProperty("db.url");
        String dbUser = configProperties.getProperty("db.user");
        String dbPassword = configProperties.getProperty("db.password");
        try {
            dbConnection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            logger.info("Database connection established");
        } catch (SQLException e) {
            logger.error("Failed to establish database connection", e);
            throw new RuntimeException("Could not establish database connection", e);
        }
    }

    protected void takeScreenshot(String filePath) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(source, new File(filePath));
            logger.info("Screenshot saved to: " + filePath);
        } catch (IOException e) {
            logger.error("Failed to take a screenshot", e);
            throw new RuntimeException("Failed to take a screenshot", e);
        }
    }
}
