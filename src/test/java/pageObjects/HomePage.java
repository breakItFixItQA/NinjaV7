package pageObjects;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends BasePage {

    // constructor
    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);  // <-- Required to initialize WebElements
    }

    // ============================
    // Locators
    // ============================

    // Link - My Account
    @FindBy(xpath = "//i[@class='fa-solid fa-user']")
    WebElement link_MyAccount;

    // Link - Login
    @FindBy(xpath = "//a[normalize-space()='Login']")
    WebElement link_Login;

    // ============================
    // Action Methods
    // ============================

    // Clicks the My Account icon
    public void clickMyAccount() {
        safeClick(link_MyAccount); // uses BasePage method to ensure reliability
    }

    // Waits for the Login link to be visible
    public void waitForLoginVisible() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(link_Login));
    }

    // Navigates to the Login link
    public void goToLogin() {
        safeClick(link_Login); // uses BasePage method to avoid click interception
    }
}
