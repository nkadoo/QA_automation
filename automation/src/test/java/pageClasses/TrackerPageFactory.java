package pageClasses;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.log4testng.Logger;

public class TrackerPageFactory {
	WebDriver driver;
	Actions action;
	
	static Logger log = Logger.getLogger(TrackerPageFactory.class);
	
	public TrackerPageFactory(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	// Find all details for Sent 
	@FindBy(css="div.sent > table > tbody > tr")
	List<WebElement> allSentDetails;
	
	// Find recipient email of last send
	@FindBy(css="div.sent > table > tbody > tr:nth-of-type(1) > td.recipient")
	WebElement lastSendRecipientText;
	
	// Return email of last recipient
	public String lastSendRecipient() {
		// return lastSendRecipientText.getText();
		return new WebDriverWait(driver, 90).until(
				ExpectedConditions.visibilityOf(lastSendRecipientText)).getText();
	}
	
}
