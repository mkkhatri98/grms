package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.ConfigReader;
import utils.LoggerHelper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.NoSuchElementException;


public class BaseTest {

   protected WebDriver driver;
    protected Wait<WebDriver> wait;
    private static final Logger log = LoggerHelper.getLogger(BaseTest.class);


    @BeforeMethod
    public void setUp() {
        log.info("Setting up WebDriver and browser configurations");
        WebDriverManager.chromedriver().setup();  // Auto-downloads ChromeDriver
        driver = new ChromeDriver();
        log.info("ChromeDriver initialized");
        // Configure Fluent Wait (global settings)

        System.out.println("Timeout: " + ConfigReader.getWaitTimeout());
        System.out.println("Polling: " + ConfigReader.getWaitPolling());
        wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class) // Exceptions to ignore
                .ignoring(Exception.class);
        driver.manage().window().maximize();
        log.info("Navigating to base URL: {}", ConfigReader.getProperty("base.url"));
        driver.get(ConfigReader.getProperty("base.url"));

    }

    @AfterMethod
    public void tearDown(ITestResult result) throws IOException {
        log.info("Tearing down WebDriver");
        // Capture screenshot only if test failed
        if (result.getStatus() == ITestResult.FAILURE) {
            log.error("Test '{}' FAILED. Capturing screenshot...", result.getName());
            captureScreenshot(result.getName());
            captureScreenshotAlure(result.getName());
        }else if (result.getStatus() == ITestResult.SUCCESS) {
            log.info("Test '{}' PASSED", result.getName());
        } else if (result.getStatus() == ITestResult.SKIP) {
            log.warn("Test '{}' SKIPPED", result.getName());
        }
        if (driver != null) {
            driver.quit();
            log.info("Driver quit successfully");
        }
    }


    // Method to capture screenshot
    public void captureScreenshot(String testName) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);

            // Create timestamp for unique filenames
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

            // Define screenshot directory
            String screenshotDir = System.getProperty("user.dir") + "/screenshots/";
            new File(screenshotDir).mkdirs(); // Create directory if it doesn't exist

            // Save with test name + timestamp
            String screenshotPath = screenshotDir + testName + "_" + timestamp + ".png";
            FileHandler.copy(source, new File(screenshotPath));
            log.info("Screenshot saved at: {}", screenshotPath);

        } catch (IOException e) {
            log.error("Failed to capture screenshot: {}", e.getMessage());
        }
    }

    public void captureScreenshotAlure(String testName) throws IOException {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try (InputStream is = Files.newInputStream(screenshot.toPath())) {
            Allure.addAttachment("Screenshot on Failure", is);
            log.info("Screenshot attached to Allure report for test: {}", testName);
        }
    }
}