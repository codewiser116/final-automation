package cashwise.pages.ui;

import cashwise.base.BasePage;
import cashwise.utils.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {

    WebDriver driver;

    public LoginPage(WebDriver driver){
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, LoginPage.class);
    }







}
