package pageObjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CategoryPage extends BasePage {

    public CategoryPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    // ============================
    // Locators
    // ============================

    // Laptops & Notebooks menu
    @FindBy(xpath = "//a[normalize-space()='Laptops & Notebooks']")
    WebElement menu_LaptopsAndNotebooks;

    // Show All Laptops & Notebooks
    @FindBy(xpath = "//a[normalize-space()='Show All Laptops & Notebooks']")
    WebElement link_ShowAllLaptops;

    // HP Laptop product link
    @FindBy(xpath = "//a[normalize-space()='HP LP3065']")
    WebElement link_HP_Laptop;

    // ============================
    // Action Methods
    // ============================

    // Click "Laptops & Notebooks" menu
    public void clickLaptopsAndNotebooks() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(menu_LaptopsAndNotebooks)).click();
    }

    // Click "Show All Laptops & Notebooks"
    public void clickShowAll() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(link_ShowAllLaptops)).click();
    }

    // Click the HP Laptop product link
    public void selectHPProduct() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // First wait for the product list or HP product to be visible
        wait.until(ExpectedConditions.visibilityOf(link_HP_Laptop));

        // Scroll into view to avoid click interception
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", link_HP_Laptop);

        // Wait for clickable
        wait.until(ExpectedConditions.elementToBeClickable(link_HP_Laptop)).click();

        System.out.println("Clicked HP product");
    }
}
