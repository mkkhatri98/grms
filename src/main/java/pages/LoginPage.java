package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    private WebDriver driver;

    @FindBy(xpath = "//*[@id=\"mat-input-0\"]")
    private WebElement usernameField;

    @FindBy(xpath = "//*[@id=\"mat-input-1\"]")
    private WebElement passwordField;

    @FindBy(xpath = "/html/body/app-root/app-simple/main/div/app-login/div/div[2]/form/button")
    private WebElement loginButton;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void login(String username, String password) {
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        loginButton.click();

    }
}