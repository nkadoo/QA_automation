package pageClasses;

import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.log4testng.Logger;

import utilities.Constants;

public class UplinkPageFactory {

	WebDriver driver;
	Actions action;
	
	static Logger log = Logger.getLogger(UplinkPageFactory.class);
	
	public UplinkPageFactory(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	/********************************************************************
	 * Uplink owner
	 *********************************************************************/
	
	// Find current Uplink URL
	@FindBy(css="div.text-link > a")
	WebElement currentUplink;
	
	// Get current Uplink URL and return as String
	public String getCurrentUplink() {
		return currentUplink.getText();
	}
	
	// Find new uplink input field
	@FindBy(css="input.ng-valid.ng-valid-maxlength")
	WebElement uplinkInputField;
	
	// Change uplink URL
	public void changeUplinkUrl(String differentUrl) throws InterruptedException {
		uplinkInputField.clear();
		Thread.sleep(500);
		uplinkInputField.sendKeys(differentUrl);
		Thread.sleep(1000);
	}
	
	// Find uplink url status
	@FindBy(css="div.status")
	WebElement uplinkUrlStatus;
	
	// Get uplink url status
	// Status can be "This link is available!", "This link is already in use.", 
	// "This link is invalid.", "This link too short."
	public String getUplinkUrlStatus() {
		return uplinkUrlStatus.getText();
	}
	
	// Find change uplink button
	@FindBy(css="div.animated.fadeIn.ht-uplink-settings>button")
	WebElement changeUplinkBtn;
	
	public void clickChangeUplink() {
		changeUplinkBtn.click();
	}
	
	// Fing URL change confirmation button
	@FindBy(css="div.message>button.ng-binding")
	WebElement urlChangeConfirmationButton;
	
	// Confirm URL change
	public void confirmUrlChange() {
		urlChangeConfirmationButton.click();
	}
	
	// Find close button on confirmation screen
	@FindBy(css="div.page>div:nth-of-type(14)>div.close")
	WebElement closeConfirmationScreenBtn;
	
	// Cancel URL change
	public void cancelUrlChange() {
		closeConfirmationScreenBtn.click();
	}
	
	/********************************************************************
	 *********************************************************************/
	
	/********************************************************************
	 * Uplink sender
	 *********************************************************************/
	
	// Find upload from My Computer button
	@FindBy(css="div.choose-files>div>div>input")
	WebElement myComputerBtn;
	
	public void uploadFirstFileUplink() {
		myComputerBtn.sendKeys(Constants.File_path_For_Space1);
	}
	
	// Find sender's name field
	@FindBy(css="input.name")
	WebElement sendersName;
	
	// Enter sender's name
	public void uplinkSenderName(String name) {
		sendersName.sendKeys(name);
	}

	// Find name error icon
	@FindBy(css="div.guest>div:nth-of-type(1)>div.field-error")
	WebElement nameErrorIcon;
	
	public boolean nameError() {
		return nameErrorIcon.isDisplayed();
	}
	
	// Find sender's email field
	@FindBy(css="input.email")
	WebElement sendersEmail;
	
	// Enter sender's email
	public void uplinkSenderEmail(String email) {
		sendersEmail.sendKeys(email);
	}

	// Find email error icon
	@FindBy(css="div.guest>div:nth-of-type(2)>div")
	WebElement emailErrorIcon;
	
	public boolean emailError() {
		return emailErrorIcon.isDisplayed();
	}
	
	// Find message field
	@FindBy(css="textarea.ng-pristine")
	WebElement uplinkMessage;
	
	// Enter a message
	public void uplinkMessage(String message) {
		uplinkMessage.sendKeys(message);
	}
	
	// Find Add More Files 
	@FindBy(css="div.add-more>div>input")
	WebElement addMoreFilesLink;
	
	// Add another file 
	public void uploadSecondFileUplink() {
		addMoreFilesLink.sendKeys(Constants.File_path_For_Space2);
	}
	
	// Find file remove icon for first file
	@FindBy(css="div.edit-files>div>div>p:nth-of-type(1)>span.close")
	WebElement removeUplinkFileIcon;
	
	// Remove first Uplink file
	public void removeFirstFileUplink() {
		removeUplinkFileIcon.click();
	}
	
	// Find upload button
	@FindBy(css="div.button.confirm.anim-fade-fast")
	WebElement uplinkUploadBtn;
	
	// send files to Uplink
	public void sendToUplink() {
		uplinkUploadBtn.click();
	}
	
	// Find delivery confirmation message
	@FindBy(css="span.complete")
	WebElement uplinkDeliveryMessage;
	
	// Get delivery confirmation text
	public String getUplinkDeliveryText() {
		return uplinkDeliveryMessage.getText();
	}
	
	// Find error message
	@FindBy(css="div.errors.open > p")
	WebElement errorMessageTextBox;
	
	public String getErrorMessage() {
		return errorMessageTextBox.getText();
	}
	
	// Find Select Recipients button (ENT)
	@FindBy(css="div.select-recipients.button.ng-scope")
	WebElement selectRecipientsButton;
	
	public void openRecipientsWindow() {
		selectRecipientsButton.click();
	}
	
	// Find recipients' checkbox
	@FindBy(css="tr.ng-scope>td.checkbox")
	List<WebElement> recipientCheckboxes;
	
	public void selectAllRecipients() {
		for (WebElement checkbox: recipientCheckboxes) {
			checkbox.click();
		}
	}
	
	// Find Save button in recipient window
	@FindBy(css="button.save")
	WebElement saveRecipientsBtn;
	
	public void saveSelectedRecipients() {
		saveRecipientsBtn.click();
	}
	
	// Find Cancel button in recipient window
	@FindBy(css="div.buttons>span")
	WebElement cancelRecipientsBtn;
	
	public void cancelRecipients() {
		cancelRecipientsBtn.click();
	}
	
	/********************************************************************
	 *********************************************************************/
	
}
