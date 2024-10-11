package cashwise.steps.ui;

import cashwise.base.BaseTest;
import cashwise.pages.ui.HomePage;
import cashwise.pages.ui.LoginPage;
import cashwise.utils.Driver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

public class LoginSteps extends BaseTest {


    LoginPage loginPage = new LoginPage(Driver.getDriver());
    HomePage homePage = new HomePage(Driver.getDriver());

    public LoginSteps() {
        super();
    }

    @Given("user goes to {string}")
    public void user_goes_to(String url) {
        driver = initializeWebDriver();
        driver.get(url);
    }

    @When("user signs up with valid credentials")
    public void user_signs_up_with_valid_credentials() throws InterruptedException {
        loginPage.signUp();
        System.out.println(loginPage.dataStorage.get("email"));
    }

    @Then("verify user signed up successfully")
    public void verify_user_signed_up_successfully() throws InterruptedException {
//        Assert.assertTrue(driver.getCurrentUrl().contains("https://cashwise.us/dashboard/infographics"));

        String expectedName = loginPage.dataStorage.get("first_name") + " "
                +loginPage.dataStorage.get("last_name");

        waitForTextToAppear(20, homePage.fullName, expectedName);
        System.out.println(expectedName);
        System.out.println(homePage.fullName.getText().replace("0\n", ""));
        Assert.assertTrue(expectedName.contains( homePage.fullName.getText()));
    }
}
