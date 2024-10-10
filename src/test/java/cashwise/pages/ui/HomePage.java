package cashwise.pages.ui;

import cashwise.base.BasePage;
import cashwise.base.BaseTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends BasePage {
    WebDriver driver;

    @FindBy(xpath = "//p[@aria-label='Account settings']")
    private WebElement userName;
    public HomePage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, LoginPage.class);
    }

    public String getUser(){
        System.out.println(userName.getText());



        return userName.getText();
    }

}
