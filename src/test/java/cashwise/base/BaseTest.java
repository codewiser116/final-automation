package cashwise.base;

import cashwise.utils.ConfigReader;
import cashwise.utils.Driver;
import io.restassured.RestAssured;
import org.openqa.selenium.WebDriver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.File;
import java.io.IOException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseTest {

    protected WebDriver driver = Driver.getDriver();
    protected static Logger logger = LogManager.getLogger(BaseTest.class);
    protected Properties configProperties = ConfigReader.loadConfigurations();
    protected Connection dbConnection;

    protected WebDriver initializeWebDriver(){
        return driver = Driver.getDriver();

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
