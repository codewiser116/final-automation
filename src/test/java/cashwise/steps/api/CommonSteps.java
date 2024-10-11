package cashwise.steps.api;

import cashwise.utils.ApiUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.record.pivottable.StreamIDRecord;
import org.junit.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonSteps extends ApiUtils{

    private static final Logger logger = LogManager.getLogger(CommonSteps.class);
    RequestSpecification requestSpecification;
    Response response;
    ObjectMapper objectMapper = new ObjectMapper();


    @Given("base url {string}")
    public void base_url(String baseUrl) {
        requestSpecification = RestAssured.given().baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);
    }
    @When("I provide valid authorization token")
    public void i_provide_valid_authorization_token() throws JsonProcessingException {
        requestSpecification = requestSpecification
                .auth().oauth2(getToken("qatester@gmail.com", "abc123!"));
    }
    @When("I provide request body with key and value")
    public void i_provide_request_body_key_with_value(DataTable dataTable) throws JsonProcessingException {
        List<Map<String, String>> data = dataTable.asMaps();

        Map<String, String> requestBody = new HashMap<>();

        for (Map<String, String> row : data) {
            String key = row.get("key");
            String value = row.get("value");
            requestBody.put(key, value);
        }

        String jsonBody = new ObjectMapper().writeValueAsString(requestBody);
        requestSpecification = requestSpecification.body(jsonBody);
    }
    @Then("I hit POST {string} endpoint")
    public void i_hit_post_endpoint(String endpoint) {
        response = requestSpecification.post(endpoint);
    }
    @Then("I verify status code is {int}")
    public void i_verify_status_code_is(Integer statusCode) {
        Assert.assertEquals((int) statusCode, response.getStatusCode());
    }
}
