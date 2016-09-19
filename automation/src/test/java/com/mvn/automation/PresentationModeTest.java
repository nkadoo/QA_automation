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
import pageClasses.PresentationModePageFactory;
import pageClasses.SigninPageFactory;
import utilities.Constants;
import utilities.ExcelUtility;
import utilities.TestUtility;

public class PresentationModeTest {
	private WebDriver driver;
	SigninPageFactory signIn;
	CreateSpacePageFactory spaceCreate;
	PresentationModePageFactory presentationMode;
	MyAccountPageFactory myAccount;
	static Logger log = Logger.getLogger(PresentationModeTest.class);
	Actions action;

	@Parameters({ "browserType", "env" })
	@BeforeMethod
	public void setUp(String browser, String envir) throws Exception {
		PropertyConfigurator.configure("log4j.properties");

		driver = TestUtility.setupDriver(browser, envir);
		Assert.assertNotNull("Driver can not be null");
		signIn = new SigninPageFactory(driver);
		spaceCreate = new CreateSpacePageFactory(driver);
		presentationMode = new PresentationModePageFactory(driver);
		myAccount = new MyAccountPageFactory(driver);
		action = new Actions(driver);
	}
	
	@Test(groups = {"positive", "Smoke"})
	public void mixedModePresentation() throws Exception {
		log.info("Running mixed mode presentation testcase");
		
		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String userEmail = ExcelUtility.getCellData(21, 1);
		String userPassword = ExcelUtility.getCellData(21, 2); 
		
		signIn.clickSigninLink();
		signIn.userEmail(userEmail);
		Thread.sleep(500);
		signIn.userPassword(userPassword);
		signIn.clickSignInWithEmail();
		Thread.sleep(3000);
		
		spaceCreate.clickFirstSpace();
		int numberOfFiles = spaceCreate.numberOfFiles();
		
		spaceCreate.clickSpaceMenu();
		spaceCreate.clickPresentThisSpace();
		Thread.sleep(1000);
		
		presentationMode.clickMixedMode();
		Thread.sleep(1000);
		presentationMode.scrollThroughFiles(numberOfFiles);
		Thread.sleep(1000);
		presentationMode.exitPresentation();
		
		myAccount.openSideBarMenu();
		Thread.sleep(500);
		myAccount.clickSignOut();
		
		log.info("mixedModePresentation - Passed");
	}
	
	
	@AfterMethod
	public void afterClass(ITestResult testResult) throws IOException {
		utilities.FullScreenCapture.takeScreenShotOnFailure(testResult, driver);
		driver.close();
	}
}
