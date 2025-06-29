package pageObjects;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ConfirmationPage extends BasePage {

    public ConfirmationPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//h1[normalize-space()='Your order has been placed!']")
    WebElement orderSuccessHeader;

    // Check if the order was successfully placed
    public boolean isOrderPlaced() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(orderSuccessHeader));
            boolean displayed = orderSuccessHeader.isDisplayed();
            System.out.println("Order confirmation displayed: " + displayed);
            return displayed;
        } catch (Exception e) {
            System.err.println("Order confirmation not found: " + e.getMessage());
            return false;
        }
    }
}
