package com.mvn.automation;

import java.io.IOException;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.webdriven.commands.IsAlertPresent;

import pageClasses.HomePageFactory;
import pageClasses.MyAccountPageFactory;
import pageClasses.SigninPageFactory;
import pageClasses.SendPageFactory;
import utilities.Constants;
import utilities.ExcelUtility;
import utilities.TestUtility;

public class SendTest {
	private WebDriver driver;
	SigninPageFactory signIn;
	HomePageFactory homePage;
	SendPageFactory sendPage;
	MyAccountPageFactory myAccount;

	static Logger log = Logger.getLogger(SendTest.class);

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

	@Test(groups = { "positive", "Smoke" })
	public void sendASingleFile() throws Exception {
		log.info("Running sendASingleFile test case");

		signIn.clickSigninLink();

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String newSendEmail = ExcelUtility.getCellData(12, 1);
		String newSendPswd = ExcelUtility.getCellData(12, 2);

		Thread.sleep(2000);
		signIn.userEmail(newSendEmail);
		Thread.sleep(500);
		signIn.userPassword(newSendPswd);
		signIn.clickSignInWithEmail();
		Thread.sleep(3000);

		homePage.clickSendInHeader();
		sendPage.enterRecipientEmail("prd_newsend@dispostable.com");
		sendPage.enterSubject("Test");
		sendPage.enterMessage("Generic message");

		sendPage.uploadFileToSend();
		sendPage.clickNextBtn();
		sendPage.clickSendBtn();
		Thread.sleep(5000);

		// Verify files were sent successfully
		Assert.assertTrue(sendPage.isConfirmationPresent());

		myAccount.openSideBarMenu();
		Thread.sleep(1000);
		myAccount.clickSignOut();
		log.info("sendASingleFile - Passed"); 
	}

	@Test(groups = "positive")
	public void sendMultipleFiles() throws Exception {
		log.info("Running sendMultipleFiles test case");

		signIn.clickSigninLink();

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String newSendEmail = ExcelUtility.getCellData(12, 1);
		String newSendPswd = ExcelUtility.getCellData(12, 2);

		Thread.sleep(2000);
		signIn.userEmail(newSendEmail);
		Thread.sleep(500);
		signIn.userPassword(newSendPswd);
		signIn.clickSignInWithEmail();
		Thread.sleep(3000);

		homePage.clickSendInHeader();
		sendPage.enterRecipientEmail("prd_newsend@dispostable.com");
		sendPage.enterSubject("Test");
		sendPage.enterMessage("Generic message");

		sendPage.uploadFileToSend();
		Thread.sleep(3000);
		sendPage.clickAddMoreFiles();
		sendPage.addMoreFilesFromDesktop();
		Thread.sleep(3000);
		sendPage.clickNextBtn();
		sendPage.clickSendBtn();
		Thread.sleep(5000);

		// Verify files were sent successfully
		Assert.assertTrue(sendPage.isConfirmationPresent());

		myAccount.openSideBarMenu();
		Thread.sleep(2000);
		myAccount.clickSignOut();
		log.info("sendMultipleFiles - Passed"); 
	}

	@Test(groups = "positive")
	public void removeAFile() throws Exception {
		log.info("Running removeAFile test case");

		signIn.clickSigninLink();
		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String newSendEmail = ExcelUtility.getCellData(12, 1);
		String newSendPswd = ExcelUtility.getCellData(12, 2);

		Thread.sleep(2000);
		signIn.userEmail(newSendEmail);
		Thread.sleep(500);
		signIn.userPassword(newSendPswd);
		signIn.clickSignInWithEmail();
		Thread.sleep(3000);

		homePage.clickSendInHeader();
		sendPage.uploadFileToSend();
		Thread.sleep(3000);
		sendPage.clickAddMoreFiles();
		sendPage.addMoreFilesFromDesktop();
		Thread.sleep(3000);
		sendPage.removeFirstFile();
		Thread.sleep(2000);
		Assert.assertEquals(sendPage.getNumberOfFiles(), 1);

		sendPage.clickStartOverBtn();

		myAccount.openSideBarMenu();
		Thread.sleep(2000);
		myAccount.clickSignOut();
		log.info("removeAFile - Passed"); 
	}

	@Test(groups = "negative")
	public void sendToAnInvalidEmail() throws Exception {
		log.info("Running send a single file test case");

		String invalidEmailError = "Email recipients list contains errors";

		signIn.clickSigninLink();

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String newSendEmail = ExcelUtility.getCellData(12, 1);
		String newSendPswd = ExcelUtility.getCellData(12, 2);

		Thread.sleep(2000);
		signIn.userEmail(newSendEmail);
		Thread.sleep(500);
		signIn.userPassword(newSendPswd);
		signIn.clickSignInWithEmail();
		Thread.sleep(3000);

		homePage.clickSendInHeader();
		sendPage.enterRecipientEmail("invalid_email@");

		sendPage.uploadFileToSend();
		sendPage.clickNextBtn();
		Thread.sleep(2000);
		Assert.assertEquals(sendPage.errorMessageText(), invalidEmailError);

		sendPage.clickStartOverBtn();

		myAccount.openSideBarMenu();
		Thread.sleep(2000);
		myAccount.clickSignOut();
		log.info("sendToAnInvalidEmail - Passed"); 
	}

	@Test(groups = "negative")
	public void sendToBlankEmail() throws Exception {
		log.info("Running send a single file test case");

		String blankEmailError = "Please specify at least one email recipient";

		signIn.clickSigninLink();

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String newSendEmail = ExcelUtility.getCellData(12, 1);
		String newSendPswd = ExcelUtility.getCellData(12, 2);

		Thread.sleep(2000);

		signIn.userEmail(newSendEmail);
		Thread.sleep(500);
		signIn.userPassword(newSendPswd);
		signIn.clickSignInWithEmail();
		Thread.sleep(3000);

		homePage.clickSendInHeader();
		sendPage.uploadFileToSend();
		sendPage.clickNextBtn();
		Thread.sleep(2000);
		Assert.assertEquals(sendPage.errorMessageText(), blankEmailError);

		sendPage.clickStartOverBtn();

		myAccount.openSideBarMenu();
		Thread.sleep(2000);
		myAccount.clickSignOut();
		log.info("sendToBlankEmail - Passed"); 
	}

	@Test(groups = "positive")
	public void sendWithAccessCode() throws Exception {
		log.info("Running 'Send with access code' test case");

		signIn.clickSigninLink();

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String newSendEmail = ExcelUtility.getCellData(12, 1);
		String newSendPswd = ExcelUtility.getCellData(12, 2);

		Thread.sleep(2000);
		signIn.userEmail(newSendEmail);
		Thread.sleep(500);
		signIn.userPassword(newSendPswd);
		signIn.clickSignInWithEmail();
		Thread.sleep(3000);

		homePage.clickSendInHeader();
		sendPage.enterRecipientEmail("prd_newsend@dispostable.com");
		sendPage.enterSubject("Access Code Test");
		sendPage.enterMessage("Code is 1234");

		sendPage.uploadFileToSend();
		Thread.sleep(3000);
		sendPage.clickNextBtn();
		sendPage.enterAccessCode("1234");
		sendPage.toggleAccessCode();
		sendPage.clickSendBtn();
		Thread.sleep(5000);

		// Verify files were sent successfully
		Assert.assertTrue(sendPage.isConfirmationPresent());

		myAccount.openSideBarMenu();
		Thread.sleep(2000);
		myAccount.clickSignOut();
		log.info("sendWithAccessCode - Passed"); 
	}

	@Test(groups = "positive")
	public void sendWithExpiration() throws Exception {
		log.info("Running 'Send with expiration date' test case");

		signIn.clickSigninLink();

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String newSendEmail = ExcelUtility.getCellData(12, 1);
		String newSendPswd = ExcelUtility.getCellData(12, 2);

		Thread.sleep(2000);
		signIn.userEmail(newSendEmail);
		Thread.sleep(500);
		signIn.userPassword(newSendPswd);
		signIn.clickSignInWithEmail();
		Thread.sleep(3000);

		homePage.clickSendInHeader();
		sendPage.enterRecipientEmail("prd_newsend@dispostable.com");
		sendPage.enterSubject("Expiration Date Test");
		sendPage.enterMessage("Setting expiration date");

		sendPage.uploadFileToSend();
		Thread.sleep(3000);
		sendPage.clickNextBtn();
		Thread.sleep(3000);
		sendPage.clickExpirationDatePicker();
		sendPage.clickNextMonthButton();
		sendPage.clickFirstDateOfNextMonth();

		sendPage.clickSendBtn();
		Thread.sleep(5000);

		// Verify files were sent successfully
		Assert.assertTrue(sendPage.isConfirmationPresent());

		myAccount.openSideBarMenu();
		Thread.sleep(2000);
		myAccount.clickSignOut();
		log.info("sendWithExpiration - Passed"); 
	}

	@Test(groups = "positive")
	public void sendWithVerifyRecipientIdentity() throws Exception {
		log.info("Running sendWithVerifyRecipientIdentity test case");

		signIn.clickSigninLink();

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String newSendEmail = ExcelUtility.getCellData(12, 1);
		String newSendPswd = ExcelUtility.getCellData(12, 2);

		Thread.sleep(2000);
		signIn.userEmail(newSendEmail);
		Thread.sleep(500);
		signIn.userPassword(newSendPswd);
		signIn.clickSignInWithEmail();
		Thread.sleep(3000);

		homePage.clickSendInHeader();
		sendPage.enterRecipientEmail("prd_newsend@dispostable.com");
		sendPage.enterSubject("Verify Recipient Identity Enabled Test");
		sendPage.enterMessage("VRI is enabled");

		sendPage.uploadFileToSend();
		Thread.sleep(3000);
		sendPage.clickNextBtn();
		Thread.sleep(3000);
		sendPage.toggleVerifyRecipientIdentity();
		sendPage.clickSendBtn();
		Thread.sleep(5000);

		// Verify files were sent successfully
		Assert.assertTrue(sendPage.isConfirmationPresent());

		myAccount.openSideBarMenu();
		Thread.sleep(2000);
		myAccount.clickSignOut();
		log.info("sendWithVerifyRecipientIdentity - Passed"); 
	}

	@Test(groups = "positive")
	public void sendWithDownloadReceipt() throws Exception {
		log.info("Running sendWithDownloadReceipt test case");

		signIn.clickSigninLink();

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String newSendEmail = ExcelUtility.getCellData(12, 1);
		String newSendPswd = ExcelUtility.getCellData(12, 2);

		Thread.sleep(2000);
		signIn.userEmail(newSendEmail);
		Thread.sleep(500);
		signIn.userPassword(newSendPswd);
		signIn.clickSignInWithEmail();
		Thread.sleep(3000);

		homePage.clickSendInHeader();
		sendPage.enterRecipientEmail("prd_newsend@dispostable.com");
		sendPage.enterSubject("Download Receipt Test");
		sendPage.enterMessage("Dowload receipt is requested");

		sendPage.uploadFileToSend();
		Thread.sleep(3000);
		sendPage.clickNextBtn();
		Thread.sleep(3000);
		sendPage.toggleDownloadReceipt();
		sendPage.clickSendBtn();
		Thread.sleep(5000);

		// Verify files were sent successfully
		Assert.assertTrue(sendPage.isConfirmationPresent());

		myAccount.openSideBarMenu();
		Thread.sleep(2000);
		myAccount.clickSignOut();
		log.info("sendWithDownloadReceipt - Passed"); 
	}

	@Test(groups = { "positive", "Smoke" })
	public void sendWithAllOptions() throws Exception {
		log.info("Running sendWithAllOptions test case");

		signIn.clickSigninLink();

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String newSendEmail = ExcelUtility.getCellData(12, 1);
		String newSendPswd = ExcelUtility.getCellData(12, 2);

		Thread.sleep(2000);

		signIn.userEmail(newSendEmail);
		Thread.sleep(500);
		signIn.userPassword(newSendPswd);
		signIn.clickSignInWithEmail();
		Thread.sleep(3000);

		homePage.clickSendInHeader();
		sendPage.enterRecipientEmail("prd_newsend@dispostable.com");
		sendPage.enterSubject("All Options Enabled Test");
		sendPage.enterMessage("Testing a send with all options enabled");

		sendPage.uploadFileToSend();
		sendPage.clickNextBtn();
		Thread.sleep(3000);

		sendPage.enterAccessCode("1234");
		sendPage.toggleAccessCode();
		Thread.sleep(2000);

		// Set expiration day for the first of the following month
		sendPage.clickExpirationDatePicker();
		sendPage.clickNextMonthButton();
		sendPage.clickFirstDateOfNextMonth();
		Thread.sleep(2000);

		// Turn VRI on
		sendPage.toggleVerifyRecipientIdentity();
		Thread.sleep(2000);

		// Turn download receipt on
		sendPage.toggleDownloadReceipt();
		Thread.sleep(2000);

		sendPage.clickSendBtn();
		Thread.sleep(5000);

		// Verify files were sent successfully
		Assert.assertTrue(sendPage.isConfirmationPresent());

		myAccount.openSideBarMenu();
		Thread.sleep(2000);
		myAccount.clickSignOut();
		log.info("sendWithAllOptions - Passed"); 
	}

	@Test(groups = { "positive", "Smoke" })
	public void sendFromAllStorageProviders() throws Exception {
		log.info("Running sendFromAllStorageProviders test case");

		signIn.clickSigninLink();

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String newSendEmail = ExcelUtility.getCellData(12, 1);
		String newSendPswd = ExcelUtility.getCellData(12, 2);

		Thread.sleep(2000);
		signIn.userEmail(newSendEmail);
		Thread.sleep(500);
		signIn.userPassword(newSendPswd);
		signIn.clickSignInWithEmail();
		Thread.sleep(3000);

		homePage.clickSendInHeader();
		sendPage.enterRecipientEmail("prd_newsend@dispostable.com");
		sendPage.enterSubject("From different file services");
		sendPage.enterMessage("Adding files from each storage service");

		sendPage.clickHTIcon();
		sendPage.selectAFile();
		sendPage.clickAddButtonFromServicePicker();
		sendPage.addFileFromService("dropbox");
		Thread.sleep(1000);
		sendPage.addFileFromService("onedrive");
		Thread.sleep(1000);
		sendPage.addFileFromService("googledrive");
		Thread.sleep(1000);

		sendPage.clickNextBtn();
		sendPage.clickSendBtn();
		Thread.sleep(5000);

		// Verify files were sent successfully
		Assert.assertTrue(sendPage.isConfirmationPresent());

		myAccount.openSideBarMenu();
		Thread.sleep(2000);
		myAccount.clickSignOut();
		log.info("sendFromAllStorageProviders - Passed"); 
	}

	@Test(groups = "positive")
	public void liteSingleFileHTOauth() throws Exception {
		log.info("Running liteSingleFileHTOauth test case");

		signIn.clickSigninLink();
		signIn.clickoAuthHtl();

		// Get the handle
		String parentHandle = driver.getWindowHandle();
		// Get all handles
		Set<String> handles = driver.getWindowHandles();

		for (String handle : handles) {
			if (!handle.equals(parentHandle)) {
				// Switch to OAuth window
				driver.switchTo().window(handle);

				Thread.sleep(1000);
				ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
				String liteSendEmail = ExcelUtility.getCellData(13, 1);
				String liteSendPswd = ExcelUtility.getCellData(13, 2);

				Thread.sleep(2000);
				signIn.clickoAuthHtlEmail();
				signIn.EnteroAuthHtlEmail(liteSendEmail);
				signIn.EnteroAuthHtlpwd(liteSendPswd);

				Thread.sleep(1000);
				signIn.clickoAuthHtlsubmit();
				Thread.sleep(1000);
				signIn.clickoAuthHtlAccept();

				// Switch back to parent window
				driver.switchTo().window(parentHandle);
				Thread.sleep(5000);
			}
		}

		// Click the Send link in the header
		homePage.clickSendInHeader();
		sendPage.enterRecipientEmail("prd_newsend@dispostable.com");
		sendPage.enterSubject("Lite Acct Test");
		sendPage.enterMessage("Generic message");

		sendPage.uploadFileToSend();
		sendPage.clickNextBtn();
		sendPage.clickSendBtn();
		Thread.sleep(5000);

		// Verify files were sent successfully
		Assert.assertTrue(sendPage.isConfirmationPresent());

		myAccount.openSideBarMenu();
		Thread.sleep(2000);
		myAccount.clickSignOut();
		log.info("liteSingleFileHTOauth - Passed"); 
	}

	@Test(groups = "positive")
	public void liteAccessCodePaywall() throws Exception {
		log.info("Running liteAccessCodePaywall test case");

		signIn.clickSigninLink();

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String liteSendEmail = ExcelUtility.getCellData(13, 1);
		String liteSendPswd = ExcelUtility.getCellData(13, 2);

		Thread.sleep(2000);
		signIn.userEmail(liteSendEmail);
		Thread.sleep(500);
		signIn.userPassword(liteSendPswd);
		signIn.clickSignInWithEmail();
		Thread.sleep(3000);

		homePage.clickSendInHeader();
		sendPage.enterRecipientEmail("prd_newsend@dispostable.com");
		sendPage.enterSubject("Paywall-Access Code Test");
		sendPage.enterMessage("Lite acct access code paywall");

		sendPage.uploadFileToSend();
		sendPage.clickNextBtn();
		Thread.sleep(1000);
		sendPage.clickAccessCode();
		Thread.sleep(1000);

		// Verify the paywall is reached by checking for the GO BACK button
		Assert.assertEquals(true, sendPage.isGoBackButtonPresent());

		// Close the paywall
		sendPage.clickGoBackButton();
		Thread.sleep(2000);

		// Clear the uploaded file from browser
		sendPage.clickStartOverBtn();

		myAccount.openSideBarMenu();
		Thread.sleep(2000);
		myAccount.clickSignOut();
		log.info("liteAccessCodePaywall - Passed"); 
	}

	@Test(groups = { "positive", "Smoke" })
	public void liteExpirationPaywall() throws Exception {
		log.info("Running liteExpirationPaywall test case");

		signIn.clickSigninLink();

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String liteSendEmail = ExcelUtility.getCellData(13, 1);
		String liteSendPswd = ExcelUtility.getCellData(13, 2);

		Thread.sleep(2000);

		signIn.userEmail(liteSendEmail);
		Thread.sleep(500);
		signIn.userPassword(liteSendPswd);
		signIn.clickSignInWithEmail();
		Thread.sleep(3000);

		// Click the Send link in the header
		homePage.clickSendInHeader();
		sendPage.enterRecipientEmail("prd_newsend@dispostable.com");
		sendPage.enterSubject("Paywall-Expiration Date Test");
		sendPage.enterMessage("Lite acct expiration paywall");

		sendPage.uploadFileToSend();
		Thread.sleep(3000);
		sendPage.clickNextBtn();
		Thread.sleep(3000);
		sendPage.clickExpirationDatePicker();
		Thread.sleep(1000);

		// Verify the paywall is reached by checking for the GO BACK button
		Assert.assertEquals(true, sendPage.isGoBackButtonPresent());

		// Close the paywall
		sendPage.clickGoBackButton();
		Thread.sleep(2000);

		// Clear the uploaded file from browser
		sendPage.clickStartOverBtn();

		myAccount.openSideBarMenu();
		Thread.sleep(2000);
		myAccount.clickSignOut();
		log.info("liteExpirationPaywall - Passed"); 
	}

	@Test(groups = { "positive", "Smoke" })
	public void liteFileSizePaywall() throws Exception {
		log.info("Running liteFileSizePaywall test case");

		signIn.clickSigninLink();

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String liteSendEmail = ExcelUtility.getCellData(13, 1);
		String liteSendPswd = ExcelUtility.getCellData(13, 2);

		Thread.sleep(2000);

		signIn.userEmail(liteSendEmail);
		Thread.sleep(500);
		signIn.userPassword(liteSendPswd);
		signIn.clickSignInWithEmail();
		Thread.sleep(3000);

		homePage.clickSendInHeader();
		sendPage.enterRecipientEmail("prd_newsend@dispostable.com");
		sendPage.enterSubject("Paywall-File Size Test");
		sendPage.enterMessage("Lite acct file size paywall");

		//uploading 300 MB file to send
		sendPage.uploadLargeFileToSend();
		Thread.sleep(2000);

		// Verify the paywall is reached by checking for the GO BACK button
		Assert.assertEquals(true, sendPage.isGoBackButtonPresent());

		// Close the paywall
		sendPage.clickGoBackButton();
		Thread.sleep(2000);

		myAccount.openSideBarMenu();
		Thread.sleep(2000);
		myAccount.clickSignOut();
		log.info("liteFileSizePaywall - Passed"); 
	}
	
	@Test(groups = { "positive", "Smoke" })
	public void sendToOneContact() throws Exception {
		log.info("Running sendToContact test case");

		signIn.clickSigninLink();

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String newSendEmail = ExcelUtility.getCellData(12, 1);
		String newSendPswd = ExcelUtility.getCellData(12, 2);

		Thread.sleep(2000);

		signIn.userEmail(newSendEmail);
		Thread.sleep(500);
		signIn.userPassword(newSendPswd);
		signIn.clickSignInWithEmail();
		Thread.sleep(3000);

		homePage.clickSendInHeader();
		
		sendPage.clickAddressBook();
		sendPage.clickSelectContacts();
		String contactEmail = sendPage.getFirstContactEmail();
		sendPage.clickFirstContact();
		Thread.sleep(1000);
		sendPage.selectContacts();
		
		sendPage.enterSubject("Contacts Test");
		sendPage.enterMessage("Generic message");
		
		sendPage.uploadFileToSend();
		sendPage.clickNextBtn();
		Thread.sleep(2000);
		Assert.assertEquals(contactEmail,sendPage.getFirstEmailEntered());
		sendPage.clickSendBtn();
		Thread.sleep(5000);

	    myAccount.openSideBarMenu();
		Thread.sleep(1000);
		myAccount.clickSignOut();
		
		log.info("sendToOneContact - Passed");
	}

	@AfterMethod
	public void afterClass(ITestResult testResult) throws IOException {
		utilities.FullScreenCapture.takeScreenShotOnFailure(testResult, driver);
		driver.close();
	}

}
