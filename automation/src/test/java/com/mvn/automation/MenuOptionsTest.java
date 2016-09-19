package com.mvn.automation;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pageClasses.CreateSpacePageFactory;
import pageClasses.MenuOptionsPagefactory;
import pageClasses.SigninPageFactory;
import pageClasses.SignupPageFactory;
import utilities.Constants;
import utilities.ExcelUtility;
import utilities.TestUtility;

public class MenuOptionsTest {
	private WebDriver driver;
	SignupPageFactory signUp;
	CreateSpacePageFactory spaceCreate;
	SigninPageFactory signIn;
	MenuOptionsPagefactory menu;
	Actions action;
	static Logger log = Logger.getLogger(CreateSpacePageFactory.class);
	String email = TestUtility.getRandomEmail();

	@Parameters({"browserType", "env"})
	@BeforeMethod
	public void setUp(String browser, String envir) throws Exception {
		PropertyConfigurator.configure("log4j.properties");

		driver = TestUtility.setupDriver(browser, envir);
		Assert.assertNotNull("Driver can not be null", driver);
		
		signUp = new SignupPageFactory(driver);
		spaceCreate = new CreateSpacePageFactory(driver);
		signIn = new SigninPageFactory(driver);
		menu = new MenuOptionsPagefactory(driver);
		action = new Actions(driver);
	}

	@Test(groups = { "positive", "Smoke" })
	public void downloadFiles() throws Exception {
		log.info("Running downloadFiles from space testcase");

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String userEmail = ExcelUtility.getCellData(2, 1);
		String userPassword = ExcelUtility.getCellData(2, 2);

		signIn.clickSigninLink();
		signIn.userEmail(userEmail);
		Thread.sleep(500);
		signIn.userPassword(userPassword);
		signIn.clickSignInWithEmail();
		Thread.sleep(3000);

		// Upload file to New space
		spaceCreate.createNewSpace();
		spaceCreate.spaceName();
		spaceCreate.uploadFiletoSpace();
		Thread.sleep(3000);
		menu.clickSpaceMenu();
		menu.clickDownloadFiles();
		Thread.sleep(3000);
		log.info("downloadFiles - Passed"); 
	}

	@Test(groups = { "positive", "Smoke" })
	public void addAccessCode() throws Exception {
		log.info("Running addAccessCode to space testcase");

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		// String fullName = ExcelUtility.getCellData(1, 0);
		String userEmail = ExcelUtility.getCellData(2, 1);
		String userPassword = ExcelUtility.getCellData(2, 2);

		signIn.clickSigninLink();
		signIn.userEmail(userEmail);
		Thread.sleep(500);
		signIn.userPassword(userPassword);
		signIn.clickSignInWithEmail();
		Thread.sleep(3000);

		// Upload file to New space
		spaceCreate.createNewSpace();
		spaceCreate.spaceName();
		spaceCreate.uploadFiletoSpace();
		Thread.sleep(3000);
		menu.clickSpaceMenu();
		menu.clickAccessCode();
		menu.addAccessCode("111111");
		menu.SaveAccessCode();
		Thread.sleep(3000);
		log.info("addAccessCode - Passed"); 
	}

	@AfterMethod
	public void afterClass(ITestResult testResult) throws IOException {
		utilities.FullScreenCapture.takeScreenShotOnFailure(testResult, driver);
		driver.close();
	}
}
