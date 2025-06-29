package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class LoginPage extends BasePage {
   
	// constructor
	public LoginPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this); // <-- Required to initialize @FindBy elements
	}

	// Locators

	@FindBy(xpath = "//input[@id='input-email']")
	WebElement txt_Email;

	@FindBy(xpath = "//input[@id='input-password']")
	WebElement txt_Password;

	@FindBy(xpath = "//button[normalize-space()='Login']")
	WebElement btn_Login;

	@FindBy(xpath = "//h2[normalize-space()='My Account']")
	WebElement confirmationText_MyAccount;

	// Action methods

	public void setEmail(String email) {
		txt_Email.sendKeys(email);
	}

	public void setPwd(String pwd) {
		txt_Password.sendKeys(pwd);
	}

	public void clickLogin() {
		safeClick(btn_Login); // replaced .click() with safeClick() to avoid interception
	}

	public WebElement getMyAccountConfirmation() {
		return confirmationText_MyAccount;
	}
}
