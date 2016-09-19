package pageClasses;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class VersionViewPageFactory {

	WebDriver driver;
	WebElement element = null;
	Actions action;
	
	public VersionViewPageFactory(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	// Find Add button
	@FindBy(css="label.add-version.ng-scope>input")
	WebElement addVersionButton;
	
	// Click Add button
	public void clickAddVersionBtn() {
		addVersionButton.click();
	}
	
	// Find the Close button
	@FindBy(css="div.interrupt-content > div.ht-version-gallery > div.replacement-header > div.browse-versions > a")
	WebElement closeVersionButton;
	
	// Close the versions view
	public void closeVersionView() {
		action = new Actions(driver);
		action.moveToElement(closeVersionButton).click().build().perform();
	}
	
	// Place all versions into a List
	//@FindBy(css="ul.view-container > li > div:nth-of-type(2) > div.approval-mask") //Changes made to version UI
	@FindBy(css="ul.view-container > li > div:nth-of-type(2)")
	List<WebElement> versions;
	
	// Click the previous version
	public void clickOlderVersion() {
		//First call is to select the older version
		versions.get(1).click();
		//Second call is to open the file
//		versions.get(1).click();		
	}
	
	// Find version menu button
	@FindBy(css=".opt.open.version")
	WebElement versionMenu;
	
	// Find make current version button in menu
	@FindBy(css="div.version-options.ng-scope.live > div.make-current-version")
	WebElement makeCurrentVersionInMenu;
	
	// Find delete version button in menu
	@FindBy(css="div.version-options.ng-scope.live > div.delete-file")
	WebElement deleteVersionInMenu;
	
	// Find Make current version icon on overlay
	@FindBy(css="div.icon-container > div.make-current-version.ng-scope")
	WebElement makeCurrentIcon;
	
	// Find Delete version icon on overlay
	@FindBy(css="div.hover-actions > div.icon-container > div.delete-file.ng-scope")
	WebElement deleteVersionIcon;
	
	// Make the file current
	public void makeCurrentVersion() {
//		versionMenu.click();
//		makeCurrentVersionInMenu.click();
		
		// Make current using icon on overlay
		action = new Actions(driver);
		action.moveToElement(makeCurrentIcon).click().build().perform();
	}
	
	// Delete the old version
	public void deleteOldVersion() {
//		versionMenu.click();
//		deleteVersionInMenu.click();
		
		// Delete older version using icon on overlay
		action = new Actions(driver);
		action.moveToElement(deleteVersionIcon).click().build().perform();	
	}

	// Find Current Version label
	@FindBy(css="li.version.ng-scope > div.current-version > p ")
	WebElement currentVersionLabel;
	
	// Find file name in header
	@FindBy(css="head > title.ng-binding")
	WebElement headerFileName;
	
	// Find version count in header
	@FindBy(css=".browse-versions>h3>span:nth-of-type(3)")
	WebElement headerVersionCount;
	
	// Return text from version count in header
	public String getVersionCount() {
		return headerVersionCount.getText();
	}
	
	// Find download button
	@FindBy(css="div.version-options.ng-scope.live > div.download-file")
	WebElement downloadFileButton;

	// Find View button on overlay
	@FindBy(css="div.hover-actions > button.btn-view-space")
	WebElement viewBtnOnOverlay;
	
	// Click View button
	public void clickViewOnOverlay() {
		action = new Actions(driver);
		action.moveToElement(viewBtnOnOverlay).click().build().perform();
	}
}
