package cashwise.utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;



import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.*;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ApiUtils {

    private static final Logger logger = LogManager.getLogger(ApiUtils.class);

    /**
     * Sets the base URI for REST Assured.
     * @param baseUri The base URI of the API.
     */
    public static void setBaseUri(String baseUri) {
        RestAssured.baseURI = baseUri;
        logger.info("Base URI set to: " + baseUri);
    }

    /**
     * Adds default headers to the REST Assured request specification.
     * @param headers A map of header key-value pairs.
     * @return The updated RequestSpecification.
     */
    public static RequestSpecification addHeaders(Map<String, String> headers) {
        return RestAssured.given().headers(headers);
    }

    /**
     * Sends a GET request to the specified endpoint.
     * @param endpoint The API endpoint.
     * @param queryParams A map of query parameters.
     * @return The Response object.
     */
    public static Response sendGetRequest(String endpoint, Map<String, String> queryParams) {
        logger.info("Sending GET request to: " + endpoint);
        Response response = RestAssured
                .given()
                .queryParams(queryParams)
                .when()
                .get(endpoint);
        logResponse(response);
        return response;
    }

    /**
     * Sends a POST request with JSON payload.
     * @param endpoint The API endpoint.
     * @param body The request body as a Java object.
     * @return The Response object.
     */
    public static Response sendPostRequest(String endpoint, Object body) {
        logger.info("Sending POST request to: " + endpoint);
        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post(endpoint);
        logResponse(response);
        return response;
    }

    /**
     * Validates the response status code.
     * @param response The Response object.
     * @param expectedStatusCode The expected status code.
     */
    public static void validateStatusCode(Response response, int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        if (actualStatusCode != expectedStatusCode) {
            logger.error("Expected status code: " + expectedStatusCode + ", but got: " + actualStatusCode);
            throw new AssertionError("Status code mismatch");
        } else {
            logger.info("Status code validated: " + actualStatusCode);
        }
    }

    /**
     * Logs the response details.
     * @param response The Response object.
     */
    public static void logResponse(Response response) {
        logger.info("Response Status Code: " + response.getStatusCode());
        logger.info("Response Body: " + response.getBody().asString());
    }


    /**
     * Reads a file and returns its content as a String.
     * @param filePath The path to the file.
     * @return The file content as a String.
     * @throws IOException
     */
    public static String readFileAsString(String filePath) throws IOException {
        logger.info("Reading file: " + filePath);
        return new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(filePath)));
    }

    /**
     * Converts an object to a JSON String using Jackson.
     * @param object The object to convert.
     * @return The JSON representation of the object.
     * @throws IOException
     */
    public static String objectToJson(Object object) throws IOException {
        com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

    /**
     * Converts a JSON String to an object of the specified class using Jackson.
     * @param json The JSON String.
     * @param clazz The Class of the object.
     * @return The deserialized object.
     * @throws IOException
     */
    public static <T> T jsonToObject(String json, Class<T> clazz) throws IOException {
        com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

    /**
     * Adds basic authentication to the REST Assured request.
     * @param username The username.
     * @param password The password.
     * @return The updated RequestSpecification.
     */
    public static RequestSpecification addBasicAuth(String username, String password) {
        return RestAssured.given().auth().preemptive().basic(username, password);
    }

    /**
     * Adds OAuth 2.0 authentication token to the REST Assured request.
     * @param token The OAuth 2.0 token.
     * @return The updated RequestSpecification.
     */
    public static RequestSpecification addOAuth2Token(String token) {
        return RestAssured.given().auth().oauth2(token);
    }

}