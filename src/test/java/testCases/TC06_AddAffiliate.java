package testCases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AffiliatePage;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import testBase.BaseClass;
import utilities.RetryAnalyzer;

public class TC06_AddAffiliate extends BaseClass {

    private static final Logger logger = LogManager.getLogger(TC06_AddAffiliate.class);

    @Test(groups = { "regression" }, retryAnalyzer = utilities.RetryAnalyzer.class)
    void testAddAffiliate() {
        logger.info("===== Starting test: testAddAffiliate =====");

        try {
            HomePage hp = new HomePage(getDriver());
            logger.debug("Initialized HomePage");

            hp.clickMyAccount();
            logger.debug("Clicked 'My Account'");

            hp.goToLogin();
            logger.debug("Navigated to Login page");

            LoginPage lp = new LoginPage(getDriver());
            lp.setEmail("ssmithga@gmail.com");
            lp.setPwd("Test1234");
            logger.debug("Entered login credentials");

            lp.clickLogin();
            logger.debug("Submitted login form");

            AffiliatePage ap = new AffiliatePage(getDriver());
            logger.debug("Initialized AffiliatePage");

            ap.navigateToAffiliateForm();
            logger.debug("Navigated to Affiliate form");

            ap.fillAffiliateDetails("ABC Company", "www.qbcompany.com", "123456", "Sonia Smith");
            logger.debug("Filled affiliate details");

            boolean added = ap.isAffiliateAdded();
            logger.debug("Affiliate added status: {}", added);

            Assert.assertTrue(added, "Affiliate details not added successfully.");
            logger.info("Test passed: Affiliate details added successfully");

        } catch (AssertionError ae) {
            logger.error("Assertion failed - Affiliate addition verification failed", ae);
            throw ae; // Required to allow RetryAnalyzer to work
        } catch (Exception e) {
            logger.error("Unexpected error occurred during Affiliate addition test", e);
            Assert.fail("Test failed due to unexpected exception.");
        }

        logger.info("===== Finished test: testAddAffiliate =====");
    }
}
