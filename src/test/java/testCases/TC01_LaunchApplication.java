package testCases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import testBase.BaseClass;
import utilities.RetryAnalyzer;

public class TC01_LaunchApplication extends BaseClass {

    private static final Logger logger = LogManager.getLogger(TC01_LaunchApplication.class);

    @Test(groups = { "sanity", "regression" }, retryAnalyzer = utilities.RetryAnalyzer.class)
    void testLaunchApplication() {
        logger.info("===== Starting test: testLaunchApplication =====");

        try {
            HomePage hp = new HomePage(getDriver());
            logger.debug("HomePage object initialized");

            String title = getDriver().getTitle();
            logger.debug("Fetched page title: {}", title);

            Assert.assertEquals(title, "Your store of fun");
            logger.info("Page title assertion passed");

        } catch (AssertionError e) {
            logger.error("Assertion failed - Title does not match expected value", e);
            throw e; // Important: rethrow to allow RetryAnalyzer to work
        } catch (Exception e) {
            logger.error("Unexpected error during test execution", e);
            Assert.fail("Test failed due to unexpected exception");
        }

        logger.info("===== Finished test: testLaunchApplication =====");
    }
}
