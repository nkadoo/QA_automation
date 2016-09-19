package pageClasses;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.ProviderLogInUtility;
import utilities.Constants;

public class SendPageFactory {
	WebDriver driver;
	WebElement element = null;
	Actions action;

	static Logger log = Logger.getLogger(SendPageFactory.class);

	public SendPageFactory(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// Find the To field
	@FindBy(css = "input.recipients")
	WebElement toField;

	// Enter the recipient's email
	public void enterRecipientEmail(String email) {
		toField.sendKeys(email);
	}

	// Find the Subject field
	@FindBy(css = "input[name='subject']")
	WebElement subjectField;

	// Enter a subject
	public void enterSubject(String subject) {
		subjectField.sendKeys(subject);
	}

	// Find the Message field
	@FindBy(css = "textarea[name='message']")
	WebElement messageField;

	// Enter a message
	public void enterMessage(String message) {
		messageField.sendKeys(message);
	}

	// Find the Upload from Desktop icon
	@FindBy(css = "i.provider.my-computer>label>input")
	WebElement uploadFrmDesktop;

	// Upload a file from desktop
	public void uploadFileToSend() {
		uploadFrmDesktop.sendKeys(Constants.File_path_For_Space1);
	}

	// Upload a non-preview-able file from desktop
	public void uploadNonPrivewableFileToSend() {
		uploadFrmDesktop.sendKeys(Constants.File_path_For_NonPreviewableFile);

	}

	// Upload a 300 MB file from desktop
	public void uploadLargeFileToSend() {
		uploadFrmDesktop.sendKeys(Constants.File_path_For_Large_File);
	}

	// Find Hightail icon
	@FindBy(css = "i.provider.hightail")
	WebElement hightailIcon;

	// Click Hightail icon
	public void clickHTIcon() {
		hightailIcon.click();
	}

	// Find Dropbox icon
	@FindBy(css = "i.provider.dropbox")
	WebElement dropboxIcon;

	// Click Dropbox icon
	public void clickDBIcon() throws Exception {
		dropboxIcon.click();
		ProviderLogInUtility.logInToService(driver, "dropbox");
	}

	// Find OneDrive icon
	@FindBy(css = "i.provider.onedrive")
	WebElement onedriveIcon;

	// Click OneDrive icon
	public void clickODIcon() throws Exception {
		onedriveIcon.click();
		ProviderLogInUtility.logInToService(driver, "onedrive");
	}

	// Find GoogleDrive icon
	@FindBy(css = "i.provider.googledrive")
	WebElement googledriveIcon;

	// Click GoogleDrive icon
	public void clickGDIcon() throws Exception {
		googledriveIcon.click();
		ProviderLogInUtility.logInToService(driver, "googledriver");
	}

	// Find second file from list because some providers have folders as the
	// first item
	@FindBy(css = "div.explorer>table.table>tbody>tr:nth-of-type(3)")
	WebElement secondFileInList;

	// Select the second file because some providers have folders as the first
	// item
	public void selectAFile() {
		secondFileInList.click();
	}

	// Find the ADD button in the "Add files" window
	@FindBy(css = "div.button.button-choose")
	WebElement addButtonFromFileServicePicker;

	// Add the file from file service selector
	public void clickAddButtonFromServicePicker() {
		addButtonFromFileServicePicker.click();
	}

	// Method to add a file from different services using the ADD MORE FILES
	// menu
	public void addFileFromService(String provider) throws Exception {
		clickAddMoreFiles();
		switch (provider) {
		case "hightail":
			addMoreFilesHT();
			break;
		case "dropbox":
			addMoreFilesDB();
			ProviderLogInUtility.logInToService(driver, provider);
			break;
		case "onedrive":
			addMoreFilesOD();
			ProviderLogInUtility.logInToService(driver, "onedrive");
			break;
		case "googledrive":
			addMoreFilesGD();
			ProviderLogInUtility.logInToService(driver, "googledrive");
			break;
		}

		selectAFile();
		clickAddButtonFromServicePicker();
	}

	// Find Add More Files button
	@FindBy(css = "div.opt.open.send")
	WebElement addMoreFilesBtn;

	// Click Add More Files button
	public void clickAddMoreFiles() {
		// addMoreFilesBtn.click();
		new WebDriverWait(driver, 10).until(
				ExpectedConditions.visibilityOf(addMoreFilesBtn)).click();
	}

	// Find the My computer label from the ADD MORE FILES menu
	@FindBy(css = "label.opt.add-files-desktop.ng-scope>input")
	WebElement addFilesFromDesktopLabel;

	// Upload a file from My computer label
	public void addMoreFilesFromDesktop() {
		addFilesFromDesktopLabel.sendKeys(Constants.File_path_For_Space2);
	}

	// Upload two files from My computer label
	// *********Does not work in Firefox************
	public void add2MoreFilesFromDesktop() throws Exception {
		addFilesFromDesktopLabel.sendKeys(Constants.File_path_For_Space2);
		addFilesFromDesktopLabel.sendKeys(Constants.File_path_For_Space3);
	}

	// Find the Hightail label from the ADD MORE FILES menu
	@FindBy(css = "div.opt.add-files-hightail")
	WebElement hightailLabel;

	// Click the HT label
	public void addMoreFilesHT() {
		hightailLabel.click();
	}

	// Find the Dropbox label from the ADD MORE FILES menu
	@FindBy(css = "div.opt.add-files-dropbox")
	WebElement addFilesFromDropboxLabel;

	// Click the DB label
	public void addMoreFilesDB() {
		addFilesFromDropboxLabel.click();
	}

	// Find the OneDrive label from the ADD MORE FILES menu
	@FindBy(css = "div.opt.add-files-onedrive")
	WebElement oneDriveLabel;

	// Click OneDrive label
	public void addMoreFilesOD() {
		oneDriveLabel.click();
	}

	// Find Google Drive label from ADD MORE FILES
	@FindBy(css = "div.opt.add-files-googledrive")
	WebElement googleDriveLabel;

	// Click Google Drive label
	public void addMoreFilesGD() {
		googleDriveLabel.click();
	}

	// Find the list of remove icons for attached files
	@FindBy(css = "div.files-state>ul>li>div.remove.anim-fade")
	List<WebElement> numberOfAttachedFiles;

	// Remove the first file in the list
	public void removeFirstFile() {
		for (WebElement file : numberOfAttachedFiles) {
			file.click();
			break;
		}
	}

	// Get the number of attached files
	public int getNumberOfFiles() {
		return numberOfAttachedFiles.size();
	}

	// Find Start Over button
	@FindBy(css = "div[class='start-over anim-fade']")
	WebElement startOverBtn;

	// Click Start Over button
	public void clickStartOverBtn() {
		startOverBtn.click();
	}

	// Find Next button
	@FindBy(css = "div[class='next anim-fade']")
	WebElement nextBtn;

	// Click Next button
	public void clickNextBtn() {
		nextBtn.click();
	}
	
	// Find recipients 
	@FindBy(css=".recipients-content > p")
	WebElement recipientsList;

//	// Find first recipient's email
//	@FindBy(css = ".quick-tips>ul>li:nth-of-type(1)")
//	WebElement firstEmailEntered;

	// Get email
	public String getFirstEmailEntered() throws Exception {
		return recipientsList.getText();
	}

	// Find Send button
	@FindBy(css = "div.next")
	WebElement sendBtn;

	// Click the Send button
	public void clickSendBtn() {
		sendBtn.click();
	}

	// Find Access Code text box
	@FindBy(css = "div.pwd-display>input")
	WebElement accessCodeTextBox;

	// Enter an Access Code
	public void enterAccessCode(String accessCode) {
		accessCodeTextBox.sendKeys(accessCode);
	}

	// Remove the Access Code
	public void removeAccessCode() {
		accessCodeTextBox.clear();
	}

	// Find Access Code toggle
	@FindBy(css = "span.pwd-toggle")
	WebElement accessCodeDisplayToggle;

	// Hide/Show Access Code
	public void toggleAccessCode() {
		// accessCodeDisplayToggle.click();
		// Adding
		new WebDriverWait(driver, 10).until(
				ExpectedConditions.visibilityOf(accessCodeDisplayToggle))
				.click();
	}

	public void getAccessCodeStatus() {
		accessCodeDisplayToggle.getText();
	}

	// Find Access Code box with a lite account
	@FindBy(css = "div.protect-with-access-code.ng-scope>span")
	WebElement liteAcctAccessCodeField;

	// Click the field to encounter the paywall
	public void clickAccessCode() {
		liteAcctAccessCodeField.click();
	}

	// Find expiration date picker
	@FindBy(css = "div.expiration > label")
	WebElement expirationPicker;

	// Click expiration date picker
	public void clickExpirationDatePicker() {
		expirationPicker.click();
	}

	// Find the "Next Month" button in the calendar
	@FindBy(css = "div.pika-title >button.pika-next")
	WebElement nextMonthButton;

	// Click Next Month button
	public void clickNextMonthButton() {
		nextMonthButton.click();
	}

	// Find the date elements in the calendar
	@FindBy(css = "div.pika-lendar>table>tbody>tr>td>button")
	List<WebElement> calendarDates;

	// Click first date
	public void clickFirstDateOfNextMonth() {
		for (int i = 0; i < 1; i++) {
			calendarDates.get(i).click();
		}
	}

	// Never expiration label
	@FindBy(css = "body > div.page.ng-scope > div.ht-send.ng-scope > div.send-container > div.send-flow > div.step.controls > div > div.expiration.ng-scope > input")
	WebElement neverExpLabel;

	// Never expiration label text
	public String neverExpLabelText() {
		return neverExpLabel.getText();
	}

	// Find verify recipient checkbox
	@FindBy(css = "div.verify-recipient")
	WebElement verifyRecipientIdentity;

	// Toggle VRI setting
	public void toggleVerifyRecipientIdentity() {
		verifyRecipientIdentity.click();
	}

	// Find download receipt checkbox
	@FindBy(css = "div.return-receipt")
	WebElement downloadReceiptCheckbox;

	// Toggle Download Receipt
	public void toggleDownloadReceipt() {
		downloadReceiptCheckbox.click();
	}

	// Find error message for invalid recipients
	@FindBy(css = "div.recipient-error.anim-fade>h2")
	WebElement emailErrorMessage;

	// Verify if the error message is present
	public boolean errorMessagePresent() {
		return emailErrorMessage.isDisplayed();
	}

	// Return the error message text
	public String errorMessageText() {
		return emailErrorMessage.getText();
	}

	// Find "OK, GO BACK" button from paywall
	@FindBy(css = "div.actions.can-upgrade > div.button.ok")
	WebElement goBackButton;

	// Click GO BACK
	public void clickGoBackButton() {
		goBackButton.click();
	}

	// Verify if the button is present, i.e. paywall reached
	public boolean isGoBackButtonPresent() {
		return goBackButton.isDisplayed();
	}

	// Find "UPGRADE TO PRO" button from paywall
	@FindBy(css = "div.button.upgrade.ng-scope")
	WebElement upgradeFrmPaywallBtn;

	// Click "UPGRADE TO PRO" button
	public void clickUpgradeFrmPaywall() {
		upgradeFrmPaywallBtn.click();
	}

	// Find files sent confirmation
	@FindBy(css = "div.files-sent.completed.ad-collaborate.show-actions")
	WebElement filesSentConfirmation;

	// Is confirmation present, i.e. were files sent successfully
	public boolean isConfirmationPresent() {
		return filesSentConfirmation.isDisplayed();
	}

	// Quota message for un-verified free users sending file to >5 recipients
	@FindBy(css = ".title")
	WebElement sendLmtForUnverified;

	public boolean isSendLimitReached() {
		return sendLmtForUnverified.isDisplayed();
	}

	// Cancellation of quota message for un-verified free users sending file to
	// >5 recipients
	@FindBy(css = "div.close:nth-child(2)")
	WebElement CancelSendLmtForUnverified;

	// Click "UPGRADE TO PRO" button
	public void closeFreeQuotaMsg() {
		CancelSendLmtForUnverified.click();
	}

	// Quota message for un-verified free users sending file to >5 recipients
	@FindBy(css = ".send-failed > h2:nth-child(1)")
	WebElement dailyQuotaFreeUser;

	public boolean isdailyQuotaFreeUserReached() {
		return dailyQuotaFreeUser.isDisplayed();
	}

	@FindBy(css = ".button")
	WebElement okbtnDailyQuota;

	// Click "UPGRADE TO PRO" button
	public void ClickOkbtnDailyQuota() {
		okbtnDailyQuota.click();
	}

	// CopyLink Link on send confirmation page
	@FindBy(css = "#copy-link-send-form")
	WebElement copylinkLink;

	// Click on CopyLink Link
	public void ClickCopylinkLink() {
		copylinkLink.click();
	}

	// CopyLink URL on send confirmation page
	@FindBy(css = "a.ng-binding")
	WebElement copyLink;

	// Copy CopyLink URL
	public void ClickCopylink() {
		copyLink.click();
	}

	// File card on recipient page
	@FindBy(css = ".thumb	")
	WebElement fileCard;

	// Copy CopyLink URL
	public void ClickFileCard() {
		fileCard.click();
	}

	// Download file from top menu on recipient page file preview
	@FindBy(css = ".download")
	WebElement downloadFile;

	// Click on Download file menu
	public void clickDownloadFile() {
		downloadFile.click();
	}

	// Download files on recipient page
	@FindBy(css = ".download-button")
	WebElement downloadFiles;

	// Click on Download file menu
	public void clickDownloadFiles() {
		downloadFiles.click();
	}

	// Download button on non-previewable file preview on recipient page
	@FindBy(css = "div.download-button:nth-child(3)")
	WebElement downloadBtn;

	// Click on Download button on non-previewable file preview on recipient
	// page
	public void clickdownloadBtn() {
		downloadBtn.click();
	}

	// Recipient access code
	@FindBy(css = "input.ng-invalid")
	WebElement recipientAccessCode;

	// Recipient enter access code to download file
	public void enterRecipientAccessCode(String code) {
		recipientAccessCode.sendKeys(code);
	}

	// View file after proving access code
	@FindBy(css = ".view-files")
	WebElement viewFilesBtn;

	// Click on view files button after providing access code
	public void clickViewFilesBtn() {
		viewFilesBtn.click();
	}

	// Incorrect recipient access code error
	@FindBy(css = "p.ng-binding:nth-child(1)")
	WebElement IncorrectRecipientAccessCodeErr;

	public String IncorrectAccessCodeError() {
		return IncorrectRecipientAccessCodeErr.getText();
	}

	// Text link - Do not have access code
	@FindBy(css = ".code-entry > span:nth-child(4)")
	WebElement noAccessCode;

	// Click on text -Do not have access code
	public void clickNoAccessCode() {
		noAccessCode.click();
	}

	// Text - Your request has been sent to the person who shared these files.
	// They will contact you directly.
	@FindBy(css = ".requested")
	WebElement RequestedAccessCode;

	public String RequestedAccessCodeText() {
		return RequestedAccessCode.getText();
	}

	/********************************************************************
	 * Contacts
	 *********************************************************************/

	// Find Address Book icon
	@FindBy(css = ".address-book")
	WebElement addressBookButton;

	// Click Address Book
	public void clickAddressBook() {
		addressBookButton.click();
	}

	// Find Import CSV option
	@FindBy(css = "div.csv-toolkit-wrapper > input")
	WebElement importCsvOption;

	// Find Select Contacts option
	@FindBy(css = ".full-width")
	WebElement selectContactsOptionBtn;

	// Click Select Contacts Option
	public void clickSelectContacts() {
		selectContactsOptionBtn.click();
	}

	// Find Search contacts text box
	@FindBy(css = "input.search")
	WebElement searchContactsTextBox;

	// Enter an email to search for
	public void searchForContactEmail(String email) {
		searchContactsTextBox.sendKeys(email);
	}

	// Find checkboxes next to each contact in list
	@FindBy(css = "tr > td:nth-of-type(1)")
	List<WebElement> allContactsCheckboxes;

	// Click all contacts
	public void clickAllContacts() {
		for (WebElement contacts : allContactsCheckboxes) {
			contacts.click();
		}
	}

	// Find checkbox for first contact in list
	@FindBy(css = "tr:nth-of-type(1) > td:nth-of-type(1)")
	WebElement firstContactCheckbox;

	// Click checkbox for first contact
	public void clickFirstContact() {
		firstContactCheckbox.click();
	}

	// Find email for first contact in list
	@FindBy(css = "tr:nth-of-type(1)> td:nth-of-type(4)")
	WebElement firstContactEmail;

	// Get the first contacts email
	public String getFirstContactEmail() {
		return firstContactEmail.getText();
	}

	// Find Select Contacts button
	@FindBy(css = "button.ng-binding")
	WebElement selectContactsButton;

	// Select Contacts
	public void selectContacts() {
		selectContactsButton.click();
	}

	/********************************************************************
	 *********************************************************************/
}
