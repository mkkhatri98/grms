package tests;

import base.BaseTest;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.GeneralDashboard;
import pages.LoginPage;
import utils.ConfigReader;
import utils.LoggerHelper;

public class LoginTest extends BaseTest {
    private static final Logger log = LoggerHelper.getLogger(LoginTest.class);
    @Test
    public void testLogin() {
        log.info("====== Starting test: testLogin ======");

        LoginPage loginPage = new LoginPage(driver);
        GeneralDashboard dashboardPage = new GeneralDashboard(driver);
        loginPage.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password")
        );
        boolean loggedIn = dashboardPage.verifyLoginSuccessful();
        Assert.assertTrue(loggedIn, "Login failed: Dashboard element not found");

        log.info("====== Finished test: testLogin ======");
    }
}