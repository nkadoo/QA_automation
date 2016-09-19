package com.mvn.automation;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pageClasses.SigninPageFactory;
import pageClasses.MyAccountPageFactory;
import utilities.Constants;
import utilities.ExcelUtility;
import utilities.TestUtility;

public class SignOutTest {
	
	private WebDriver driver;
	SigninPageFactory signIn;
	MyAccountPageFactory myAccount;
	static Logger log = Logger.getLogger(SignOutTest.class);
	
	@Parameters({"browserType", "env"})
	@BeforeMethod
	public void setUp(String browser, String envir) throws Exception {
		PropertyConfigurator.configure("log4j.properties");

		driver = TestUtility.setupDriver(browser, envir);
		Assert.assertNotNull("Driver can not be null", driver);
		
		myAccount = new MyAccountPageFactory(driver);
		signIn = new SigninPageFactory(driver);
	}

	@Test(groups = "positive")
	public void signOutUser() throws Exception{
		log.info("Running signOutUser Test case");
		
		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String userEmail = ExcelUtility.getCellData(4, 1);
		String userPassword = ExcelUtility.getCellData(4, 2);
		
		signIn.clickSigninLink();
		signIn.userEmail(userEmail);
		Thread.sleep(500);
		signIn.userPassword(userPassword);
		signIn.clickSignInWithEmail();
		Thread.sleep(3000);

		myAccount.openSideBarMenu();	
		Thread.sleep(1000);
		
		myAccount.clickSignOut();
		Thread.sleep(1000);
		log.info("signOutUser - Passed"); 
	}

	@AfterMethod
	public void afterClass(ITestResult testResult) throws IOException {
		utilities.FullScreenCapture.takeScreenShotOnFailure(testResult, driver);
		driver.close();
	}
}
