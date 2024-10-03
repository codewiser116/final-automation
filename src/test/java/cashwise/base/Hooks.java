package cashwise.base;

import cashwise.utils.ConfigReader;
import cashwise.utils.Driver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class Hooks {

    protected WebDriver driver = Driver.getDriver();
    protected static Logger logger = LogManager.getLogger(Hooks.class);
    protected Properties configProperties = ConfigReader.loadConfigurations();
    BaseTest baseTest = new BaseTest();
    protected Connection dbConnection;

    @Before
    public void setUp() {
        configProperties = ConfigReader.loadConfigurations();
        logger.info("Starting Test Setup");
//        baseTest.initializeWebDriver();
//        baseTest. initializeAPI();
//        baseTest.initializeDatabaseConnection();
        logger.info("Test Setup Completed");
    }

    @After
    public void tearDown() {
        logger.info("Starting Test Teardown");
        if (driver != null) {
            driver.quit();
            logger.info("WebDriver closed");
        }
//        if (dbConnection != null) {
//            try {
//                dbConnection.close();
//                logger.info("Database connection closed");
//            } catch (SQLException e) {
//                logger.error("Failed to close database connection", e);
//            }
//        }
        logger.info("Test Teardown Completed");
    }

}
