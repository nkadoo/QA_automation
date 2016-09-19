package com.mvn.automation;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pageClasses.HomePageFactory;
import pageClasses.MyAccountPageFactory;
import pageClasses.ShareSpacesFactory;
import pageClasses.SigninPageFactory;
import pageClasses.SendPageFactory;
import pageClasses.SignupPageFactory;
import utilities.Constants;
import utilities.ExcelUtility;
import utilities.TestUtility;

public class QuotaTest {
	private WebDriver driver;
	SignupPageFactory signUp;
	SigninPageFactory signIn;
	HomePageFactory homePage;
	SendPageFactory sendPage;
	ShareSpacesFactory shareSpace;
	MyAccountPageFactory myAccount;
	Actions action;

	static Logger log = Logger.getLogger(QuotaTest.class);

	@Parameters({ "browserType", "env" })
	@BeforeMethod
	public void setUp(String browser, String envir) throws Exception {
		PropertyConfigurator.configure("log4j.properties");

		driver = TestUtility.setupDriver(browser, envir);
		Assert.assertNotNull("Driver can not be null", driver);

		signIn = new SigninPageFactory(driver);
		signUp = new SignupPageFactory(driver);
		homePage = new HomePageFactory(driver);
		sendPage = new SendPageFactory(driver);
		shareSpace = new ShareSpacesFactory(driver);
		myAccount = new MyAccountPageFactory(driver);
		action = new Actions(driver);
	}

	@Test(groups = { "positive", "Smoke" })
	public void unverified_5send() throws Exception {
		log.info("Running 'unverified lite user doing >5 send' test case");

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String userEmail = ExcelUtility.getCellData(15, 1);		
		String userPassword = ExcelUtility.getCellData(15, 2);
		String recptxlsEmail = ExcelUtility.getCellData(17, 1);
		String sendMsg = ExcelUtility.getCellData(15, 0);
		String sendSub = ExcelUtility.getCellData(15, 4);

		signIn.clickSigninLink();
		signIn.userEmail(userEmail);
		Thread.sleep(1000);
		signIn.userPassword(userPassword);
		signIn.clickSignInWithEmail();
		Thread.sleep(3000);

		homePage.clickSendInHeader();
		sendPage.enterRecipientEmail(recptxlsEmail);
		Thread.sleep(1000);
		sendPage.enterSubject(sendMsg);
		sendPage.enterMessage(sendSub);

		sendPage.uploadFileToSend();
		sendPage.clickNextBtn();
//		sendPage.clickSendBtn();
		Thread.sleep(5000);

		// Verify unverified free user seeing quota limit message and not able to send file
		Assert.assertTrue(sendPage.isSendLimitReached());		
		sendPage.closeFreeQuotaMsg();
		Thread.sleep(1000);

		sendPage.clickStartOverBtn();
		Thread.sleep(1000);
		myAccount.openSideBarMenu();
		Thread.sleep(1000);
		myAccount.clickSignOut();
		log.info("unverified_5send - Passed"); 
	}

	@Test(groups = { "positive"})
	public void unverified_5share() throws Exception {
		log.info("Running 'unverified lite user doing >5 Share' test case");

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String userEmail = ExcelUtility.getCellData(15, 1);		
		String userPassword = ExcelUtility.getCellData(15, 2);
		String recptxlsEmail = ExcelUtility.getCellData(17, 1);
		String shareMsg = ExcelUtility.getCellData(15, 0);

		signIn.clickSigninLink();
		signIn.userEmail(userEmail);
		Thread.sleep(1000);
		signIn.userPassword(userPassword);
		signIn.clickSignInWithEmail();
		Thread.sleep(3000);

		WebElement viewSpace = driver.findElement(By.cssSelector("li.fadeIn"));
		action.moveToElement(viewSpace).perform();
		Thread.sleep(2000);
		shareSpace.clickViewSpace();
		Thread.sleep(1000);		
		shareSpace.shareBtnClick();
		Thread.sleep(1000);
		shareSpace.enterRcptEmail(recptxlsEmail);
		shareSpace.enterShareMsg(shareMsg);
		Thread.sleep(1000);

		shareSpace.sendShareBtnClick();
		Thread.sleep(1000);

		// Verify unverified free user seeing quota limit message and not able to share
		Assert.assertTrue(sendPage.isSendLimitReached());		
		shareSpace.closeFreeQuotaMsg();
		Thread.sleep(1000);
		shareSpace.clickCloseShare();

		Thread.sleep(1000);
		myAccount.openSideBarMenu();
		Thread.sleep(1000);
		myAccount.clickSignOut();
		log.info("unverified_5share - Passed"); 
	}

	@Test(groups = { "positive", "Smoke" })
	public void freeUser_dailySendQuota() throws Exception {
		log.info("Running free user daily send quota test case");

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String userEmail = ExcelUtility.getCellData(16, 1);		
		String userPassword = ExcelUtility.getCellData(16, 2);
		String recptxlsEmail = ExcelUtility.getCellData(18, 1);
		String sendMsg = ExcelUtility.getCellData(16, 0);
		String sendSub = ExcelUtility.getCellData(16, 4);

		signIn.clickSigninLink();
		signIn.userEmail(userEmail);
		Thread.sleep(1000);
		signIn.userPassword(userPassword);
		signIn.clickSignInWithEmail();
		Thread.sleep(3000);

		homePage.clickSendInHeader();
		sendPage.enterRecipientEmail(recptxlsEmail);
		Thread.sleep(2000);
		sendPage.enterSubject(sendMsg);
		sendPage.enterMessage(sendSub);

		sendPage.uploadFileToSend();
		sendPage.clickNextBtn();
		sendPage.clickSendBtn();
		Thread.sleep(3000);

		// Verify unverified free user seeing quota limit message and not able to send file
		Assert.assertTrue(sendPage.isdailyQuotaFreeUserReached());		
		sendPage.ClickOkbtnDailyQuota();

		Thread.sleep(1000);
		myAccount.openSideBarMenu();
		Thread.sleep(1000);
		myAccount.clickSignOut();
		log.info("freeUser_dailySendQuota - Passed"); 
	}

	@Test(groups = { "positive"})
	public void freeUser_dailyShareQuota() throws Exception {
		log.info("Running daily share quota test for free user");

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String userEmail = ExcelUtility.getCellData(16, 1);		
		String userPassword = ExcelUtility.getCellData(16, 2);
		String recptxlsEmail = ExcelUtility.getCellData(18, 1);
		String shareMsg = ExcelUtility.getCellData(16, 0);

		signIn.clickSigninLink();
		signIn.userEmail(userEmail);
		Thread.sleep(1000);
		signIn.userPassword(userPassword);
		signIn.clickSignInWithEmail();
		Thread.sleep(3000);

		WebElement viewSpace = driver.findElement(By.cssSelector("li.fadeIn"));
		action.moveToElement(viewSpace).perform();
		Thread.sleep(2000);
		shareSpace.clickViewSpace();
		Thread.sleep(1000);		
		shareSpace.shareBtnClick();
		Thread.sleep(1000);
		shareSpace.enterRcptEmail(recptxlsEmail);
		Thread.sleep(2000);
		shareSpace.enterShareMsg(shareMsg);
		Thread.sleep(1000);

		shareSpace.sendShareBtnClick();
		Thread.sleep(1000);
		shareSpace.sendShareBtnClick();
		Thread.sleep(1000);
		shareSpace.sendShareBtnClick();
		Thread.sleep(1000);
		shareSpace.sendShareBtnClick();

		// Verify unverified free user seeing quota limit message and not able to share
		Thread.sleep(1000);
		Assert.assertTrue(shareSpace.isShareQuotaReached());		
		shareSpace.clickCloseShare();

		Thread.sleep(1000);
		myAccount.openSideBarMenu();
		Thread.sleep(1000);
		myAccount.clickSignOut();
		log.info("freeUser_dailyShareQuota - Passed"); 
	}
		
	@Test(groups = { "positive" })
	public void paidUser_dailySendQuota() throws Exception {
		log.info("Running free user daily send quota test case");

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String userEmail = ExcelUtility.getCellData(12, 1);		
		String userPassword = ExcelUtility.getCellData(12, 2);
		String recptxlsEmail = ExcelUtility.getCellData(19, 1);
		String sendMsg = ExcelUtility.getCellData(12, 0);
		String sendSub = ExcelUtility.getCellData(12, 4);

		signIn.clickSigninLink();
		signIn.userEmail(userEmail);
		Thread.sleep(500);
		signIn.userPassword(userPassword);
		signIn.clickSignInWithEmail();
		Thread.sleep(3000);

		homePage.clickSendInHeader();
		sendPage.enterRecipientEmail(recptxlsEmail);
		Thread.sleep(4000);
		sendPage.enterSubject(sendMsg);
		sendPage.enterMessage(sendSub);

		sendPage.uploadFileToSend();
		sendPage.clickNextBtn();
		sendPage.clickSendBtn();
		Thread.sleep(3000);

		// Verify unverified free user seeing quota limit message and not able to send file
		Assert.assertTrue(sendPage.isdailyQuotaFreeUserReached());		
		sendPage.ClickOkbtnDailyQuota();

		Thread.sleep(1000);
		myAccount.openSideBarMenu();
		Thread.sleep(1000);
		myAccount.clickSignOut();
		log.info("paidUser_dailySendQuota - Passed"); 
	}

	@Test(groups = { "positive", "Smoke"})
	public void paidUser_dailyShareQuota() throws Exception {
		log.info("Running paidUser_dailyShareQuota");

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String userEmail = ExcelUtility.getCellData(12, 1);		
		String userPassword = ExcelUtility.getCellData(12, 2);
		String recptxlsEmail = ExcelUtility.getCellData(19, 1);
		String shareMsg = ExcelUtility.getCellData(12, 0);

		signIn.clickSigninLink();
		signIn.userEmail(userEmail);
		Thread.sleep(1000);
		signIn.userPassword(userPassword);
		signIn.clickSignInWithEmail();
		Thread.sleep(3000);

		WebElement viewSpace = driver.findElement(By.cssSelector("li.fadeIn"));
		action.moveToElement(viewSpace).perform();
		Thread.sleep(2000);
		shareSpace.clickViewSpace();
		Thread.sleep(1000);		
		shareSpace.shareBtnClick();
		Thread.sleep(1000);
		shareSpace.enterRcptEmail(recptxlsEmail);
		Thread.sleep(4000);
		shareSpace.enterShareMsg(shareMsg);
		Thread.sleep(1000);

		shareSpace.sendShareBtnClick();
		Thread.sleep(1000);
		shareSpace.sendShareBtnClick();
		Thread.sleep(1000);
		shareSpace.sendShareBtnClick();
		Thread.sleep(1000);
		shareSpace.sendShareBtnClick();

		// Verify unverified free user seeing quota limit message and not able to share
		Thread.sleep(1000);
		Assert.assertTrue(shareSpace.isShareQuotaReached());		
		shareSpace.clickCloseShare();

		Thread.sleep(1000);
		myAccount.openSideBarMenu();
		Thread.sleep(1000);
		myAccount.clickSignOut();
		log.info("paidUser_dailyShareQuota - Passed"); 
	}	
	
	@AfterMethod
	public void afterClass(ITestResult testResult) throws IOException {
		utilities.FullScreenCapture.takeScreenShotOnFailure(testResult, driver);
		driver.close();
	}
}
