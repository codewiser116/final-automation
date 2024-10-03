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

    @FindBy(xpath = "//button[text()='Continue']")
    public WebElement continueBtn;

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

    @FindBy(id = "//li[@data-value='USD']")
    private WebElement currencyOption;

    @FindBy(xpath = "//button[@form='register-form-2']")
    private WebElement secondSignBtn;


    public void singUp(){
        signUpBtn.click();
        emailField.sendKeys(faker.internet().emailAddress());
        String password = faker.internet().password();
        passwordField.sendKeys(password);
        confirmPasswordField.sendKeys(password);
        continueBtn.click();

        firstNameInput.sendKeys(faker.name().firstName());
        lastnameInput.sendKeys(faker.name().lastName());
        companyInput.sendKeys(faker.company().name());
        areaOfBusiness.click();



    }


}







