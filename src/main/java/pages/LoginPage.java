package pages;

import base.BaseTest;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import utils.LoggerHelper;


import java.time.Duration;
public class LoginPage{
    private static final Logger log = LoggerHelper.getLogger(LoginPage.class);
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
        log.info("Attempting login with username: {}", username);
        usernameField.sendKeys(username);
        log.info("Entered username");
        passwordField.sendKeys(password);
        log.info("Entered password");
        loginButton.click();
        log.info("Login button clicked");

    }
}