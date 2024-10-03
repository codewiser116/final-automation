package cashwise.base;

import cashwise.utils.ConfigReader;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * BaseApiTest is a base class for all API test classes.
 * It sets up common configurations such as base URI, authentication,
 * and request specifications for API testing.
 */
public class BaseApiTest {

    // Logger instance for logging
    protected static final Logger logger = LogManager.getLogger(BaseApiTest.class);

    // Properties object to hold configuration properties
    protected Properties configProperties = ConfigReader.loadConfigurations();

    // Common RequestSpecification for RestAssured
    protected RequestSpecification requestSpec;

//    /**
//     * This method runs before any test method in the class.
//     * It loads API configurations and initializes RestAssured settings.
//     */
//    @Before
//    public void setUpApi() {
//
//        // Initialize RestAssured settings
//        initializeRestAssured();
//        logger.info("API Test Setup Completed");
//    }
//
//    /**
//     * This method runs after all test methods in the class have been run.
//     * It can be used for any cleanup if necessary.
//     */
//    @After
//    public void tearDownApi() {
//        // Perform any necessary cleanup
//        logger.info("API Test Teardown Completed");
//    }

    /**
     * Initializes RestAssured configurations for API testing.
     */
    protected void initializeRestAssured() {
        // Set base URI
        String baseUri = configProperties.getProperty("api.base.uri");
        RestAssured.baseURI = baseUri;
        logger.info("RestAssured base URI set to: " + baseUri);

        // Set up default request specification
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri(baseUri);

        // Add common headers if any
        String apiKey = configProperties.getProperty("api.key");
        if (apiKey != null) {
            builder.addHeader("API-Key", apiKey);
            logger.info("API key added to request headers");
        }

        // Set up authentication if necessary
        String authType = configProperties.getProperty("api.auth.type");
        if ("basic".equalsIgnoreCase(authType)) {
            String username = configProperties.getProperty("api.auth.username");
            String password = configProperties.getProperty("api.auth.password");
            builder.setAuth(RestAssured.basic(username, password));
            logger.info("Basic authentication configured");
        } else if ("oauth2".equalsIgnoreCase(authType)) {
            String token = configProperties.getProperty("api.auth.token");
            builder.setAuth(RestAssured.oauth2(token));
            logger.info("OAuth2 authentication configured");
        }

        // Build the RequestSpecification
        requestSpec = builder.build();

        // Set the default request specification for RestAssured
        RestAssured.requestSpecification = requestSpec;
        logger.info("RestAssured request specification initialized");
    }

    // Additional utility methods specific to API testing can be added here
}

