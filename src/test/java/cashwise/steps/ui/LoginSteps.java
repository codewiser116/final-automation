package cashwise.steps.ui;

import cashwise.base.BaseTest;
import io.cucumber.java.en.Given;
import org.openqa.selenium.WebDriver;

public class LoginSteps extends BaseTest {

    @Given("user goes to {string}")
    public void user_goes_to(String url) {
        driver = initializeWebDriver();
        driver.get(url);
    }

}
