package com.mvn.automation;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
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
import utilities.Constants;
import utilities.ExcelUtility;
import utilities.TestUtility;

public class CollectionsTest {
	private WebDriver driver;
	SigninPageFactory signIn;
	CreateSpacePageFactory spaceCreate;
	MyAccountPageFactory myAccount;
	static Logger log = Logger.getLogger(CollectionsTest.class);
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
	
	@Test(groups = {"positive", "Smoke"})
	public void newCollection() throws Exception {
		log.info("Running create new collection test");
		
		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String userEmail = ExcelUtility.getCellData(2, 1);
		String userPassword = ExcelUtility.getCellData(2, 2);
		
		signIn.clickSigninLink();
		signIn.userEmail(userEmail);
		Thread.sleep(500);
		signIn.userPassword(userPassword);
		signIn.clickSignInWithEmail();
		Thread.sleep(3000);
		
		spaceCreate.clickNewCollection();
		spaceCreate.clickThreeSpaces();
		spaceCreate.clickNextButton();
		
		// Generate random collection name
		String collectionName = TestUtility.getDate();
		
		spaceCreate.enterCollectionName(collectionName);
		spaceCreate.clickCreateCollectionBtn();
		Thread.sleep(1000);
		
		Assert.assertEquals(spaceCreate.getMessageText(), "Created collection '" + collectionName + "'");
		Thread.sleep(3000);
		
		myAccount.openSideBarMenu();
		Thread.sleep(500);
		myAccount.clickSignOut();
		
		log.info("newCollection - Passed");
	}
	
	@AfterMethod
	public void afterClass(ITestResult testResult) throws IOException {
		utilities.FullScreenCapture.takeScreenShotOnFailure(testResult, driver);
		driver.close();
	}
}
