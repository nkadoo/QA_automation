package pageClasses;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.Constants;
import utilities.ExcelUtility;

import com.mvn.automation.ContentProviderTest;

public class MyAccountPageFactory {

	WebDriver driver;
	Actions action;

	static Logger log = Logger.getLogger(MyAccountPageFactory.class);

	// Initialize webdriver
	public MyAccountPageFactory(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// Close the help popup element
	@FindBy(css = ".close-button")
	WebElement closeHelpPopup;

	// Click on Close help popup
	public void closeHelp() {
		closeHelpPopup.click();
	}

	// Default Avatar image element
	// @FindBy(css = ".default") //Only works for prod
	@FindBy(css = "div.menu > div > div > img.default.default-avatar.ng-scope")
	// Use for stage
	WebElement defaultAvatar;

	// Check if default avatar is present
	public boolean isDefaultAvatarDisplayed() {
		return defaultAvatar.isDisplayed();
	}

	// Click on Avatar image
	public void clickDefaultAvatar() {
		defaultAvatar.click();
	}

	// Web element when user has Avatar image
	@FindBy(css = ".user-avatar")
	WebElement avatarImage;

	// Click on Avatar image when we have a image uploaded for avatar
	public void clickAvaterImage() {
		avatarImage.click();
	}
	
	public void openSideBarMenu() {
		try {
			if (this.isDefaultAvatarDisplayed()) {
				this.clickDefaultAvatar();
			}
		} catch (Exception e) {
			this.clickAvaterImage();
		}
	}

	// Upgrade button element
	@FindBy(css = ".upgrade-btn")
	WebElement upgradeButton;

	// Click on Upgrade account button
	public void clickUpgrade() {
		upgradeButton.click();
	}

	// Credit card number element
	@FindBy(name = "cardNumber")
	WebElement CCnumber;
	// CC expiration element
	@FindBy(name = "expiration")
	WebElement CCexpiration;

	// CC CVV number element
	@FindBy(name = "cvv")
	WebElement CVV;

	// CC ZIP elememt
	@FindBy(name = "zip")
	WebElement CCzip;

	// Upgrade my account button element
	@FindBy(css = ".orange")
	WebElement upgradeAccountButton;

	// Click on Upgrade my account button
	public void clickUpgradeAccount() {
		upgradeAccountButton.click();
	}

	// Method for filling CC details
	public void CCDetails() {
		CCnumber.sendKeys(Constants.Credit_card_number);
		CCexpiration.sendKeys(Constants.Credit_card_expiration);
		CVV.sendKeys(Constants.Credit_card_cvv);
		CCzip.sendKeys(Constants.Credit_card_zip);
	}

	// Your plan element
	@FindBy(css = ".yourplan")
	WebElement yourPlan;

	// Check your plan
	public String yourPlan() {
		return yourPlan.getText();
	}
	
	// Find Edit button for billing 
	@FindBy(css="label.change.ng-scope")
	WebElement editBillingLink;
	
	// Check if edit billing option is present
	public boolean isEditBillingPresent() {
		try {
			this.editBillingLink.isDisplayed();
			return true;			
		} catch (NoSuchElementException e) {
			return false;
		}		
	}

	// Sign out from right rail
	@FindBy(css = ".signout > a:nth-child(1)")
	WebElement signOut;

	// Sign Out
	public void clickSignOut() {
		signOut.click();
	}

	// Change Password element
	@FindBy(css = "div.change")
	WebElement passChange;

	// Click on Password change link
	public void clickPassChange() {
		passChange.click();
	}

	// Current password field element
	@FindBy(css = "input.ng-pristine:nth-child(2)")
	WebElement currentPassword;

	// Enter current password
	public void userCurrentPassword(String userCurrentPassword) {
		currentPassword.sendKeys(userCurrentPassword);
	}

	// New password field element
	@FindBy(css = "input.ng-pristine:nth-child(3)")
	WebElement newPassword;

	// Enter new password
	public void userNewPassword(String userNewPassword) {
		newPassword.sendKeys(userNewPassword);
	}

	// Update password button element
	@FindBy(css = "div.actions:nth-child(2) > button:nth-child(2)")
	WebElement updateButton;

	// Click Update button for change password
	public void clickUpdatePasswordButton() {
		updateButton.click();
	}

	// Password updated text element
	@FindBy(css = "div.status:nth-child(3) > span:nth-child(1)")
	WebElement pwdUpdated;

	// Password updated text 
	public String pwdUpdatedText() {
		return pwdUpdated.getText();
	}

	// Forget password element
	@FindBy(css = ".reset-password")
	WebElement resetPassword;

	// CLick on Reset password
	public void resetPass() {
		resetPassword.click();
	}
	
	// Check your email - reset password element
	@FindBy(css = "div.status:nth-child(5) > span:nth-child(1)")
	WebElement checkUrEmail;

	// Check your email text
	public String checkUrEmailText() {
		return checkUrEmail.getText();
	}

	// CLick on Reset password
	public void checkUrEmailClick() {
		checkUrEmail.click();
	}	

	// Find dropbox connection status
	@FindBy(css = "tr.ng-scope:nth-child(1) > td.connector.disconnect")
	WebElement dropboxIsConnected;

	// Check if dropbox is connected
	public boolean dropboxConnection() {
		try {
			// If this element's tag name is located then user is connected to
			// dropbox
			this.dropboxIsConnected.getTagName();
			return true;
		} catch (NoSuchElementException e) {
			// A NoSuchElementException means the user is not connected to
			// dropbox
			return false;
		}
	}

	// Find dropbox connect/disconnect link
	@FindBy(css = "tr.ng-scope:nth-child(1) > td:nth-child(3)")
	WebElement dropboxService;

	// Click on dropbox disconnect link
	public void clickDropboxDisconnect() {
		//dropboxService.click();
		action = new Actions(driver);
		action.moveToElement(dropboxService).click().build().perform();
	}

	// Find googledrive connection status
	@FindBy(css = "tr.ng-scope:nth-child(2) > td.connector.disconnect")
	WebElement googleDriveIsConnected;

	// Check if google is connected
	public boolean googleDriveConnection() {
		try {
			// If this element's tag name is located then user is connected to
			// googledrive
			this.googleDriveIsConnected.getTagName();
			return true;
		} catch (NoSuchElementException e) {
			// A NoSuchElementException means the user is not connected to
			// googledrive
			return false;
		}
	}

	// Find googledrive connect/disconnect link
	@FindBy(css = "tr.ng-scope:nth-child(2) > td:nth-child(3)")
	WebElement googleService;

	// Click on google disconnect link
	public void clickGoogleDisconnect() {
		//googleService.click();
		action = new Actions(driver);
		action.moveToElement(googleService).click().build().perform();
	}

	// Find onedrive connection status
	@FindBy(css = "tr.ng-scope:nth-child(3) > td.connector.disconnect")
	WebElement oneDriveIsConnected;

	// Check if onedrive is connected
	public boolean oneDriveConnection() {
		try {
			// If this element's tag name is located then user is connected to
			// onedrive
			this.oneDriveIsConnected.getTagName();
			return true;
		} catch (NoSuchElementException e) {
			// A NoSuchElementException means the user is not connected to
			// onedrive
			return false;
		}
	}

	// Find onedrive connect/disconnect link
	@FindBy(css = "tr.ng-scope:nth-child(3) > td:nth-child(3)")
	WebElement onedriveService;

	// Click on onedrive disconnect link
	public void clickOneDriveDisconnect() {
		onedriveService.click();
	}

	// Find Dropbox email field
	@FindBy(css = "div.text-input-wrapper >input[name=login_email]")
	public static WebElement dropBoxEmailField;

	// Find Dropbox password field
	@FindBy(css = "div.text-input-wrapper >input[name=login_password]")
	WebElement dropBoxPswdField;

	// Find Dropbox Sign In button
	@FindBy(css = "button.login-button.button-primary")
	WebElement dropBoxSignInButton;

	// Find Dropbox Allow button
	@FindBy(css = "button.auth-button.button-primary")
	WebElement dropBoxAllowButton;

	// Find GoogleDrive email field
	@FindBy(css = "input#Email")
	WebElement googleDriveEmailField;

	// Find Google Next button
	@FindBy(css = "input#next")
	WebElement googleDriveNextButton;

	// Find Google password field
	@FindBy(css = "input#Passwd")
	WebElement googleDrivePswdField;

	// Find Google SignIn button
	@FindBy(css = "input#signIn")
	WebElement googleDriveSignInButton;

	// Find Google Allow button
	@FindBy(css = "button#submit_approve_access")
	WebElement googleDriveAllowButton;

	// Find OneDrive email field
	@FindBy(css = "div#idDiv_PWD_UsernameTb > div > input")
	WebElement oneDriveEmailField;

	// Find OneDrive password field
	@FindBy(css = "div#idDiv_PWD_PasswordTb > div > input")
	WebElement oneDrivePswdField;

	// Find OneDrive Sign In button
	@FindBy(css = "input#idSIButton9")
	WebElement oneDriveSignInButton;
}