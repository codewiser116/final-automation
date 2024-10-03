package cashwise.pages.ui;

import cashwise.base.BasePage;
import cashwise.base.BaseTest;
import cashwise.utils.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {
    WebDriver driver;
    public LoginPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, LoginPage.class);
    }

    @FindBy(xpath = "//button[text()='Sign up']")
    private WebElement signUpBtn;

    @FindBy(id = "email_input_text")
    private WebElement emailInput;

    @FindBy(id = "password_input_text")
    private WebElement passwordInput;

    @FindBy(id = "repeat_password_input_text")
    private WebElement passwordRepeatInput;

    @FindBy(xpath = "//button[text()= 'Continue']")
    private WebElement continueBtn;




    @FindBy(id = "first_name_input_text")
    private WebElement firstNameInput;

    @FindBy(id = "last_name_input_text")
    private WebElement lastnameInput;


    @FindBy(id = "company_name_input_text")
    private WebElement companyInput;

    @FindBy(id = "mui-component-select-business_area_id")
    private WebElement areaOfBusiness;

    @FindBy(id = "mui-component-select-currency")
    private WebElement selectCurrency;

    @FindBy(xpath = "//button[@form='register-form-2']")
    private WebElement signUpBtn2;


}
