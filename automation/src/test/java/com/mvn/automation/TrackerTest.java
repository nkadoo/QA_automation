package com.mvn.automation;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pageClasses.HomePageFactory;
import pageClasses.MyAccountPageFactory;
import pageClasses.SendPageFactory;
import pageClasses.SigninPageFactory;
import pageClasses.TrackerPageFactory;
import utilities.Constants;
import utilities.ExcelUtility;
import utilities.TestUtility;

public class TrackerTest {
	private WebDriver driver;
	SigninPageFactory signIn;
	HomePageFactory homePage;
	SendPageFactory sendPage;
	TrackerPageFactory trackerPage;
	MyAccountPageFactory myAccount;

	static Logger log = Logger.getLogger(TrackerTest.class);

	@Parameters({ "browserType", "env" })
	@BeforeMethod
	public void setUp(String browser, String envir) throws Exception {
		PropertyConfigurator.configure("log4j.properties");

		driver = TestUtility.setupDriver(browser, envir);
		Assert.assertNotNull("Driver can not be null", driver);

		signIn = new SigninPageFactory(driver);
		homePage = new HomePageFactory(driver);
		sendPage = new SendPageFactory(driver);
		trackerPage = new TrackerPageFactory(driver);
		myAccount = new MyAccountPageFactory(driver);
	}
	
	@Test(groups = { "positive", "Smoke" })
	public void trackSent() throws Exception {
		log.info("Running trackSent test case");

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
		
		String recipientEmail = TestUtility.getShortRandomEmail();
		sendPage.enterRecipientEmail(recipientEmail);
		sendPage.enterSubject("Tracker Test");
		sendPage.enterMessage("Generic message");

		sendPage.uploadFileToSend();
		sendPage.clickNextBtn();
		sendPage.clickSendBtn();
		Thread.sleep(5000);

		// Verify files were sent successfully
		Assert.assertTrue(sendPage.isConfirmationPresent());
		
		homePage.clickTrackInHeader();

		Assert.assertEquals(recipientEmail, trackerPage.lastSendRecipient());

		myAccount.openSideBarMenu();
		Thread.sleep(1000);
		myAccount.clickSignOut();
		log.info("trackSent - Passed"); 
	}
	
	@AfterMethod
	public void afterClass(ITestResult testResult) throws IOException {
		utilities.FullScreenCapture.takeScreenShotOnFailure(testResult, driver);
		driver.close();
	}
}
