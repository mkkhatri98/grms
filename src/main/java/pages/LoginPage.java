package pages;

import base.BaseTest;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
public class LoginPage{
    private static final Logger logger = LoggerFactory.getLogger(LoginPage.class);
    private WebDriver driver;
    private Wait<WebDriver> wait;

    @FindBy(xpath = "//*[@id=\"mat-input-0\"]")
    private WebElement usernameField;

    @FindBy(xpath = "//*[@id=\"mat-input-1\"]")
    private WebElement passwordField;

    @FindBy(xpath = "/html/body/app-root/app-simple/main/div/app-login/div/div[2]/form/button")
    private WebElement loginButton;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(2))
                .ignoring(NoSuchElementException.class);
        PageFactory.initElements(driver, this);
    }

    public void login(String username, String password) {
        logger.info("Attempting login with username: {}", username);
        usernameField.sendKeys(username);
        logger.info("Entered username");
        passwordField.sendKeys(password);
        logger.info("Entered password");
        loginButton.click();
        logger.info("Login button clicked");

    }
}