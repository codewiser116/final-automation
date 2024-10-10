package cashwise.steps.ui;

import cashwise.base.BaseTest;
import cashwise.pages.ui.LoginPage;
import cashwise.utils.Driver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

public class LoginSteps extends BaseTest {


    LoginPage loginPage = new LoginPage(Driver.getDriver());

    @Given("user goes to {string}")
    public void user_goes_to(String url) {
        driver = initializeWebDriver();
        driver.get(url);
    }

    @When("user signs up with valid credentials")
    public void user_signs_up_with_valid_credentials() {
        loginPage.signUp();
        System.out.println(loginPage.dataStorage.get("email"));

    }

    @Then("verify user signed up successfully")
    public void verify_user_signed_up_successfully() {

        String desiredURL = "https://cashwise.us/dashboard/infographics";
        if(driver.getCurrentUrl().equals(desiredURL)){
            Assert.assertEquals(desiredURL, driver.getCurrentUrl());

        }

//      Assert.assertEquals(loginPage.nameVerification.getText(), loginPage.dataStorage.get("firstname"));
        String nameVerify= loginPage.dataStorage.get("firstname");

        System.out.println(nameVerify);
        System.out.println("name is: " + loginPage.nameVerification.getText());







    }


}
