package pageClasses;

import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RecipientPageFactory {
	WebDriver driver;
	WebElement element = null;
	
	static Logger log = Logger.getLogger(RecipientPageFactory.class);

	public RecipientPageFactory(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// Approval icon on recipient page
	@FindBy(css=".approve-file")
	WebElement approveFileIcon;	
	// Click approval icon on recipient page
	public void clickApproveFileIcon() {
		approveFileIcon.click();
	}

	// Click on file card to comment
	@FindBy(css = "div.annotation-media-container")
	WebElement imageLocator;
	// Click on the image to open the comment field
	public void clickOnImage() {
		imageLocator.click();
	}

	@FindBy(css="textarea.new-annotation-input")
	WebElement commentField;
	public void enterComment() {
		commentField.sendKeys("Recipient is adding comment on file");
	}

	// Find Post comment button
	@FindBy(css = ".post-comment")
	WebElement postCommentButton;
	// click on Post comment field
	public void clickPostComment() {
		postCommentButton.click();
	}

	// Find follow up checkbox
	@FindBy(css="div.follow-up")
	WebElement followUp;	
	// Find mentioned user
	@FindBy(css=".result.ng-binding.ng-scope.selected")
	WebElement mentionedUser;
	// Request follow up

	public void requestFollowUp() throws InterruptedException {
		followUp.click();
		mentionedUser.click();
	}	
	
	// Type @Mention in the comment field
	public void enterMention() throws InterruptedException {
		commentField.click();
		commentField.sendKeys("@prd");
		Thread.sleep(2000);
		commentField.sendKeys(Keys.RETURN);
	}

	// Select Needs follow up with comment
	@FindBy(css=".follow-up")
	WebElement needsFollowUp;
	
	// Select Needs follow up with comment
	public void clickNeedsFollowUp() {
		needsFollowUp.click();
	}

	// File approved by recipient notification on top
	@FindBy(css=".version-approved")
	WebElement fileApproved;
	
	// File approved confirmation text
	public String fileApprovedText() {
		return fileApproved.getText();
	}

	// Select Needs follow up with everyone
	@FindBy(css="ht-single-select-autocomplete")
	WebElement needsFollowUpEveryone;
	
	// Select Needs follow up with comment
	public void selectNeedsFollowUpEveryone() {
		needsFollowUpEveryone.click();
	}

	// Comment added verification text
	@FindBy(css=".comment-count")
	WebElement commentAdded;
	
	// File approved confirmation text
	public String commentAddedText() {
		return commentAdded.getText();
	}	
	
}
