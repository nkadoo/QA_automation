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
import pageClasses.SigninPageFactory;
import pageClasses.SendPageFactory;
import utilities.Constants;
import utilities.ExcelUtility;
import utilities.TestUtility;

public class RecipientDownloadTest {
	private WebDriver driver;
	SigninPageFactory signIn;
	HomePageFactory homePage;
	SendPageFactory sendPage;
	MyAccountPageFactory myAccount;

	static Logger log = Logger.getLogger(RecipientDownloadTest.class);

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
	}

	@Test(groups = { "positive" })
	public void RecipientDownloadSingleFile() throws Exception {
		log.info("Running RecipientDownloadSingleFile test case");

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
		sendPage.enterSubject("Test Single File download");
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
		
		//Download Single preview-able file from header
		sendPage.ClickFileCard();
		Thread.sleep(1000);
		sendPage.clickDownloadFile();
		Thread.sleep(1000);
		
		log.info("RecipientDownloadSingleFile - Passed"); 	
	}

	@Test(groups = { "positive", "Smoke"})
	public void RecipientDownloadMultipleFiles() throws Exception {
		log.info("Running RecipientDownloadMultipleFiles test case");

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
		sendPage.enterSubject("Testing multiple file download");
		sendPage.enterMessage("Generic message");

		sendPage.uploadFileToSend();
		Thread.sleep(3000);
		sendPage.clickAddMoreFiles();
		sendPage.addMoreFilesFromDesktop();
		Thread.sleep(1000);
		sendPage.clickNextBtn();
		sendPage.clickSendBtn();
		Thread.sleep(5000);

		// Verify files were sent successfully
		Assert.assertTrue(sendPage.isConfirmationPresent());
		Thread.sleep(1000);

		//Copy recipient URL
		WebElement recipientUrl = driver.findElement(By.cssSelector("#copy-link-send-form"));
		String receipient_Url = recipientUrl.getAttribute("data-clipboard-text");		

		//Logout from Sender
		myAccount.openSideBarMenu();
		Thread.sleep(1000);
		myAccount.clickSignOut();
		Thread.sleep(3000);	
		
		//Launch recipient URL
		driver.get(receipient_Url);	
		Thread.sleep(5000);	
		
		//Download all files from recipient page
		sendPage.clickDownloadFiles();
		Thread.sleep(3000);				
		log.info("RecipientDownloadMultipleFiles - Passed"); 
	}

	@Test(groups = "positive")
	public void RecipientDownloadWithAccessCode() throws Exception {
		log.info("Running RecipientDownloadWithAccessCode Testcase");

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
		sendPage.enterSubject("Testing recipient file download with Access code");
		sendPage.enterMessage("Generic message");

		sendPage.uploadFileToSend();
		sendPage.clickNextBtn();
		sendPage.enterAccessCode("1234");
		sendPage.toggleAccessCode();
		sendPage.clickSendBtn();
		Thread.sleep(5000);

		// Verify files were sent successfully
		Assert.assertTrue(sendPage.isConfirmationPresent());

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
		
		//Download file by providing access code
		sendPage.enterRecipientAccessCode("1234");
		sendPage.clickViewFilesBtn();
		Thread.sleep(1000);
		sendPage.ClickFileCard();
		Thread.sleep(1000);
		sendPage.clickDownloadFile();
		Thread.sleep(1000);
		log.info("RecipientDownloadWithAccessCode - Passed"); 
	}
	
	@Test(groups = "negative")
	public void RecipientDownloadWithIncorrectAccessCode() throws Exception {
		log.info("Running RecipientDownloadWithIncorrectAccessCode Testcase");

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
		sendPage.enterSubject("Testing recipient file download with incorrect Access code");
		sendPage.enterMessage("Generic message");

		sendPage.uploadFileToSend();
		Thread.sleep(3000);
		sendPage.clickNextBtn();
		sendPage.enterAccessCode("1234");
		sendPage.toggleAccessCode();
		sendPage.clickSendBtn();
		Thread.sleep(5000);

		// Verify files were sent successfully
		Assert.assertTrue(sendPage.isConfirmationPresent());

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
		
		//Provide incorrect access code
		sendPage.enterRecipientAccessCode("1111");
		sendPage.clickViewFilesBtn();
		sendPage.clickViewFilesBtn();
		sendPage.clickViewFilesBtn();
		Thread.sleep(1000);
		Assert.assertEquals(sendPage.IncorrectAccessCodeError(), "The access code you entered is incorrect.");
								
		log.info("RecipientDownloadWithIncorrectAccessCode - Passed"); 
	}

	@Test(groups = "positive")
	public void RequestAccessCode() throws Exception {
		log.info("Running RequestAccessCode Testcase");

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
		sendPage.enterSubject("Testing recipient file download with incorrect Access code");
		sendPage.enterMessage("Generic message");

		sendPage.uploadFileToSend();
		Thread.sleep(3000);
		sendPage.clickNextBtn();
		sendPage.enterAccessCode("1234");
		sendPage.toggleAccessCode();
		sendPage.clickSendBtn();
		Thread.sleep(5000);

		// Verify files were sent successfully
		Assert.assertTrue(sendPage.isConfirmationPresent());

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
		Thread.sleep(2000);	
		
		//Request for Access code
		sendPage.clickNoAccessCode();
		Thread.sleep(2000);	

		//Need to login to request Access code		
		signIn.EnterRequestAccessSignInEmail(recipientEmail);
		Thread.sleep(500);
		signIn.EnterRequestAccessSignInPwd(recipientPwd);
		Thread.sleep(500);
		signIn.clickrequestSignInBtn();
		Thread.sleep(3000);		
		Assert.assertEquals(sendPage.RequestedAccessCodeText(), "Your request has been sent to the person who shared these files. They will contact you directly.");
								
		myAccount.openSideBarMenu();
		Thread.sleep(2000);
		myAccount.clickSignOut();
		log.info("RequestAccessCode - Passed"); 
	}	

	@Test(groups = "positive")
	public void RecipientDownloadWithVRI() throws Exception {
		log.info("Running RecipientDownloadWithVRI testcase");

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
		sendPage.enterSubject("Testing recipient file download with VRI");
		sendPage.enterMessage("Generic message");

		sendPage.uploadFileToSend();
		sendPage.clickNextBtn();
		Thread.sleep(1000);
		sendPage.toggleVerifyRecipientIdentity();
		Thread.sleep(500);
		sendPage.clickSendBtn();
		Thread.sleep(5000);

		// Verify files were sent successfully
		Assert.assertTrue(sendPage.isConfirmationPresent());

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
		
		//Verify your identity and download file		
		signIn.EnterVriEmail(recipientEmail);
		Thread.sleep(500);
		signIn.EnterVriPwd(recipientPwd);
		Thread.sleep(500);
		signIn.clickSignInWithEmail();
		Thread.sleep(2000);
		sendPage.ClickFileCard();
		Thread.sleep(1000);
		sendPage.clickDownloadFile();
		Thread.sleep(1000);
		
		myAccount.closeHelp(); 
		Thread.sleep(500);
		myAccount.openSideBarMenu();
		Thread.sleep(2000);
		myAccount.clickSignOut();
		log.info("RecipientDownloadWithVRI - Passed"); 
	}
	
	@Test(groups = "negative")
	public void RecipientDownloadWithIncorrectVRI() throws Exception {
		log.info("Running RecipientDownloadWithIncorrectVRI testcase");
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
		sendPage.enterSubject("Testing recipient file download with VRI");
		sendPage.enterMessage("Generic message");

		sendPage.uploadFileToSend();
		Thread.sleep(3000);
		sendPage.clickNextBtn();
		Thread.sleep(3000);
		sendPage.toggleVerifyRecipientIdentity();
		sendPage.clickSendBtn();
		Thread.sleep(5000);

		// Verify files were sent successfully
		Assert.assertTrue(sendPage.isConfirmationPresent());

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
		
		//Verify the error message on giving incorrect identity		
		signIn.EnterVriEmail("incorrectVRI@yopmail.com");
		Thread.sleep(500);
		signIn.EnterVriPwd("sdhgfds");
		Thread.sleep(500);
		signIn.clickSignInWithEmail();
		Thread.sleep(500);
		signIn.clickSignInWithEmail();
		Thread.sleep(500);
		signIn.clickSignInWithEmail();
		Thread.sleep(500);
		signIn.clickSignInWithEmail();
		Thread.sleep(500);
		Assert.assertEquals(signIn.vriSigninErrorExist(), "Hmmm...there's still a problem. Make sure your email address and password are correct.");
		Thread.sleep(500);
		log.info("RecipientDownloadWithIncorrectVRI - Passed"); 
	}

	@Test(groups = "positive")
	public void RecipientDownloadWithChangedExpiration() throws Exception {
		log.info("Running RecipientDownloadWithChangedExpiration test case");

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
		sendPage.enterSubject("Testing recipient file download with changed expiration option");
		sendPage.enterMessage("Generic message");

		sendPage.uploadFileToSend();
		sendPage.clickNextBtn();
		sendPage.clickExpirationDatePicker();
		sendPage.clickNextMonthButton();
		sendPage.clickFirstDateOfNextMonth();
		Thread.sleep(2000);
		sendPage.clickSendBtn();
		Thread.sleep(5000);

		// Verify files were sent successfully
		Assert.assertTrue(sendPage.isConfirmationPresent());

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

		//Download file 
		sendPage.ClickFileCard();
		Thread.sleep(1000);
		sendPage.clickDownloadFile();
		Thread.sleep(1000);

		log.info("RecipientDownloadWithChangedExpiration - Passed"); 
	}
			
	@Test(groups = "positive")
	public void RecipientDownloadWithNeverExpiration() throws Exception {
		log.info("Running RecipientDownloadWithNeverExpiration test case");

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
		sendPage.enterSubject("Testing recipient file download with never expiration option");
		sendPage.enterMessage("Generic message");

		sendPage.uploadFileToSend();
		sendPage.clickNextBtn();
//		Assert.assertEquals(sendPage.neverExpLabelText(), "Never Expire");
//		Thread.sleep(500);
		sendPage.clickSendBtn();
		Thread.sleep(3000);

		// Verify files were sent successfully
		Assert.assertTrue(sendPage.isConfirmationPresent());

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

		//Download file 
		sendPage.ClickFileCard();
		Thread.sleep(1000);
		sendPage.clickDownloadFile();
		Thread.sleep(1000);

		log.info("RecipientDownloadWithNeverExpiration - Passed"); 
	}	
	
	@Test(groups = "positive")
	public void RecipientDownloadWithReturnReceipt() throws Exception {
		log.info("Running RecipientDownloadWithReturnReceipt test case");

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
		sendPage.enterSubject("Testing recipient file Download with receipt option");
		sendPage.enterMessage("Generic message");

		sendPage.uploadFileToSend();
		sendPage.clickNextBtn();
		Thread.sleep(500);
		sendPage.toggleDownloadReceipt();
		sendPage.clickSendBtn();
		Thread.sleep(3000);
		
		// Verify files were sent successfully
		Assert.assertTrue(sendPage.isConfirmationPresent());

		//Copy Recipient URL
		WebElement recipientUrl = driver.findElement(By.cssSelector("#copy-link-send-form"));
		String receipient_Url = recipientUrl.getAttribute("data-clipboard-text");		

		//Logout from Sender
		myAccount.openSideBarMenu();
		Thread.sleep(1000);
		myAccount.clickSignOut();
		
		//Launch recipient URL
		driver.get(receipient_Url);	
		Thread.sleep(3000);	

		//Download file 
		sendPage.ClickFileCard();
		Thread.sleep(1000);
		sendPage.clickDownloadFile();
		Thread.sleep(1000);
		
		log.info("RecipientDownloadWithReturnReceipt - Passed"); 
	}
			
	@Test(groups = { "positive", "Smoke" })
	public void RecipientDownloadWithAllOptions() throws Exception {
		log.info("Running RecipientDownloadWithAllOptions test case");

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
		sendPage.enterSubject("Testing recipient file Download with all four options");
		sendPage.enterMessage("Generic message");

		sendPage.uploadFileToSend();
		sendPage.clickNextBtn();
		Thread.sleep(1000);

		//Set Access code
		sendPage.enterAccessCode("1234");
		sendPage.toggleAccessCode();
		Thread.sleep(2000);

		// Set expiration day for the first of the following month
		sendPage.clickExpirationDatePicker();
		sendPage.clickNextMonthButton();
		sendPage.clickFirstDateOfNextMonth();
		Thread.sleep(1000);

		// Turn VRI ON
		sendPage.toggleVerifyRecipientIdentity();
		Thread.sleep(1000);

		// Turn download receipt ON 
		sendPage.toggleDownloadReceipt();
		Thread.sleep(1000);

		sendPage.clickSendBtn();
		Thread.sleep(3000);

		// Verify files were sent successfully
		Assert.assertTrue(sendPage.isConfirmationPresent());

		//Copy Recipient URL
		WebElement recipientUrl = driver.findElement(By.cssSelector("#copy-link-send-form"));
		String receipient_Url = recipientUrl.getAttribute("data-clipboard-text");		

		//Logout from Sender
		myAccount.openSideBarMenu();
		Thread.sleep(1000);
		myAccount.clickSignOut();
		
		//Launch recipient URL
		driver.get(receipient_Url);	
		Thread.sleep(3000);	

		//Download file sent with all 4 options
		//VRI
		signIn.EnterVriEmail(recipientEmail);
		Thread.sleep(500);
		signIn.EnterVriPwd(recipientPwd);
		Thread.sleep(500);
		signIn.clickSignInWithEmail();
		Thread.sleep(2000);		

		//Access Code
		sendPage.enterRecipientAccessCode("1234");
		sendPage.clickViewFilesBtn();
		Thread.sleep(1000);		

		//Download File
		sendPage.clickDownloadFiles();
		Thread.sleep(1000);
		
		myAccount.openSideBarMenu();
		Thread.sleep(2000);
		myAccount.clickSignOut();
		log.info("RecipientDownloadWithAllOptions - Passed"); 
	}
	
	@Test(groups = { "positive" })
	public void RecipientDownloadNonPreviewableFile() throws Exception {
		log.info("Running RecipientDownloadNonPreviewableFile test case");

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
		sendPage.enterSubject("Test non preview-able file download");
		sendPage.enterMessage("Generic message");
		sendPage.uploadNonPrivewableFileToSend();
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
		
		//Download Single preview-able file from header
		sendPage.ClickFileCard();
		Thread.sleep(1000);
		sendPage.clickdownloadBtn();
		Thread.sleep(1000);
		
		log.info("RecipientDownloadNonPreviewableFile - Passed"); 	
	}
	@AfterMethod
	public void afterClass(ITestResult testResult) throws IOException {
		utilities.FullScreenCapture.takeScreenShotOnFailure(testResult, driver);
		driver.close();
	}

}
