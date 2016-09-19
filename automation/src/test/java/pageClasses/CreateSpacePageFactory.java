package pageClasses;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.Constants;
import utilities.TestUtility;

public class CreateSpacePageFactory {

	WebDriver driver;
	Actions action;
	String date = new SimpleDateFormat("MMddyyyyHHmm").format(new Date());
	static Logger log = Logger.getLogger(CreateSpacePageFactory.class);

	// Initialize webdriver
	public CreateSpacePageFactory(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	/********************************************************************
	 * All spaces view
	 *********************************************************************/

	// Find onboarding close button
	@FindBy(css = "div.close-button")
	WebElement onboardingCloseButton;

	// Close onboarding screen
	public void closeOnboarding() {
		onboardingCloseButton.click();
	}

	// Create a space button on new login
	@FindBy(xpath = ".//*[@id='ng-app']/body/div[1]/div[1]/div[3]/div/ul[1]/li[1]/div/div/input")
	WebElement createASpace;

	// Click on Create A space button for newly signed up user
	public void createASpace() {
		log.info("Click on Create a Space button");
		createASpace.click();
	}

	// Create new space button on space header
	@FindBy(css = "div.create-space-menu.ng-scope")
	WebElement createNewSpace;

	// Click on Create New space button for user who has a space
	public void createNewSpace() {
		createNewSpace.click();
	}

	// Click on the first space
	@FindBy(css = "li.space-card-container.fadeIn.ng-scope:nth-of-type(1)")
	WebElement firstSpace;

	// Click on the first space created
	public void clickFirstSpace() {
		firstSpace.click();
	}

	// Find copy link icon on space card overlay
	@FindBy(css = ".copy-link.zeroclipboard-is-hover")
	WebElement copyLinkOnOverlay;

	// Click copy link icon on space card
	public void clickCopyLinkOnSpaceCard() {
		action = new Actions(driver);
		action.moveToElement(firstSpace).moveToElement(copyLinkOnOverlay)
				.click().build().perform();
	}

	// Find delete icon on space card overlay
	@FindBy(css = ".delete-space.ng-scope")
	WebElement deleteIconOnOverlay;

	// Click delete icon on space card
	public void clickDeleteOnSpaceCard() {
		action = new Actions(driver);
		action.moveToElement(firstSpace).moveToElement(deleteIconOnOverlay)
				.click().build().perform();
	}

	// Click on Delete confirmation button on Space
	// @FindBy(css = "span.acknowledge")
	@FindBy(css = "button.btn-confirm")
	WebElement deleteConfirmButton;

	// Click on Delete button
	public void clickDeleteConfirmButton() {
		deleteConfirmButton.click();
	}

	/********************************************************************
	 * Collections
	 *********************************************************************/
	// Find New Collection text
	@FindBy(css = ".filter.add-new")
	WebElement newCollectionText;

	// Click New Collection
	public void clickNewCollection() {
		// newCollectionText.click();
		action = new Actions(driver);
		action.moveToElement(newCollectionText).click().build().perform();
	}

	// Put all space cards in a list
	@FindBy(css = "li.space-card-container.fadeIn.ng-scope")
	List<WebElement> spaceCards;

	// Click the first three spaces
	public void clickThreeSpaces() {
		for (int i = 0; i < 3; i++) {
			spaceCards.get(i).click();
		}
	}

	// Find Next button
	@FindBy(css = ".btn-batch-action.next")
	WebElement nextButton;

	// Click Next button
	public void clickNextButton() {
		nextButton.click();
	}

	// Find Collection Name text box
	@FindBy(css = ".input-collection-name")
	WebElement collectionNameTextBox;

	// Enter Collection name
	public void enterCollectionName(String name) {
		collectionNameTextBox.sendKeys(name);
	}

	// Find Create Collection button
	@FindBy(css = ".btn-create-collection")
	WebElement createCollectionBtn;

	// Click Create Collection button
	public void clickCreateCollectionBtn() {
		createCollectionBtn.click();
	}

	// Find Cancel Collection text
	@FindBy(css = ".btn-cancel")
	WebElement cancelCollectionText;

	// Cancel collection creation
	public void clickCancelCollection() {
		cancelCollectionText.click();
	}

	// Find collection created message
	@FindBy(css = ".message.ng-binding.action-completed")
	WebElement collectionCreatedMessage;

	// Get collection created message text
	public String getMessageText() {
		return collectionCreatedMessage.getText();
	}

	/********************************************************************
	 *********************************************************************/

	/********************************************************************
	 * Space view
	 *********************************************************************/

	// Upload a file to space
	@FindBy(css = "i.provider.my-computer > label > input[type=\"file\"")
	WebElement fileUpload;

	// Upload a file to Spaces
	public void uploadFiletoSpace() {
		fileUpload.sendKeys(Constants.File_path_For_Space1);
	}

	// Create space via Hightail Oauth
	@FindBy(css = "i.provider:nth-child(2)")
	WebElement hightailUpload;

	// click on hightail upload
	public void clickHightailUpload() {
		hightailUpload.click();
	}

	// Create space via Dropbox
	@FindBy(css = "i.provider:nth-child(3)")
	WebElement dropboxUpload;

	// click on dropbox upload
	public void clickDropboxUpload() {
		dropboxUpload.click();
	}

	// Create space via Google Drive
	@FindBy(css = "i.provider:nth-child(4)")
	WebElement googleDriveUpload;

	// click on Google Drive upload
	public void clickGoogleUpload() {
		googleDriveUpload.click();
	}

	// Create space via Microsoft One Drive
	@FindBy(css = "i.provider:nth-child(5)")
	WebElement oneDriveUpload;

	// click on Microsoft One Drive upload
	public void clickOneDriveUpload() {
		oneDriveUpload.click();
	}

	// Create a Space name element
	@FindBy(css = "input.inline-edit")
	WebElement spaceName;

	// Name the newly created space
	public void spaceName() {
		// Create Date as current Date in MM DD YYYY HH mm format
		// String date = new SimpleDateFormat("MMddyyyyHHmm").format(new
		// Date());
		// Generate random email using the Date format
		String newSpaceName = "New Space " + date;
		spaceName.sendKeys(newSpaceName);
		spaceName.sendKeys(Keys.ENTER);
	}

	// Give the space a custom name
	public void spaceName(String name) {
		spaceName.sendKeys(name);
		spaceName.sendKeys(Keys.ENTER);
	}

	// Find space name
	@FindBy(css = "div.full-width-container > h1")
	WebElement spaceNameElement;

	// Get the space name
	public String getSpaceName() {
		// String date = new SimpleDateFormat("MMddyyyyHHmm").format(new
		// Date());
		// return "New Space " + date;
		return spaceNameElement.getText();
	}

	// Space description element
	@FindBy(css = "div.space-description.ng-binding.show")
	WebElement spaceDesc;

	// Click on space description element
	public void spaceDesc() {
		spaceDesc.click();
	}

	// Description for Spaces element
	@FindBy(css = ".space-description-editor")
	WebElement addSpaceDesc;

	// Enter description
	public void EnterSpaceDesc() {
		spaceDesc.sendKeys("This description is added for new space");
	}

	// Dashboard moved to space menu
	/*
	 * // Find dashboard icon
	 * 
	 * @FindBy(css = "a.view-activity") WebElement dashboardIcon;
	 * 
	 * // Open dashboard public void openDashboard() { dashboardIcon.click(); }
	 */

	// Upload background image for Space element
	@FindBy(css = "div.add-bg > input[type=\"file\"]")
	WebElement backgroundImage;

	// Upload file to spaces background
	public void uploadSpacesBackgroundImage() {
		backgroundImage.sendKeys(Constants.Space_background_image);
	}

	// Save background image element
	@FindBy(css = ".save")
	WebElement saveBackgroundImage;

	// Click on Save button on uploading space background image
	public void saveBackgroundImageButton() {
		saveBackgroundImage.click();
	}

	// Remove background image for space element
	@FindBy(css = ".remove-bg")
	WebElement removeBackgroundImage;

	// CLick on Remove background image for space button
	public void removeBackgroundImageSpace() {
		removeBackgroundImage.click();
	}

	public boolean isRemoveImageAvailable() {
		removeBackgroundImage.isDisplayed();
		return true;
	}

	// Find the first file in the space
	@FindBy(css = "ul.file-list > li.file-thumb:nth-of-type(1)")
	WebElement firstFile;

	// Open the first file
	public void clickFirstFile() {
		firstFile.click();
	}

	// Find View button on file card overlay
	@FindBy(css = ".btn-view-space")
	WebElement viewBtnOnFileCard;

	// Click View buton on file card
	public void clickViewOnFileCard() {
		action = new Actions(driver);
		action.moveToElement(firstFile).moveToElement(viewBtnOnFileCard)
				.click().build().perform();
	}

	// Find rename button on file card overlay
	@FindBy(css = ".rename-file.ng-scope")
	WebElement renameBtnOnFileCard;

	// Click rename on file card
	public void clickRenameOnFileCard() {
		action = new Actions(driver);
		action.moveToElement(firstFile).moveToElement(renameBtnOnFileCard)
				.click().build().perform();
	}

	// Find add version on file card overlay
	@FindBy(css = ".add-version-target")
	WebElement addVersionOnFileCard;

	// Click add version on file card
	public void clickAddVersionOnFileCard() {
		action = new Actions(driver);
		action.moveToElement(firstFile).moveToElement(addVersionOnFileCard)
				.click().build().perform();
	}

	// Find download on file card overlay
	@FindBy(css = "div.icon-container > div.download-file.ng-scope")
	WebElement downloadOnFileCard;

	// Click download on file card
	public void clickDwnldOnFileCard() {
		action = new Actions(driver);
		action.moveToElement(firstFile).moveToElement(downloadOnFileCard)
				.click().build().perform();
	}

	// Find delete on file card overlay
	@FindBy(css = "div.icon-container > div.download-file.ng-scope")
	WebElement deleteOnFileCard;

	public void clickDeleteOnFileCard() {
		action = new Actions(driver);
		action.moveToElement(firstFile).moveToElement(deleteOnFileCard).click()
				.build().perform();
	}

	// Find delete confirmation button on file card overlay
	@FindBy(css = "button.btn-confirm")
	WebElement deleteConfirmationOnFileCard;

	// Click delete confirmation on file card
	public void clickDeleteConfirmOnFileCard() {
		deleteConfirmationOnFileCard.click();
	}

	// // First image web element of the space
	// @FindBy(css = "button.btn-view-space")
	// WebElement firstSpaceFile;
	//
	// // Click on Space first image
	// public void clickFirstImage() {
	// firstSpaceFile.click();
	// }

	/********************************************************************
	 * Add file menu and elements within
	 *********************************************************************/

	// Add file menu
	@FindBy(css = "div.ht-add-files-menu.ng-isolate-scope > div.opt.open.space")
	WebElement addFileButton;

	// Click on Add file menu button
	public void clickAddFileButton() {
		addFileButton.click();
	}

	// Find add more files from desktop element
	@FindBy(css = "div.options.live > label > input[type=\"file\"")
	WebElement addMoreFilesDesktop;

	// Upload one more file to Spaces
	public void uploadMoreFilesFromDesktop() {
		// addMoreFiles.sendKeys(Constants.File_path_For_Space2);
		addMoreFilesDesktop.sendKeys(Constants.File_path_For_Space2);
	}

	// Find add more files from Hightail element
	@FindBy(css = "div.options.live > div.opt.add-files-hightail")
	WebElement addMoreFilesHT;

	// Open Hightail file picker
	public void openHTFilePicker() {
		addMoreFilesHT.click();
	}

	// Find add more files from Dropbox element
	@FindBy(css = "div.options.live > div.opt.add-files-dropbox")
	WebElement addMoreFilesDropbox;

	// Open Dropbox file picker
	public void openDropboxFilePicker() {
		addMoreFilesDropbox.click();
	}

	// Find add more files from GoogleDrive element
	@FindBy(css = "div.options.live > div.opt.add-files-googledrive")
	WebElement addMoreFilesGoogleDrive;

	// Open Google Drive file picker
	public void openGoogleDriveFilePicker() {
		addMoreFilesGoogleDrive.click();
	}

	// Find add more files from OneDrive element
	@FindBy(css = "div.options.live > div.opt.add-files-onedrive")
	WebElement addMoreFilesOneDrive;

	// Open OneDrive file picker
	public void openOneDriveFilePicker() {
		addMoreFilesOneDrive.click();
	}

	/********************************************************************
	 *********************************************************************/

	/********************************************************************
	 * Space menu button and elements within
	 *********************************************************************/

	// Space menu option
	@FindBy(css = "div.ht-action-menu > div.opt.open.space")
	WebElement spaceMenu;

	// Click on Space menu option
	public void clickSpaceMenu() {
		spaceMenu.click();
	}

	// Find Request approval
	@FindBy(css = "div.space-options > div.opt.approval.ng-scope")
	WebElement requestApproval;

	// Click Request approval element
	public void clickRequestApproval() {
		requestApproval.click();
	}

	// Download files menu
	@FindBy(css = "div.opt.download-files.ng-scope")
	WebElement downloadFiles;

	// Click on Download files menu
	public void clickDownloadFiles() {
		downloadFiles.click();
	}

	// Add access code menu
	@FindBy(css = "div.opt.access-code")
	WebElement accessCode;

	// Click Access code
	public void clickAccessCode() {
		accessCode.click();
	}

	// Access code input field
	@FindBy(css = "input.ng-pristine")
	WebElement accessCodeField;

	// Add access code to the field
	public void addAccessCode(String code) {
		accessCodeField.sendKeys(code);
	}

	// Save access code element
	@FindBy(css = "button.save")
	WebElement saveAccessCode;

	// Click on Save access code button
	public void SaveAccessCode() {
		saveAccessCode.click();
	}

	// Find View Activity
	@FindBy(css = "div.opt.view-activity.ng-scope>a")
	WebElement viewActivityElement;

	// Open dashboard
	public void openDashboard() {
		viewActivityElement.click();
	}

	// Find Copy this Space element
	@FindBy(css = "div.space-options > div.opt.duplicate-space.ng-scope")
	WebElement copySpace;

	public void copyTheSpace() {
		copySpace.click();
	}

	// Find Copy this space confirmation button
	@FindBy(css = ".message>button")
	WebElement copySpaceConfirmationBtn;

	public void copySpaceConfirm() {
		copySpaceConfirmationBtn.click();
	}

	// Find Present this Space element
	@FindBy(css = ".opt.present-space")
	WebElement presentThisSpace;

	// Click Present this Space
	public void clickPresentThisSpace() {
		presentThisSpace.click();
	}

	// Find Space Settings element
	@FindBy(css = ".opt.space-settings.ng-scope")
	WebElement spaceSettings;

	// Click Space Settings
	public void clickSpaceSettings() {
		spaceSettings.click();
	}

	// Find "Anyone with the link radio" button
	@FindBy(css = "li.less-padding > div.checkbox.selected")
	WebElement anyoneWithLink;

	// Click "Anyone with link" radio button
	public void selectAnyoneWithLink() {
		anyoneWithLink.click();
	}

	// Find "Only specific people" radio button
	@FindBy(css = "div.menu-container > div.section:nth-of-type(1) > form > ul > li:nth-of-type(2) > div")
	WebElement onlySpecificPeople;

	// Click "Only specific people" radio button
	public void selectOnlySpecificPeople() {
		onlySpecificPeople.click();
	}

	// Find space permission drop down
	@FindBy(css = ".select-menu.ng-binding")
	WebElement permissionDropDown;

	// Open permissions menu
	public void openPermissionsDropDown() {
		permissionDropDown.click();
	}

	// Find Edit
	@FindBy(css = "ul.permission-select > li:nth-of-type(2)")
	WebElement editPermission;

	// Click Edit
	public void clickEdit() {
		editPermission.click();
	}

	// Find Comment and Download
	@FindBy(css = "ul.permission-select > li:nth-of-type(3)")
	WebElement commentAndDownloadPermission;

	// Click Comment and Download
	public void clickCommentAndDownload() {
		commentAndDownloadPermission.click();
	}

	// Find Comment Only
	@FindBy(css = "ul.permission-select > li:nth-of-type(4)")
	WebElement commentOnlyPermission;

	// Click Comment Only
	public void clickCommentOnly() {
		commentOnlyPermission.click();
	}

	// Find Any follower approval
	@FindBy(css = "div.menu-container > div.section:nth-of-type(2) > form > ul > li:nth-of-type(1)")
	WebElement approveAnyFollower;

	// Select any follower can approve
	public void selectApproveAnyFollower() {
		approveAnyFollower.click();
	}

	// Find Only followers requested approval
	@FindBy(css = "div.menu-container > div.section:nth-of-type(2) > form > ul > li:nth-of-type(2)")
	WebElement approveOnlyRequested;

	// Select only followers requested can approve
	public void selectApproveOnlyRequested() {
		approveOnlyRequested.click();
	}

	// Find No one can approve
	@FindBy(css = "div.menu-container > div.section:nth-of-type(2) > form > ul > li:nth-of-type(3)")
	WebElement approvalsOff;

	// Turn off approvals
	public void turnOffApprovals() {
		approvalsOff.click();
	}

	// Find Save button in space settings menu
	@FindBy(css = "div.content > button.save")
	WebElement saveSpaceSettings;

	// Click Save in space settings
	public void clickSaveSpaceSettingsBtn() {
		saveSpaceSettings.click();
	}

	// Find header notification for permission change
	@FindBy(css = ".message.ng-binding.action-completed")
	WebElement headerNotification;

	// Get the notification text
	public String getNotificationText() {
		return headerNotification.getText();
	}

	// Find all the files displayed in the space
	@FindBy(css = "ul.file-list > li")
	List<WebElement> filesInSpace;

	// Return number of files
	public int numberOfFiles() {
		return filesInSpace.size();
	}

	/********************************************************************
	 *********************************************************************/

	/********************************************************************
	 * File level elements
	 *********************************************************************/

	// Find close file button
	@FindBy(css = ".close-button")
	WebElement closeFileButton;

	// Close file and return to space
	public void closeFile() {
		closeFileButton.click();
	}

	// Find the file name
	@FindBy(css = ".name.ng-binding")
	WebElement fileName;

	// Get the file name
	public String getFileName() {
		return fileName.getText();
	}

	// Version text on File details page
	@FindBy(css = ".version")
	WebElement versionText;

	// Click on the version text
	public void clickVersionText() {
		versionText.click();
	}

	// Check if new version text is available
	public boolean isVersionTextAvailable() {
		return versionText.isDisplayed();
		// return true;
	}

	// Return the text from the versionText element
	public String versionNumber() {
		return versionText.getText();
	}

	// Approve file web element
	@FindBy(css = "div.approve-file.ng-scope")
	WebElement approveFile;

	// Click on Approve file option
	public void clickApproveFile() {
		approveFile.click();
	}

	// Approved file web element
	@FindBy(css = ".version-approved")
	WebElement fileApproved;

	// Check if file is approved
	public boolean isFileApproved() {
		fileApproved.isDisplayed();
		return true;
	}

	// Un-approve button web element
	@FindBy(css = "div.approve-file.ng-scope.approved")
	WebElement unApprove;

	// CLick on Un-approve button
	public void clickUnapprove() {
		unApprove.click();
	}

	// Click on file options on file details page
	@FindBy(css = "div.opt.open.file")
	WebElement fileDetailsOptions;

	// Click on File details options
	public void openFileDetailsOptions() {
		fileDetailsOptions.click();
	}

	// Find request approval in file details menu
	@FindBy(css = "div.file-options > div.opt.approval.ng-scope")
	WebElement fileMenuRequestApprovalElement;

	// Request approval from file menu
	public void fileMenuRequestApproval() {
		fileMenuRequestApprovalElement.click();
	}

	// Delete file option from file details menu
	@FindBy(css = "div.file-options > div.delete-file")
	WebElement deleteFile;

	// Click on Delete file option on File details page
	public void deleteFile() {
		deleteFile.click();
	}

	// Find delete confirmation button
	@FindBy(css = "button.ng-binding")
	WebElement deleteConfirmationBtn;

	// Click delete confirmation button
	public void clickDeleteConfirmationBtn() {
		deleteConfirmationBtn.click();
	}

	// Verify that the delete confirmation button is present
	public boolean isDeleteConfirmationPresent() {
		return deleteConfirmationBtn.isDisplayed();
	}

	// Add file options on file details page
	@FindBy(css = "div.opt.new-version.ng-scope")
	WebElement addFileOption;

	// Click on File add option on File details page
	public void clickAddFileOptions() {
		openFileDetailsOptions();
		addFileOption.click();
	}

	// Find file version from desktop element
	@FindBy(css = "div.add-file-menu > label.opt.add-files-desktop.ng-scope > input")
	WebElement addFileVersionFromDesktop;

	public void addFileVersion() {
		addFileVersionFromDesktop.sendKeys(Constants.File_path_For_Space2);
	}

	// Find Image locator and click on it
	@FindBy(css = "div.annotation-media-container")
	WebElement imageLocator;

	// Click on the image to open the comment field
	public void clickOnImage() {
		imageLocator.click();
	}

	// Locate guest commenter name field
	@FindBy(css = ".guest-user-name")
	WebElement guestNameField;

	// Enter guest's name
	public void enterGuestName() {
		guestNameField.sendKeys("Guest Commenter");
	}

	// Locate guest commenter email field
	@FindBy(css = ".guest-user-email")
	WebElement guestEmailField;

	// Enter guest's email
	public void enterGuestEmail() {
		guestEmailField.sendKeys("gst_" + TestUtility.getRandomEmail());
	}

	// Locate sign up link for guest
	@FindBy(css = ".guest-signup")
	WebElement guestSignUpLink;

	// Verify that the link is displayed
	public boolean isGuestSignUpPresent() {
		return guestSignUpLink.isDisplayed();
	}

	// Locate comment field
	// @FindBy(css = "textarea.ng-valid:nth-child(2)") -changed in follow ups v2
	@FindBy(css = "textarea.new-annotation-input")
	WebElement commentField;

	// Type @Mention in the comment field
	public void enterMention() throws InterruptedException {
		commentField.click();
		commentField.sendKeys("@prd");
		Thread.sleep(2000);
		commentField.sendKeys(Keys.RETURN);
	}

	// Type @Mention in the comment field
	public void enterComment() {
		commentField.sendKeys("This is the comment added on the file");
	}

	// Find Post comment button
	@FindBy(css = ".post-comment")
	WebElement postCommentButton;

	// click on Post comment field
	public void clickPostComment() {
		postCommentButton.click();
	}

	// Find follow up checkbox
	// @FindBy(css = "div.needs-followup") - changed in follow up v2
	@FindBy(css = "div.follow-up")
	WebElement followUp;

	// Find mentioned user
	@FindBy(css = ".result.ng-binding.ng-scope.selected")
	WebElement mentionedUser;

	// Request follow up
	public void requestFollowUp() throws InterruptedException {
		followUp.click();
		mentionedUser.click();
	}

	// Find follow up annotation
	@FindBy(css = "div.followup-annotation")
	WebElement followUpAnnotation;

	// Check if the annotation is present
	public boolean isFollowUpDisplayed() {
		return followUpAnnotation.isDisplayed();
	}

	// Find current zoom percent text. Only for image files
	@FindBy(css = ".current-zoom-percent")
	WebElement percentText;

	// Get current zoom percentage
	public String currentZoomPercent() {
		return percentText.getText();
	}

	// Click zoom percent
	public void zoomToFitImage() {
		percentText.click();
	}

	// Find page indicator for documents
	@FindBy(css = ".page-indicator")
	WebElement pageIndicatorText;

	// Click page indicator
	public void zoomToFitDocument() {
		pageIndicatorText.click();
	}

	// Find zoom in button
	@FindBy(css = ".zoom-in")
	WebElement zoomInButton;

	// Zoom in
	public void zoomIn(int i) {	
		for (int x = 0; x < i; x++) {
			zoomInButton.click();
		}
	}

	// Find zoom out button
	@FindBy(css = ".zoom-out")
	WebElement zoomOutButton;

	// Zoom out
	public void zoomOut(int i) {
		for (int x = 0; x < i; x++) {
			zoomOutButton.click();
		}
	}

	/********************************************************************
	 *********************************************************************/

	/********************************************************************
	 * Request for approval flow
	 *********************************************************************/

	// Find follower icon
	@FindBy(css = "div.invited-user-icon.ng-scope")
	WebElement followerIcon;

	// Select follower to approve
	public void selectFollower() {
		followerIcon.click();
	}

	// Find file to approve
	@FindBy(css = "div.file-selection-list > ul.file-list > li.file-thumb")
	WebElement fileToApprove;

	// Select file to approve
	public void selectFileToApprove() {
		fileToApprove.click();
	}

	// Find approval header text
	@FindBy(css = "div.header-container.editing > span")
	WebElement approvalHeader;

	// Return approval header text for verification
	public String approvalHeaderText() {
		return approvalHeader.getText();
	}

	// Find Cancel approval button
	@FindBy(css = "div.header-container > div > button.cancel.button")
	WebElement cancelApprovalBtn;

	// Cancel approval
	public void cancelApproval() {
		cancelApprovalBtn.click();
	}

	// Find Send for Approval button
	@FindBy(css = "div > button.save.button.animated")
	WebElement sendForApprovalBtn;

	// Send for Approval
	public void sendForApproval() {
		sendForApprovalBtn.click();
	}

	// Find file pending approval in file header
	@FindBy(css = "span.version-approval-pending.ng-binding.ng-scope")
	WebElement pendingApprovalElement;

	// Get approval pending text
	public String getPendingApprovalText() {
		return pendingApprovalElement.getText();
	}

	/********************************************************************
	 *********************************************************************/

	/********************************************************************
	 * Coach Marks
	 *********************************************************************/

	// Find coach mark title
	@FindBy(css = "div.info > div.coachmark-title")
	WebElement coachMarkTitleText;

	// Return coach mark title text
	public String coachMarkTitle() {
		return coachMarkTitleText.getText();
	}

	// Find "Do this later" text
	@FindBy(css = "div.coachmark-action")
	WebElement doThisLaterText;

	// Click "Do this later"
	public void clickDoThisLater() {
		doThisLaterText.click();
	}

	/********************************************************************
	 *********************************************************************/

	/*
	 * // Delete space from menu
	 * 
	 * @FindBy(css = "div.opt.delete-space.ng-scope") WebElement
	 * deleteMenuOption;
	 * 
	 * // click Delete space menu option public void clickDeletemenuOption() {
	 * deleteMenuOption.click(); }
	 * 
	 * // Delete space confirmation message
	 * 
	 * @FindBy(css = ".message") WebElement deleteSpaceConfirm;
	 * 
	 * // Check delete space confirmation message public boolean
	 * isDeleteSpaceMsg() { deleteSpaceConfirm.isDisplayed(); return true; }
	 */

}
