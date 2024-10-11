package cashwise.pages.ui;

import cashwise.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {

    WebDriver driver;

    public LoginPage(WebDriver driver){
        super();
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
    public WebElement firstName;

    @FindBy(id = "last_name_input_text")
    public WebElement lastName;

    @FindBy(id = "company_name_input_text")
    public WebElement nameOfBusiness;

    @FindBy(id = "mui-component-select-business_area_id")
    public WebElement areaOfBusinessDropdown;

    @FindBy(xpath = "//li[text()='IT, Software Development']")
    public WebElement businessOption;

    @FindBy(id = "address_input_text")
    public WebElement address;

    @FindBy(id = "mui-component-select-currency")
    public WebElement currencyDropDown;

    @FindBy(xpath = "//li[@data-value='USD']")
    public WebElement currencyOption;

    @FindBy(xpath = "//button[@form='register-form-2']")
    public WebElement secondSignUpBtn;

    public void signUp(){
        signUpBtn.click();

        String email = faker.internet().emailAddress();
        dataStorage.addData("email", email);
        emailField.sendKeys(email);

        String password = faker.internet().password();
        dataStorage.addData("password", password);
        passwordField.sendKeys(password);
        confirmPasswordField.sendKeys(password);

        click(continueBtn);

        String first_name = faker.name().firstName();
        firstName.sendKeys(first_name);
        dataStorage.addData("first_name", first_name);

        String last_name = faker.name().lastName();
        lastName.sendKeys(last_name);
        dataStorage.addData("last_name", last_name);

        nameOfBusiness.sendKeys(faker.company().name());

        areaOfBusinessDropdown.click();
        businessOption.click();

        address.sendKeys(faker.address().fullAddress());

        currencyDropDown.click();
        currencyOption.click();

        waitForElementToBeClickable(secondSignUpBtn, 20);
        secondSignUpBtn.click();
    }

}
