package com.mvn.automation;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pageClasses.CreateSpacePageFactory;
import pageClasses.MyAccountPageFactory;
import pageClasses.SigninPageFactory;
import pageClasses.SignupPageFactory;
import utilities.Constants;
import utilities.ExcelUtility;
import utilities.TestUtility;

public class GuestCommentingTest {
	private WebDriver driver;
	SigninPageFactory signIn;
	CreateSpacePageFactory spaceCreate;
	MyAccountPageFactory myAccount;
	static Logger log = Logger.getLogger(GuestCommentingTest.class);
	Actions action;

	@Parameters({ "browserType", "env" })
	@BeforeMethod
	public void setUp(String browser, String envir) throws Exception {
		PropertyConfigurator.configure("log4j.properties");

		driver = TestUtility.setupDriver(browser, envir);
		Assert.assertNotNull("Driver can not be null");
		signIn = new SigninPageFactory(driver);
		spaceCreate = new CreateSpacePageFactory(driver);
		myAccount = new MyAccountPageFactory(driver);
		action = new Actions(driver);
	}

	@Test(groups = { "positive", "Smoke" })
	public void guestComment() throws Exception {
		log.info("Running guestComment testcase");

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String userEmail = ExcelUtility.getCellData(7, 1);
		String userPassword = ExcelUtility.getCellData(7, 2);

		signIn.clickSigninLink();
		signIn.userEmail(userEmail);
		Thread.sleep(500);
		signIn.userPassword(userPassword);
		signIn.clickSignInWithEmail();
		Thread.sleep(3000);

		spaceCreate.createNewSpace();
		spaceCreate.spaceName();
		spaceCreate.uploadFiletoSpace();
		Thread.sleep(2000);

		String spaceUrl = driver.getCurrentUrl();
		
		myAccount.openSideBarMenu();
		Thread.sleep(1000);
		myAccount.clickSignOut();
		Thread.sleep(3000);

		driver.get(spaceUrl);
		Thread.sleep(4000);
		
		spaceCreate.clickFirstFile();
		Thread.sleep(1000);
		spaceCreate.clickOnImage();
		
		spaceCreate.enterGuestName();
		spaceCreate.enterGuestEmail();
		spaceCreate.enterComment();
		spaceCreate.clickPostComment();
		
		Assert.assertEquals(spaceCreate.isGuestSignUpPresent(), true);
		
		log.info("guestComment - Passed");
	}

	@AfterMethod
	public void afterClass(ITestResult testResult) throws IOException {
		utilities.FullScreenCapture.takeScreenShotOnFailure(testResult, driver);
		driver.close();

	}
}
