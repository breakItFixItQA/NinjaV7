package testCases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pageObjects.CategoryPage;
import pageObjects.ProductPage;
import testBase.BaseClass;
import utilities.RetryAnalyzer;

public class TC03_AddToCart extends BaseClass {

    private static final Logger logger = LogManager.getLogger(TC03_AddToCart.class);

    @Test(groups = {"sanity", "regression"}, retryAnalyzer = RetryAnalyzer.class)
    @Parameters("browser") // Optional if you're using testng.xml for parallel execution
    public void testAddToCart(String browser) {
        logger.info("===== Starting testAddToCart on browser: {} =====", browser);

        try {
            CategoryPage cp = new CategoryPage(getDriver());
            logger.debug("CategoryPage initialized");

            cp.clickLaptopsAndNotebooks();
            logger.debug("Clicked on 'Laptops & Notebooks'");

            cp.clickShowAll();
            logger.debug("Clicked 'Show All' under Laptops & Notebooks");

            Thread.sleep(500); // Consider replacing with WebDriverWait if needed
            cp.selectHPProduct();
            logger.debug("Selected HP Laptop Product");

            ProductPage pp = new ProductPage(getDriver());
            logger.debug("ProductPage initialized");

            pp.setDeliveryDate();
            logger.debug("Set delivery date");

            pp.clickAddToCart();
            logger.debug("Clicked on 'Add to Cart'");

            boolean success = pp.isSuccessMessageDisplayed();
            logger.debug("Success message displayed: {}", success);

            Assert.assertTrue(success, "Add to Cart Failed!");
            logger.info("Add to Cart test passed");

        } catch (AssertionError ae) {
            logger.error("Assertion failed - product not added to cart", ae);
            throw ae;
        } catch (Exception e) {
            logger.error("Exception occurred during Add to Cart test", e);
            Assert.fail("Test failed due to unexpected exception.");
        }

        logger.info("===== Finished testAddToCart =====");
    }
}
