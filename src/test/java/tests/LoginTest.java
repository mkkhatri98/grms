package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.GeneralDashboard;
import pages.LoginPage;
import utils.ConfigReader;

public class LoginTest extends BaseTest {
    @Test
    public void testLogin() {

        LoginPage loginPage = new LoginPage(driver);
        GeneralDashboard dashboardPage = new GeneralDashboard(driver);
        loginPage.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password")
        );
        boolean loggedIn = dashboardPage.verifyLoginSuccessful();
        Assert.assertTrue(loggedIn, "Login failed: Dashboard element not found");
    }
}