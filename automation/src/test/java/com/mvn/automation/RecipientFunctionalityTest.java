package com.mvn.automation;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pageClasses.HomePageFactory;
import pageClasses.MyAccountPageFactory;
import pageClasses.RecipientPageFactory;
import pageClasses.SigninPageFactory;
import pageClasses.SendPageFactory;
import utilities.Constants;
import utilities.ExcelUtility;
import utilities.TestUtility;

public class RecipientFunctionalityTest {
	private WebDriver driver;
	SigninPageFactory signIn;
	HomePageFactory homePage;
	SendPageFactory sendPage;
	RecipientPageFactory recipientPage;
	MyAccountPageFactory myAccount;

	static Logger log = Logger.getLogger(RecipientFunctionalityTest.class);

	@Parameters({ "browserType", "env" })
	@BeforeMethod
	public void setUp(String browser, String envir) throws Exception {
		PropertyConfigurator.configure("log4j.properties");

		driver = TestUtility.setupDriver(browser, envir);
		Assert.assertNotNull("Driver can not be null", driver);

		signIn = new SigninPageFactory(driver);
		homePage = new HomePageFactory(driver);
		sendPage = new SendPageFactory(driver);
		myAccount = new MyAccountPageFactory(driver);
		recipientPage = new RecipientPageFactory(driver);
	}

	@Test(groups = { "positive" })
	public void RecipientFileApproval() throws Exception {
		log.info("Running RecipientFileApproval testcase");

		signIn.clickSigninLink();
		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String sendEmail = ExcelUtility.getCellData(12, 1);
		String sendPwd = ExcelUtility.getCellData(12, 2);
		String recipientEmail = ExcelUtility.getCellData(13, 1);
		String recipientPwd = ExcelUtility.getCellData(13, 2);

		Thread.sleep(2000);
		signIn.userEmail(sendEmail);
		Thread.sleep(500);
		signIn.userPassword(sendPwd);
		signIn.clickSignInWithEmail();
		Thread.sleep(3000);

		homePage.clickSendInHeader();
		sendPage.enterRecipientEmail(recipientEmail);
		Thread.sleep(500);
		sendPage.enterSubject("Test recipient approval process");
		sendPage.enterMessage("Generic message");
		sendPage.uploadFileToSend();
		sendPage.clickNextBtn();
		sendPage.clickSendBtn();
		Thread.sleep(5000);

		// Verify files were sent successfully
		Assert.assertTrue(sendPage.isConfirmationPresent());
		Thread.sleep(1000);

		//Copy Recipient URL
		WebElement recipientUrl = driver.findElement(By.cssSelector("#copy-link-send-form"));
		String receipient_Url = recipientUrl.getAttribute("data-clipboard-text");		

		//Logout from Sender
		myAccount.openSideBarMenu();
		Thread.sleep(1000);
		myAccount.clickSignOut();
		Thread.sleep(3000);	
		
		//Launch recipient URL
		driver.get(receipient_Url);	
		Thread.sleep(3000);	
		
		//Recipient approving file by signing-in
		sendPage.ClickFileCard();
		Thread.sleep(1000);
		recipientPage.clickApproveFileIcon();
		signIn.enterApprovalFileSignInEmail(recipientEmail);
		Thread.sleep(500);
		signIn.enterApprovalFileSignInPwd(recipientPwd);
		Thread.sleep(500);
		signIn.clickapprovalSignInBtn();
		Thread.sleep(1000);
		
		Assert.assertEquals(recipientPage.fileApprovedText(), "VERSION 1 APPROVED BY YOU");
		Thread.sleep(1000);

		myAccount.closeHelp(); 
		Thread.sleep(500);
		myAccount.openSideBarMenu();
		Thread.sleep(2000);
		myAccount.clickSignOut();
		log.info("RecipientFileApproval - Passed"); 	
	}

	@Test(groups = { "negative" })
	public void RecipientFileApprovalIncorrectCredential() throws Exception {
		log.info("Running RecipientFileApprovalIncorrectCredential testcase");

		signIn.clickSigninLink();
		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String sendEmail = ExcelUtility.getCellData(12, 1);
		String sendPwd = ExcelUtility.getCellData(12, 2);
		String recipientEmail = ExcelUtility.getCellData(13, 1);

		Thread.sleep(2000);
		signIn.userEmail(sendEmail);
		Thread.sleep(500);
		signIn.userPassword(sendPwd);
		signIn.clickSignInWithEmail();
		Thread.sleep(3000);

		homePage.clickSendInHeader();
		sendPage.enterRecipientEmail(recipientEmail);
		Thread.sleep(500);
		sendPage.enterSubject("Test recipient approval process with incorrect credential");
		sendPage.enterMessage("Generic message");
		sendPage.uploadFileToSend();
		sendPage.clickNextBtn();
		sendPage.clickSendBtn();
		Thread.sleep(5000);

		// Verify files were sent successfully
		Assert.assertTrue(sendPage.isConfirmationPresent());
		Thread.sleep(1000);

		//Copy Recipient URL
		WebElement recipientUrl = driver.findElement(By.cssSelector("#copy-link-send-form"));
		String receipient_Url = recipientUrl.getAttribute("data-clipboard-text");		

		//Logout from Sender
		myAccount.openSideBarMenu();
		Thread.sleep(1000);
		myAccount.clickSignOut();
		Thread.sleep(3000);	
		
		//Launch recipient URL
		driver.get(receipient_Url);	
		Thread.sleep(3000);	
		
		//Recipient approving file by signing-in
		sendPage.ClickFileCard();
		Thread.sleep(1000);
		recipientPage.clickApproveFileIcon();
		signIn.enterApprovalFileSignInEmail("incorrect@credential.com");
		Thread.sleep(500);
		signIn.enterApprovalFileSignInPwd("garbage");
		Thread.sleep(500);
		signIn.clickapprovalSignInBtn();
		Thread.sleep(500);
		signIn.clickapprovalSignInBtn();
		Thread.sleep(500);
		signIn.clickapprovalSignInBtn();
		Thread.sleep(500);
		signIn.clickapprovalSignInBtn();
		Thread.sleep(500);		
		Assert.assertEquals(signIn.ErrorApprovalSignIn(), "Hmmm...there's still a problem. Make sure your email address and password are correct.");
		log.info("RecipientFileApprovalIncorrectCredential - Passed"); 	
	}	
	
	@Test(groups = { "positive", "Smoke"})
	public void RecipientComment() throws Exception {
		log.info("Running RecipientComment test case");

		signIn.clickSigninLink();
		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String sendEmail = ExcelUtility.getCellData(12, 1);
		String sendPwd = ExcelUtility.getCellData(12, 2);
		String recipientEmail = ExcelUtility.getCellData(13, 1);
		String recipientPwd = ExcelUtility.getCellData(13, 2);

		Thread.sleep(2000);
		signIn.userEmail(sendEmail);
		Thread.sleep(500);
		signIn.userPassword(sendPwd);
		signIn.clickSignInWithEmail();
		Thread.sleep(3000);

		homePage.clickSendInHeader();
		sendPage.enterRecipientEmail(recipientEmail);
		Thread.sleep(500);
		sendPage.enterSubject("Test recipient comment test");
		sendPage.enterMessage("Generic message");
		sendPage.uploadFileToSend();
		sendPage.clickNextBtn();
		sendPage.clickSendBtn();
		Thread.sleep(5000);

		// Verify files were sent successfully
		Assert.assertTrue(sendPage.isConfirmationPresent());
		Thread.sleep(1000);

		//Copy Recipient URL
		WebElement recipientUrl = driver.findElement(By.cssSelector("#copy-link-send-form"));
		String receipient_Url = recipientUrl.getAttribute("data-clipboard-text");		

		//Logout from Sender
		myAccount.openSideBarMenu();
		Thread.sleep(1000);
		myAccount.clickSignOut();
		Thread.sleep(3000);	
		
		//Launch recipient URL
		driver.get(receipient_Url);	
		Thread.sleep(3000);	
		
		//Comment on the file
		sendPage.ClickFileCard();
		Thread.sleep(1000);
		recipientPage.clickOnImage();
		recipientPage.enterComment();
		Thread.sleep(500);		
		recipientPage.clickPostComment();
		Thread.sleep(1000);
		
		//Recipient signing-in
		signIn.enterApprovalFileSignInEmail(recipientEmail);
		Thread.sleep(500);
		signIn.enterApprovalFileSignInPwd(recipientPwd);
		Thread.sleep(500);
		signIn.clickapprovalSignInBtn();
		Thread.sleep(3000);
		
		Assert.assertEquals(recipientPage.commentAddedText(), "1 COMMENT");
		Thread.sleep(1000);

		myAccount.closeHelp(); 
		Thread.sleep(500);
		myAccount.openSideBarMenu();
		Thread.sleep(2000);
		myAccount.clickSignOut();
		log.info("RecipientComment - Passed"); 	
	}

	@AfterMethod
	public void afterClass(ITestResult testResult) throws IOException {
		utilities.FullScreenCapture.takeScreenShotOnFailure(testResult, driver);
		driver.close();
	}

}
