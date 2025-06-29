package pageObjects;

import java.time.Duration;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutPage extends BasePage {

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//strong[normalize-space()='login page']")
    WebElement loginPageLink;

    @FindBy(id = "input-shipping-address")
    WebElement shippingAddressDropdown;

    @FindBy(id = "button-shipping-methods")
    WebElement shippingMethodsButton;

    @FindBy(id = "button-shipping-method")
    WebElement flatShippingButton;

    @FindBy(id = "button-payment-methods")
    WebElement paymentMethodsButton;

    @FindBy(id = "button-payment-method")
    WebElement codButton;

    @FindBy(xpath = "//div[@class='text-end']//button[contains(text(),'Confirm')]")
    WebElement confirmButton;

    // Navigate to login page from checkout
    public void clickLogin() {
        loginPageLink.click();
    }

    // Complete the checkout process
    public void completeCheckout() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // Handle shipping address dropdown safely
            Select select = new Select(wait.until(ExpectedConditions.visibilityOf(shippingAddressDropdown)));
            if (select.getOptions().size() > 1) {
                select.selectByIndex(1);
            } else {
                select.selectByIndex(0); // Fallback if only one address
            }
            System.out.println("Shipping address selected.");

            // Step through shipping method
            wait.until(ExpectedConditions.elementToBeClickable(shippingMethodsButton)).click();
            System.out.println("Clicked shipping methods button.");

            wait.until(ExpectedConditions.elementToBeClickable(flatShippingButton)).click();
            System.out.println("Clicked flat shipping option.");

            // Step through payment method
            wait.until(ExpectedConditions.elementToBeClickable(paymentMethodsButton)).click();
            System.out.println("Clicked payment methods button.");

            wait.until(ExpectedConditions.elementToBeClickable(codButton)).click();
            System.out.println("Clicked cash on delivery option.");

            // Confirm the order
            scroll(confirmButton);
            wait.until(ExpectedConditions.elementToBeClickable(confirmButton));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", confirmButton);
            System.out.println("Clicked confirm order button.");

        } catch (Exception e) {
            System.err.println("Error during checkout process: " + e.getMessage());
            throw e;
        }
    }

    // Scroll utility method
    private void scroll(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }
}
