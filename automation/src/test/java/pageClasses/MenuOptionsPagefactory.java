package pageClasses;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MenuOptionsPagefactory {

	WebDriver driver;

	// Initialize webdriver
	public MenuOptionsPagefactory(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// Space menu option
	@FindBy(css = "div.opt.open.space")
	WebElement spaceMenu;

	// Click on Space menu option
	public void clickSpaceMenu() {
		spaceMenu.click();
	}
	
	//Download files menu
	@FindBy(css="div.opt.download-files.ng-scope")
	WebElement downloadFiles;
	
	//Click on Download files menu
	public void clickDownloadFiles(){
		downloadFiles.click();
	}
	
	//Add access code menu
	@FindBy(css="div.opt.access-code")
	WebElement accessCode;
	
	//Click Access code
	public void clickAccessCode(){
		accessCode.click();
	}
	
	//Access code input field
	@FindBy(css="form.animated>input.ng-pristine")
	WebElement accessCodeField;
	
	//Add access code to the field
	public void addAccessCode(String code){
		accessCodeField.sendKeys(code);
	}
	
	//Save access code element
	@FindBy(css="button.save")
	WebElement saveAccessCode;
	
	//Click on Save access code button
	public void SaveAccessCode(){
		saveAccessCode.click();
	}

	// Find Copy this Space element
	@FindBy(css="div.space-options > div.opt.duplicate-space.ng-scope")
	WebElement copySpace;
	
	public void copyTheSpace() {
		copySpace.click();
	}
	
	// Find Space Settings element
	@FindBy(css=".opt.space-settings.ng-scope")
	WebElement spaceSettings;
	
	// Click Space Settings
	public void clickSpaceSettings() {
		spaceSettings.click();
	}
	
	// Find "Anyone with the link radio" button
	@FindBy(css="li.less-padding > div.checkbox.selected")
	WebElement anyoneWithLink;
	
	// Click "Anyone with link" radio button
	public void selectAnyoneWithLink() {
		anyoneWithLink.click();
	}
	
	// Find "Only specific people" radio button
	@FindBy(css="div.menu-container > div.section:nth-of-type(1) > form > ul > li:nth-of-type(2) > div")
	WebElement onlySpecificPeople;
	
	// Click "Only specific people" radio button
	public void selectOnlySpecificPeople() {
		onlySpecificPeople.click();
	}
	
	// Find space permission drop down
	@FindBy(css=".select-menu.ng-binding")
	WebElement permissionDropDown;
	
	// Open permissions menu
	public void openPermissionsDropDown() {
		permissionDropDown.click();
	}
	
	// Find Edit
	@FindBy(css="ul.permission-select > li:nth-of-type(2)")
	WebElement editPermission;
	
	// Click Edit
	public void clickEdit() {
		editPermission.click();
	}
	
	// Find Comment and Download 
	@FindBy(css="ul.permission-select > li:nth-of-type(3)")
	WebElement commentAndDownloadPermission;
	
	// Click Comment and Download 
	public void clickCommentAndDownload() {
		commentAndDownloadPermission.click();
	}
	
	// Find Comment Only
	@FindBy(css="ul.permission-select > li:nth-of-type(4)")
	WebElement commentOnlyPermission;
	
	// Click Comment Only
	public void clickCommentOnly() {
		commentOnlyPermission.click();
	}

	// Find Save button in space settings menu
	@FindBy(css="div.content > button.save")
	WebElement saveSpaceSettings;
	
	// Click Save in space settings
	public void clickSaveSpaceSettingsBtn() {
		saveSpaceSettings.click();
	}
	
	// Find header notification for permission change
	@FindBy(css=".message.ng-binding.action-completed")
	WebElement headerNotification;
	
	// Get the notification text
	public String getNotificationText() {
		return headerNotification.getText();
	}
	
}
