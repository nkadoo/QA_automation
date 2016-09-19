package com.mvn.automation;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.Alert;
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

public class SamlTest {
	private WebDriver driver;
	SigninPageFactory signIn;
	CreateSpacePageFactory spaceCreate;
	MyAccountPageFactory myAccount;
	static Logger log = Logger.getLogger(SamlTest.class);
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
	
	@Parameters({ "browserType", "env"})
	@Test(groups = {"positive", "Smoke"})
	public void samlLogIn(String browserType1, String envir1) throws Exception {
		log.info("Running SAML log in test");
		
		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String userEmail = ExcelUtility.getCellData(22, 1);
		String userPassword = ExcelUtility.getCellData(22, 2);
		
		signIn.clickSigninLink();
		signIn.corporateSignIn();
		Thread.sleep(1000);
		signIn.enterCorporateEmail(userEmail);
		signIn.clickNextButton();
		Thread.sleep(2000);
		
		signIn.enterOktaUsername(userEmail);
		Thread.sleep(500);
		signIn.enterOktaPassword(userPassword);
		signIn.clickOktaSignInBtn();
		Thread.sleep(2000);
		switch (browserType1.toLowerCase()) {
		case "firefox":
			Alert alert = driver.switchTo().alert();
			alert.accept();
			break;
		default:
			break;
		}
		
		Thread.sleep(5000);
		
		myAccount.openSideBarMenu();
		Thread.sleep(1000);
		myAccount.clickSignOut();
		Thread.sleep(1000);
		
		log.info("samlLogIn - Passed");
	}
	
	@AfterMethod
	public void afterClass(ITestResult testResult) throws IOException {
		utilities.FullScreenCapture.takeScreenShotOnFailure(testResult, driver);
		driver.close();
	}
}
