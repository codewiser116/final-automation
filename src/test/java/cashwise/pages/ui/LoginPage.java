package cashwise.pages.ui;

import cashwise.base.BasePage;
import cashwise.utils.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {

    WebDriver driver;

    public LoginPage(WebDriver driver){
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//button[text()='Sign up']")
    public WebElement signUpBtn;

    @FindBy(id = "email_input_text")
    public WebElement emailField;

    @FindBy(id = "password_input_text")
    public WebElement passwordField;

    @FindBy(id = "repeat_password_input_text")
    public WebElement confirmPasswordField;

    @FindBy(id = "//button[text()='Continue']")
    public WebElement continueBtn;







}
