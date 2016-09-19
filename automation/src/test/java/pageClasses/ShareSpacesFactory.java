package pageClasses;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.apache.log4j.Logger;

public class ShareSpacesFactory {
	
	WebDriver driver;
	Actions action;

	// Initialize webdriver
	public ShareSpacesFactory(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	static Logger log = Logger.getLogger(ShareSpacesFactory.class);
	
	public void navigateToSpacesHomePage(WebDriver driver) {
		driver.findElement(By.id("ng-app"));
	}

	// Share button on header
	// @FindBy(css = "div.button:nth-child(4)") //Threw an exception
	@FindBy(css = "div.button.send.ng-scope")
	WebElement shareBtn;
	
	// Click on Share button on header
	public void shareBtnClick() {
		shareBtn.click();
	}

	// Share link on share dialog
	@FindBy(css = "i.ng-binding")
	WebElement shareLink;

	// Copy share link on share dialog
	@FindBy(css = "#global-zeroclipboard-flash-bridge")
	WebElement copyShareLink;
	
	// Send with your desktop email on share dialog
	@FindBy(css = "a.opt > i:nth-child(1)")
	WebElement sendShareLink;
	
//	// Recipient email on share dialog
//	@FindBy(css = "#as-input-096")
//		WebElement recptShareEmail;
//	public void enterRecptEmail(String recptEmail) {
//		recptShareEmail.sendKeys(recptEmail);
//	}	

	// Sharing message on share dialog
	@FindBy(css = "input.top-border")
	WebElement shareMsg;
	
	// Recipient email address
	@FindBy(css="input.top-rounded.as-input")
	WebElement rcptEmail;
	
	// Fill in the recipient's email
	public void enterRcptEmail(String recipient) {
		rcptEmail.sendKeys(recipient);
	}
	
	// Message test box on share dialog
	@FindBy(css = ".bottom-rounded")
	WebElement shareMsgText;
	
	// Enter a share message
	public void enterShareMsg(String shareMsg) {
		shareMsgText.click();
		shareMsgText.sendKeys(shareMsg);
	}
	
	// Permissions menu is currently only in share form for private spaces
	// Find permissions menu selector
	@FindBy(css="i.arrow.ng-scope")
	WebElement permissionsMenu;
	
	// Open permissions menu
	public void openPermissions() {
		permissionsMenu.click();
	}
	
	// Find Can edit permission
	@FindBy(css="div.options > div.option.ng-binding.ng-scope:nth-of-type(2)")
	WebElement shareWithEdit;
	
	// Select Can edit
	public void selectEditPermission() {
		Actions action = new Actions(driver);
		action.moveToElement(permissionsMenu).click().moveToElement(shareWithEdit).click().build().perform();
	}
	
	// Find Can comment and download permission
	@FindBy(css="div.options > div.option.ng-binding.ng-scope:nth-of-type(3)")
	WebElement shareWithCommentAndDownload;
	
	// Select Can comment and download
	public void selectCommentAndDownload() throws Exception {
		Actions action = new Actions(driver);
		action.moveToElement(permissionsMenu).click().moveToElement(shareWithCommentAndDownload).click().build().perform();
	}
	
	// Find Can comment only
	@FindBy(css="div.options > div.option.ng-binding.ng-scope:nth-of-type(4)")
	WebElement shareWithCommentOnly;
		
	// Select Can comment only
	public void selectCommentOnly() {
		Actions action = new Actions(driver);
		action.moveToElement(permissionsMenu).click().moveToElement(shareWithCommentOnly).click().build().perform();
	}

	// Send button on share dialog
	//@FindBy(css = "button.default") //Share form has changed
	@FindBy(css="button.btn-confirm.ng-binding")
	WebElement sendShareBtn;
	
	// Click on Share button on header
	public void sendShareBtnClick() {
		sendShareBtn.click();
	}
	
	// Get the text of the button
	public String getShareBtnText() {
		return sendShareBtn.getText();
	}
	
	//Slack element on Share space
	@FindBy(css="div.opt.slack.component")
	WebElement slackButton;
	
	//Click on Slack button
	public void clickSlackButton(){
		slackButton.click();
	}

	//Slack team name
	@FindBy(css=".input_inline")
	WebElement slackTeamName;
	
	//Enter Slack Team name
	public void enterSlackTeamName(String slackName){
		slackTeamName.sendKeys(slackName);
	}
	// Share success message on share dialog
//	@FindBy(css = "div.ht-interrupt:nth-child(4)")
//	WebElement SuccessShareMsg;
	//Verify share success message
//	public String shareSuccess(){
//		return SuccessShareMsg.getText();	
//	}

	//View existing space
	@FindBy(css=".btn-view-space")
	WebElement viewSpace;
	
	public void clickViewSpace(){
		viewSpace.click();
	}

	// Cancellation of quota message for un-verified free users sharing space to >5 recipients
	@FindBy(css="div.ht-interrupt:nth-child(26) > div:nth-child(2)")	
	WebElement CancelShareLmtForUnverified;
	
	// Click "UPGRADE TO PRO" button
	public void closeFreeQuotaMsg() {
		CancelShareLmtForUnverified.click();
	}
	
	//Close share dialog
	@FindBy(css="div.close:nth-child(2)")
	WebElement closeShare;
	
	public void clickCloseShare(){
		closeShare.click();
	}	

	//Daily quota message for free users sharing space with >25 recipients
	@FindBy(css=".error-message")
	WebElement shareQuotaReached;

	public boolean isShareQuotaReached() {
		return shareQuotaReached.isDisplayed();
	}	
		
}