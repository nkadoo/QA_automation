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

import pageClasses.SigninPageFactory;
import pageClasses.MyAccountPageFactory;
import utilities.Constants;
import utilities.ExcelUtility;
import utilities.TestUtility;

public class PasswordUpdateTest {
	
	private WebDriver driver;
	SigninPageFactory signIn;
	MyAccountPageFactory myAccount;
	//Log4j logger init
	static Logger log = Logger.getLogger(SignInTest.class);
	
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
	public void passwordUpdateSpacesUser() throws Exception{
		log.info("Running passwordUpdateSpacesUser Test case");
		
		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");

		String userEmail = ExcelUtility.getCellData(11, 1);
		String userPassword = ExcelUtility.getCellData(11, 2);
		String newPassword = ExcelUtility.getCellData(11, 5);
		
		signIn.clickSigninLink();
		signIn.userEmail(userEmail);
		Thread.sleep(500);
		signIn.userPassword(userPassword);
		signIn.clickSignInWithEmail();
		Thread.sleep(3000);

		//Changing password for the user
		myAccount.openSideBarMenu();
		Thread.sleep(1000);
		
		myAccount.clickPassChange();
		Thread.sleep(500);
		myAccount.userCurrentPassword(userPassword);
		Thread.sleep(500);
		myAccount.userNewPassword(newPassword);
		Thread.sleep(500);
		myAccount.clickUpdatePasswordButton();
		Thread.sleep(2000);
		Assert.assertEquals(myAccount.pwdUpdatedText(), "password updated!");
		Thread.sleep(2000);		
		myAccount.clickSignOut();
		Thread.sleep(3000);

		signIn.clickSigninLink();
		signIn.userEmail(userEmail);
		Thread.sleep(500);
		signIn.userPassword(newPassword);
		signIn.clickSignInWithEmail();
		
		//Set password back to original password
		myAccount.openSideBarMenu();
		Thread.sleep(1000);
		
		myAccount.clickPassChange();
		myAccount.userCurrentPassword(newPassword);
		myAccount.userNewPassword(userPassword);
		myAccount.clickUpdatePasswordButton();
		Thread.sleep(2000);

		//Verify that password is updated
		Assert.assertEquals(myAccount.pwdUpdatedText(), "password updated!");
		Thread.sleep(1000);		

		myAccount.clickSignOut();
		log.info("passwordUpdateSpacesUser - Passed"); 
	}
	
	@Test(groups = "positive")
	public void resetUserPassword() throws Exception{
		log.info("Running resetUserPassword Test case");
		
		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String userEmail = ExcelUtility.getCellData(11, 1);
		String userPassword = ExcelUtility.getCellData(11, 2);
		
		signIn.clickSigninLink();
		signIn.userEmail(userEmail);
		Thread.sleep(500);
		signIn.userPassword(userPassword);
		signIn.clickSignInWithEmail();
		Thread.sleep(3000);

		//Changing password for the user
		myAccount.openSideBarMenu();
		Thread.sleep(1000);
		myAccount.clickPassChange();
		Thread.sleep(1000);
		myAccount.resetPass();
		Thread.sleep(2000);

		//Verify that email is sent to reset password
		Assert.assertEquals(myAccount.checkUrEmailText(), "check your email!");
		Thread.sleep(1000);
		
		myAccount.clickSignOut();
		log.info("resetUserPassword - Passed"); 
	}

	@AfterMethod
	public void afterClass(ITestResult testResult) throws IOException {
		utilities.FullScreenCapture.takeScreenShotOnFailure(testResult, driver);
		driver.close();

	}
}