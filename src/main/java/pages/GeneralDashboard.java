package pages;

import base.BaseTest;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import utils.LoggerHelper;

import java.time.Duration;


public class GeneralDashboard {
    private static final Logger log = LoggerHelper.getLogger(GeneralDashboard.class);
    private WebDriver driver;
    private Wait<WebDriver> wait;

    @FindBy(xpath = "//*[@id=\"highcharts-3gj04kf-0\"]/svg/text[1]")
    private WebElement generalDashboardChartTitle;


    public GeneralDashboard(WebDriver driver) {
        this.driver = driver;
        this.wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(2))
                .ignoring(NoSuchElementException.class);
        PageFactory.initElements(driver, this);
    }

    public boolean verifyLoginSuccessful() {
        try {
            // Use FluentWait to check for element visibility
            wait.until(ExpectedConditions.urlContains("dashboard"));
            log.info("Login verification: Dashboard element found");
            return true;  // Element found within timeout
        } catch (Exception e) {
            log.error("Login verification failed: {}", e.getMessage());
            return false; // Element not found or timeout exceeded
        }
    }

}
