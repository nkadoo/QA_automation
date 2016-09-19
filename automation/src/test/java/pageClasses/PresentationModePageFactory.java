package pageClasses;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.SendKeysAction;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PresentationModePageFactory {
	
	WebDriver driver;
	Actions action;
	
	// Initialize 
	public PresentationModePageFactory(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	// Find Mixed mode button
	@FindBy(css="li.mode:nth-of-type(1)")
	WebElement mixedModeButton;
	
	// Click Mixed mode
	public void clickMixedMode() {
		mixedModeButton.click();
	}
	
	// Find File mode button
	@FindBy(css="li.mode:nth-of-type(2)")
	WebElement fileModeButton;
	
	// Click File mode
	public void clickFileMode() {
		fileModeButton.click();
	}
	
	// Find Feedback mode button
	@FindBy(css="li.mode:nth-of-type(3)")
	WebElement feedbackModeButton;
	
	// Click Feedback mode
	public void clickFeedbackMode() {
		feedbackModeButton.click();
	}
	
	// Find home slide
	@FindBy(css=".slide.space.ng-scope")
	WebElement homeSlide;
	
	// Find preview item
	@FindBy(css=".preview-item")
	WebElement filePreview;
	
	// Scroll through files
	public void scrollThroughFiles(int numberOfFiles) throws InterruptedException {
		action = new Actions(driver);
		action.moveToElement(homeSlide).click().sendKeys(Keys.ARROW_RIGHT).build().perform();
		
		for (int i = 0; i < numberOfFiles-1; i++) {
			action.moveToElement(filePreview).click().sendKeys(Keys.ARROW_RIGHT).build().perform();
			Thread.sleep(1000);
		}
	}
	
	// Find the HT logo in the upper left
	@FindBy(css=".default")
	WebElement htLogo;
	
	// Click the logo to exit presentation
	public void exitPresentation() {
		htLogo.click();
	}
}
