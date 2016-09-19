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

import pageClasses.SigninPageFactory;
import pageClasses.SignupPageFactory;
import utilities.Constants;
import utilities.ExcelUtility;
import utilities.TestUtility;

public class SignInTest {
	private WebDriver driver;
	SignupPageFactory signUp;
	SigninPageFactory signIn;
	static Logger log = Logger.getLogger(SignUpTest.class);

	Actions action;

	@Parameters({"browserType", "env"})
	@BeforeMethod
	public void setUp(String browser, String envir) throws Exception {
		PropertyConfigurator.configure("log4j.properties");

		driver = TestUtility.setupDriver(browser, envir);
		Assert.assertNotNull("Driver can not be null", driver);
		
		signUp = new SignupPageFactory(driver);
		action = new Actions(driver);
		signIn = new SigninPageFactory(driver);
	}
		
	@Test(groups = "positive")
	public void signInUser() throws Exception {
		log.info("Running signInUser Test case");

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String userEmail = ExcelUtility.getCellData(2, 1);		
		String userPassword = ExcelUtility.getCellData(2, 2);

		signIn.clickSigninLink();
		signIn.userEmail(userEmail);
		Thread.sleep(500);
		signIn.userPassword(userPassword);
		signIn.clickSignInWithEmail();
		Thread.sleep(3000);
		log.info("signInUser - Passed"); 
	}

	@AfterMethod
		public void afterClass(ITestResult testResult) throws IOException {
			utilities.FullScreenCapture.takeScreenShotOnFailure(testResult, driver);
			driver.close();
		}
}
