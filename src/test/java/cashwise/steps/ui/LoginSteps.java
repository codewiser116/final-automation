package cashwise.steps.ui;

import cashwise.base.BaseTest;
import cashwise.utils.Driver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

public class LoginSteps extends BaseTest {

    @Given("user goes to {string}")
    public void user_goes_to(String url) {
        driver = initializeWebDriver();
        driver.get(url);
    }

    @When("user signs up with valid credentials")
    public void user_signs_up_with_valid_credentials() {

    }

    @Then("verify user signed up successfully")
    public void verify_user_signed_up_successfully() {

    }


}
