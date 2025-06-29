package pageObjects;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductPage extends BasePage {

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//input[@id='input-option-225']")
    WebElement input_DeliveryDate;

    @FindBy(xpath = "//button[@id='button-cart']")
    WebElement btn_AddToCart;

    @FindBy(xpath = "//div[@class='alert alert-success alert-dismissible']")
    WebElement alert_Success;

    @FindBy(xpath = "//a[@title='Checkout']//i[@class='fa-solid fa-share']")
    WebElement link_Checkout;

    @FindBy(xpath = "//div//button//i[@class='fa-solid fa-heart']")
    WebElement wishlistIcon;

    // === Add to Wishlist ===
    public void addToWishlist() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", wishlistIcon);
        wait.until(ExpectedConditions.elementToBeClickable(wishlistIcon)).click();
    }

    // === Success Message Display ===
    public boolean isSuccessMessageDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.visibilityOf(alert_Success));
            return alert_Success.getText().contains("Success");
        } catch (Exception e) {
            return false;
        }
    }

    // === Set Delivery Date (JavaScript Approach) ===
    public void setDeliveryDate() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        LocalDate date = LocalDate.now().plusDays(5);
        String formattedDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        try {
            System.out.println("Setting delivery date to: " + formattedDate);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", input_DeliveryDate);

            wait.until(ExpectedConditions.visibilityOf(input_DeliveryDate));
            wait.until(ExpectedConditions.elementToBeClickable(input_DeliveryDate));

            // Set date using JavaScript
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].setAttribute('value', arguments[1]);",
                input_DeliveryDate, formattedDate
            );

            System.out.println("Delivery date successfully set using JavaScript.");
        } catch (Exception e) {
            System.err.println("Failed to set delivery date: " + e.getMessage());
            throw e;
        }
    }

    // === Add to Cart ===
    public void clickAddToCart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", btn_AddToCart);
        wait.until(ExpectedConditions.elementToBeClickable(btn_AddToCart)).click();
    }

    // === Go to Checkout ===
    public void clickCheckout() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", link_Checkout);
        wait.until(ExpectedConditions.elementToBeClickable(link_Checkout)).click();
    }

    // === Getters for Use in Waits ===
    public WebElement getWishlistIcon() {
        return wishlistIcon;
    }

    public WebElement getAddToCartButton() {
        return btn_AddToCart;
    }

    public WebElement getSuccessMessageElement() {
        return alert_Success;
    }
}
