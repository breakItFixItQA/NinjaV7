package testCases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountPage;
import pageObjects.CategoryPage;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.ProductPage;
import testBase.BaseClass;
import utilities.RetryAnalyzer;

import java.time.Duration;

public class TC05_AddToWishList extends BaseClass {

    private static final Logger logger = LogManager.getLogger(TC05_AddToWishList.class);

    @Test(groups = {"regression"}, retryAnalyzer = RetryAnalyzer.class)
    void testAddToWishList() {
        logger.info("===== Starting test: testAddToWishList =====");

        try {
            // Step 1: Navigate to login
            HomePage hp = new HomePage(getDriver());
            logger.debug("Initialized HomePage");

            hp.clickMyAccount();
            logger.debug("Clicked 'My Account'");

            hp.goToLogin();
            logger.debug("Navigated to Login page");

            // Step 2: Perform login
            LoginPage lp = new LoginPage(getDriver());
            lp.setEmail("ssmithga@gmail.com");
            lp.setPwd("Test1234");
            logger.debug("Entered login credentials");

            lp.clickLogin();
            logger.debug("Clicked 'Login'");

            // Step 3: Verify login success
            AccountPage ap = new AccountPage(getDriver());
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(ap.getMyAccountConfirmation()));

            boolean isLoggedIn = ap.getMyAccountConfirmation().isDisplayed();
            Assert.assertTrue(isLoggedIn, "Login failed - My Account confirmation not displayed");
            logger.info("Login successful");

            // Step 4: Navigate to product
            CategoryPage cp = new CategoryPage(getDriver());
            cp.clickLaptopsAndNotebooks();
            logger.debug("Clicked 'Laptops & Notebooks'");

            cp.clickShowAll();
            logger.debug("Clicked 'Show All Laptops & Notebooks'");

            cp.selectHPProduct();
            logger.debug("Selected HP product");

            // Step 5: Add to Wishlist
            ProductPage pp = new ProductPage(getDriver());

            // Debug: Verify we are on the Product page
            logger.debug("Current page title: " + getDriver().getTitle());

            // Wait for Wishlist icon to be visible
            wait.until(ExpectedConditions.visibilityOf(pp.getWishlistIcon()));
            pp.addToWishlist();
            logger.debug("Clicked 'Add to Wishlist'");

            // Step 6: Confirm success
            boolean isSuccess = pp.isSuccessMessageDisplayed();
            logger.debug("Success message displayed: {}", isSuccess);

            Assert.assertTrue(isSuccess, "Wishlist confirmation message not displayed");
            logger.info("Product successfully added to wishlist");

        } catch (AssertionError ae) {
            logger.error("Assertion failed in testAddToWishList", ae);
            throw ae;
        } catch (Exception e) {
            logger.error("Unexpected exception occurred during testAddToWishList", e);
            Assert.fail("Test failed due to unexpected exception: " + e.getMessage());
        }

        logger.info("===== Finished test: testAddToWishList =====");
    }
}
