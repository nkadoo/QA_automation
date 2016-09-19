package com.mvn.automation;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pageClasses.HomePageFactory;
import pageClasses.MyAccountPageFactory;
import pageClasses.SigninPageFactory;
import pageClasses.UplinkPageFactory;
import utilities.Constants;
import utilities.ExcelUtility;
import utilities.TestUtility;

public class UplinkTest {
	private WebDriver driver;
	SigninPageFactory signIn;
	HomePageFactory homePage;
	UplinkPageFactory uplink;
	MyAccountPageFactory myAccount;

	static Logger log = Logger.getLogger(UplinkTest.class);
	
	@Parameters({"browserType", "env"})
	@BeforeMethod
	public void setUp(String browser, String envir) throws Exception {
		PropertyConfigurator.configure("log4j.properties");
		
		driver = TestUtility.setupDriver(browser, envir);
		Assert.assertNotNull("Driver can not be null", driver);
		
		signIn = new SigninPageFactory(driver);
		homePage = new HomePageFactory(driver);
		uplink = new UplinkPageFactory(driver);
		myAccount = new MyAccountPageFactory(driver);
	}
	
	@Parameters("env")
	@Test(groups = "positive")
	public void changeUplinkUrl(String envir) throws Exception{
		log.info("Running change Uplink URL test case");
		
		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String userEmail = ExcelUtility.getCellData(12, 1);
		String userPassword = ExcelUtility.getCellData(12, 2);
		
		signIn.clickSigninLink();
		signIn.userEmail(userEmail);
		Thread.sleep(500);
		signIn.userPassword(userPassword);
		signIn.clickSignInWithEmail();
		Thread.sleep(3000);
		
		homePage.clickUplinkInHeader();
		Thread.sleep(2000);
		
		String currentUplinkUrl = uplink.getCurrentUplink();
		uplink.changeUplinkUrl(TestUtility.getDate());
		Thread.sleep(500);
		
		Assert.assertEquals("This link is available!", uplink.getUplinkUrlStatus());
		
		uplink.clickChangeUplink();
		uplink.confirmUrlChange();
		Thread.sleep(2000);
		
		String newUplinkUrl = uplink.getCurrentUplink();
		Assert.assertFalse(currentUplinkUrl == newUplinkUrl);

		switch (envir.toLowerCase()) {
		case "prod" :
			ExcelUtility.setCellData(newUplinkUrl, 12, 7);
			break;
		case "stage" :
			ExcelUtility.setCellData(newUplinkUrl, 12, 6);
			break;
		default:
			Assert.fail("Error retrieving Uplink URL");
			break;
		}
			
		log.info("New Uplink added to ExcelData");
		
		myAccount.openSideBarMenu();
		Thread.sleep(1000);
		myAccount.clickSignOut();
		
		log.info("Old: " + currentUplinkUrl + ", New: " + newUplinkUrl);
		log.info("changeUplinkUrl - Passed");
	}
	
	@Parameters("env")
	@Test(groups = "positive")
	public void reusePreviousUplinkUrl(String envir) throws Exception{
		log.info("Running reuse previous Uplink URL test case");
		
		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String userEmail = ExcelUtility.getCellData(12, 1);
		String userPassword = ExcelUtility.getCellData(12, 2);
		
		signIn.clickSigninLink();
		signIn.userEmail(userEmail);
		Thread.sleep(500);
		signIn.userPassword(userPassword);
		signIn.clickSignInWithEmail();
		Thread.sleep(3000);
		
		homePage.clickUplinkInHeader();
		Thread.sleep(2000);
		
		String previousUplinkUrl = uplink.getCurrentUplink();
		String uplinkExtension = previousUplinkUrl.substring(previousUplinkUrl.length()-14);

		uplink.changeUplinkUrl(TestUtility.getDate());
		Thread.sleep(500);
		
		Assert.assertEquals("This link is available!", uplink.getUplinkUrlStatus());
		
		uplink.clickChangeUplink();
		uplink.confirmUrlChange();
		Thread.sleep(2000);
		
		uplink.changeUplinkUrl(uplinkExtension);
		uplink.clickChangeUplink();
		uplink.confirmUrlChange();
		Thread.sleep(2000);
		
		String newUplinkUrl = uplink.getCurrentUplink();

		switch (envir.toLowerCase()) {
		case "prod" :
			Assert.assertEquals(newUplinkUrl, ExcelUtility.getCellData(12, 7));
			break;
		case "stage" :
			Assert.assertEquals(newUplinkUrl, ExcelUtility.getCellData(12, 6));
			break;
		default:
			Assert.fail("Error retrieving Uplink URL");
			break;
		}
		
		myAccount.openSideBarMenu();
		Thread.sleep(1000);
		myAccount.clickSignOut();

		log.info("reusePreviousUplinkUrl - Passed");
	}
	
	@Test(groups = "negative")
	public void changeUplinkTooShort() throws Exception{
		log.info("Running change Uplink URL-short test case");
		
		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String userEmail = ExcelUtility.getCellData(12, 1);
		String userPassword = ExcelUtility.getCellData(12, 2);
		
		signIn.clickSigninLink();
		signIn.userEmail(userEmail);
		Thread.sleep(500);
		signIn.userPassword(userPassword);
		signIn.clickSignInWithEmail();
		Thread.sleep(3000);
		
		homePage.clickUplinkInHeader();
		Thread.sleep(2000);
		
		uplink.changeUplinkUrl("-");
		
		Assert.assertEquals("This link too short.", uplink.getUplinkUrlStatus());
		
		myAccount.openSideBarMenu();
		Thread.sleep(1000);
		myAccount.clickSignOut();
		log.info("changeUplinkTooShort - Passed");
	}
	
	@Test(groups = "negative")
	public void changeUplinkAlreadyInUse() throws Exception{
		log.info("Running change Uplink URL-already in use test case");
		
		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String userEmail = ExcelUtility.getCellData(12, 1);
		String userPassword = ExcelUtility.getCellData(12, 2);
		
		signIn.clickSigninLink();
		signIn.userEmail(userEmail);
		Thread.sleep(500);
		signIn.userPassword(userPassword);
		signIn.clickSignInWithEmail();
		Thread.sleep(3000);
		
		homePage.clickUplinkInHeader();
		Thread.sleep(2000);
		
		uplink.changeUplinkUrl("enterprise-stage");
		
		Assert.assertEquals("This link is already in use.", uplink.getUplinkUrlStatus());
		
		myAccount.openSideBarMenu();
		Thread.sleep(1000);
		myAccount.clickSignOut();
		log.info("changeUplinkAlreadyInUse - Passed");
	}

	@Parameters("env")
	@Test(groups = "positive")
	public void sendToProUplinkAsGuest(String envir) throws Exception {
		log.info("Running send to pro Uplink test case");
		
		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String senderEmail = TestUtility.getRandomEmail();
		String uplinkUrl = null;
		
		switch (envir.toLowerCase()) {
		case "prod":
			uplinkUrl = ExcelUtility.getCellData(12, 7);
			break;
		case "stage":
			uplinkUrl = ExcelUtility.getCellData(12, 6);
			break;
		default:
			Assert.fail("Error retrieving Uplink URL");
			break;
		}
		
		driver.get(uplinkUrl);
		
		uplink.uploadFirstFileUplink();
		Thread.sleep(1000);
		
		uplink.uplinkSenderName("Guest Sender");
		Thread.sleep(500);
		uplink.uplinkSenderEmail(senderEmail);
		uplink.uplinkMessage("Send to Uplink test");
		
		uplink.uploadSecondFileUplink();
		
		uplink.removeFirstFileUplink();
		
		uplink.sendToUplink();
		Thread.sleep(5000);
		
		Assert.assertEquals("Your files have been delivered to", uplink.getUplinkDeliveryText());
		log.info("sendToProUplink - Passed");
	}
	
	@Parameters("env")
	@Test(groups = "positive")
	public void sendToEntUplinkAsGuest(String envir) throws Exception {
		log.info("Running send to pro Uplink test case");
		
		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String senderEmail = TestUtility.getRandomEmail();
		String uplinkUrl = null;
		
		switch (envir.toLowerCase()) {
		case "prod":
			uplinkUrl = ExcelUtility.getCellData(7, 7);
			break;
		case "stage":
			uplinkUrl = ExcelUtility.getCellData(7, 6);
			break;
		default:
			Assert.fail("Error retrieving Uplink URL");
			break;
		}
		
		driver.get(uplinkUrl);
		
		uplink.uploadFirstFileUplink();
		Thread.sleep(1000);
		
		uplink.uplinkSenderName("Guest Sender");
		Thread.sleep(500);
		uplink.uplinkSenderEmail(senderEmail);
		uplink.uplinkMessage("Send to ENT Uplink test");
		
		uplink.openRecipientsWindow();
		uplink.selectAllRecipients();
		uplink.saveSelectedRecipients();
		Thread.sleep(500);
		
		uplink.sendToUplink();
		Thread.sleep(5000);
		
		Assert.assertEquals("Your files have been delivered to", uplink.getUplinkDeliveryText());
		log.info("sendToEntUplink - Passed");
	}
	
	@Parameters("env")
	@Test(groups = "negative")
	public void uplinkInvalidEmail(String envir) throws Exception {
		log.info("Running uplink invalid email test case");
		
		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String uplinkUrl = null;
		
		switch (envir.toLowerCase()) {
		case "prod":
			uplinkUrl = ExcelUtility.getCellData(12, 7);
			break;
		case "stage":
			uplinkUrl = ExcelUtility.getCellData(12, 6);
			break;
		default:
			Assert.fail("Error retrieving Uplink URL");
			break;
		}
		
		driver.get(uplinkUrl);
		
		uplink.uploadFirstFileUplink();
		Thread.sleep(1000);
		
		uplink.uplinkSenderName("Guest Sender");
		Thread.sleep(500);
		uplink.uplinkSenderEmail("invalid@email");
		uplink.uplinkMessage("Invalid email test");
		
		uplink.sendToUplink();
		
		Assert.assertTrue(uplink.emailError());
		
		log.info("uplinkInvalidEmail - Passed");
	}

	@Parameters("env")
	@Test(groups = "negative")
	public void uplinkInvalidName(String envir) throws Exception {
		log.info("Running uplink invalid name test case");
		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String senderEmail = TestUtility.getRandomEmail();
		String uplinkUrl = null;
		
		switch (envir.toLowerCase()) {
		case "prod":
			uplinkUrl = ExcelUtility.getCellData(12, 7);
			break;
		case "stage":
			uplinkUrl = ExcelUtility.getCellData(12, 6);
			break;
		default:
			Assert.fail("Error retrieving Uplink URL");
			break;
		}
		
		driver.get(uplinkUrl);
		
		uplink.uploadFirstFileUplink();
		Thread.sleep(1000);
		
		uplink.uplinkSenderName("test1234");
		Thread.sleep(500);
		uplink.uplinkSenderEmail(senderEmail);
		uplink.uplinkMessage("Invalid name test");
		
		uplink.sendToUplink();
		
		Assert.assertTrue(uplink.nameError());
		
		log.info("uplinkInvalidName - Passed");
	}
	
	@AfterMethod
	public void afterClass(ITestResult testResult) throws IOException {
		utilities.FullScreenCapture.takeScreenShotOnFailure(testResult, driver);
		driver.close();
	}
}
