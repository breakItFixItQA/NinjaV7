package testCases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountPage;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import testBase.BaseClass;
import utilities.DataProviders;

import java.time.Duration;

public class TC02_Login extends BaseClass {

    private static final Logger logger = LogManager.getLogger(TC02_Login.class);

    @Test(
        groups = {"sanity", "regression", "datadriven"},
        dataProvider = "LoginData",
        dataProviderClass = DataProviders.class,
        retryAnalyzer = utilities.RetryAnalyzer.class
    )
    public void testLogin(String email, String pwd) {
        logger.info("===== Starting testLogin with email: {} =====", email);

        try {
            // Initialize Home Page and navigate to login
            HomePage hp = new HomePage(getDriver());
            hp.clickMyAccount();
            hp.goToLogin();

            // Login actions
            LoginPage lp = new LoginPage(getDriver());
            lp.setEmail(email);
            lp.setPwd(pwd);
            lp.clickLogin();

            // Validate successful login
            AccountPage ap = new AccountPage(getDriver());
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
            WebElement confirmationElement = wait.until(
                ExpectedConditions.visibilityOf(ap.getMyAccountConfirmation())
            );

            boolean status = confirmationElement.isDisplayed();
            if (status) {
                logger.info("✅ Login successful for: {}", email);
                ap.clickMyAccountDropDown();
                ap.clickLogout();
                Assert.assertTrue(true);
            } else {
                String screenshotPath = captureScreen("login_failure_" + email.replaceAll("[^a-zA-Z0-9]", "_"));
                logger.warn("❌ Login failed for: {}. Screenshot: {}", email, screenshotPath);
                Assert.fail("Login failed - My Account confirmation not visible. Screenshot: " + screenshotPath);
            }

        } catch (AssertionError ae) {
            String screenshotPath = captureScreen("assertion_failure_" + email.replaceAll("[^a-zA-Z0-9]", "_"));
            logger.error("❌ Assertion failed during login for: {}, Screenshot: {}", email, screenshotPath, ae);
            throw ae; // Let TestNG handle it
        } catch (Exception e) {
            String screenshotPath = captureScreen("exception_" + email.replaceAll("[^a-zA-Z0-9]", "_"));
            logger.error("❌ Exception occurred during login for: {}, Screenshot: {}", email, screenshotPath, e);
            Assert.fail("Unexpected exception during login for email: " + email + 
                        ". Exception: " + e.getMessage() + 
                        ". Screenshot: " + screenshotPath);
        }

        logger.info("===== Finished testLogin with email: {} =====", email);
    }
}
