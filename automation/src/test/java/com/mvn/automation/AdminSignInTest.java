package com.mvn.automation;

import java.io.IOException;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pageClasses.SigninPageFactory;
import pageClasses.MyAccountPageFactory;
import pageClasses.AdminPageFactory;
import utilities.Constants;
import utilities.ExcelUtility;
import utilities.TestUtility;

public class AdminSignInTest {
	private WebDriver driver;
	SigninPageFactory signIn;
	MyAccountPageFactory myAccount;
	AdminPageFactory admin;
	static Logger log = Logger.getLogger(AdminSignInTest.class);

	// Use this class for mouse-over actions
	Actions action;

	@Parameters({"browserType", "env"})
	@BeforeMethod
	public void setUp(String browser, String envir) throws Exception {
		PropertyConfigurator.configure("log4j.properties");

		driver = TestUtility.setupDriver(browser, envir);
		Assert.assertNotNull("Driver can not be null", driver);
		
		myAccount = new MyAccountPageFactory(driver);
		signIn = new SigninPageFactory(driver);
		admin = new AdminPageFactory(driver);
	}

	@Test(groups = { "positive", "Smoke" })
	public void AdminSignInUser() throws Exception {
		log.info("Running Admin SignIn and Admin console verification Test case");

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String userEmail = ExcelUtility.getCellData(7, 1);
		String userPassword = ExcelUtility.getCellData(7, 2);

		// SignIn with Admin account
		signIn.clickSigninLink();
		signIn.userEmail(userEmail);
		Thread.sleep(500);
		signIn.userPassword(userPassword);
		signIn.clickSignInWithEmail();
		Thread.sleep(3000);

		// Open side bar menu
		myAccount.openSideBarMenu();

		// Verify Admin Plan		
		Thread.sleep(1000);
		Assert.assertEquals(admin.VerifyAdminPlan(), "BUSINESS PLAN - Hightail QA Inc.");

		// Click on the Admin Console
		admin.clickadminConsoleBtn();
		Thread.sleep(5000);

		// Get the handle
		String parentHandle = driver.getWindowHandle();
		// Get all handles
		Set<String> handles = driver.getWindowHandles();

		for (String handle : handles) {
			if (!handle.equals(parentHandle)) {

				// Switch to the new window
				driver.switchTo().window(handle);
				Thread.sleep(3000);

				// Verify Admin console is open
				Assert.assertEquals(admin.VerifyAdminHeader(), "MEMBERS");

				// Close Admin window
				driver.close();

				// Switch back to parent window
				driver.switchTo().window(parentHandle);
				Thread.sleep(3000);
			}
		}

		// Logout from Admin account
		myAccount.clickSignOut();
		Thread.sleep(1000);
		log.info("AdminSignInUser - Passed"); 
	}

	
	@Test(groups = { "positive", "Smoke" })
	public void AdminLinkSignIn() throws Exception {
		log.info("Running Admin SignIn from /admin-login link Test case");

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String userEmail = ExcelUtility.getCellData(7, 1);
		String userPassword = ExcelUtility.getCellData(7, 2);

		driver.get(Constants.ADMIN_URL);	

		admin.enterAdminEmail(userEmail);
		admin.enterAdminPwd(userPassword);
		admin.clickAdminSigninBtn();
		Thread.sleep(3000);

		// Verify Admin console is open
		Assert.assertEquals(admin.VerifyAdminHeader(), "MEMBERS");
		Thread.sleep(1000);

		// Logout from Admin account
		admin.clickAdminSignout();
		Thread.sleep(3000);
		log.info("AdminLinkSignIn - Passed"); 
		}		

	@Test(groups = { "negative" })
	public void AdminSignInEmpty() throws Exception {
		log.info("Running Admin SignIn from /admin-login link when no password entered, Test case");

		driver.get(Constants.ADMIN_URL);	

		//Three times SignIn button click displays error message
		admin.clickAdminSigninBtn();
		admin.clickAdminSigninBtn();
		admin.clickAdminSigninBtn();
		Thread.sleep(1000);

		// Verify error message 
		Assert.assertEquals(admin.VerifyErrOnSignin(), "Hmmm...there's still a problem. Make sure your email address and password are correct.");
		log.info("AdminSignInEmpty - Passed");
	}
	
	@Test(groups = { "negative" })
	public void AdminSignInNoPwd() throws Exception {
		log.info("Running Admin SignIn from /admin-login link when no password entered, Test case");

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String userEmail = ExcelUtility.getCellData(7, 1);

		driver.get(Constants.ADMIN_URL);	

		admin.enterAdminEmail(userEmail);

		//Three times SignIn button click displays error message
		admin.clickAdminSigninBtn();
		admin.clickAdminSigninBtn();
		admin.clickAdminSigninBtn();
		Thread.sleep(1000);

		// Verify error message 
		Assert.assertEquals(admin.VerifyErrOnSignin(), "Hmmm...there's still a problem. Make sure your email address and password are correct.");
		log.info("AdminSignInNoPwd - Passed");
	}		

	@Test(groups = { "negative" })
	public void AdminSignInNoEmail() throws Exception {
		log.info("Running Admin SignIn from /admin-login link when no Email entered Test case");

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String userPassword = ExcelUtility.getCellData(7, 2);

		driver.get(Constants.ADMIN_URL);	

		admin.enterAdminPwd(userPassword);

		//Three times SignIn button click displays error message
		admin.clickAdminSigninBtn();
		admin.clickAdminSigninBtn();
		admin.clickAdminSigninBtn();
		Thread.sleep(1000);

		// Verify error message  
		Assert.assertEquals(admin.VerifyErrOnSignin(), "Hmmm...there's still a problem. Make sure your email address and password are correct.");
		log.info("AdminSignInNoEmail - Passed");
	}		

	@Test(groups = { "negative" })
	public void AdminSignInInvalidEmail() throws Exception {
		log.info("Running Admin SignIn from /admin-login link with invalid email Testcase");

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String userEmail = "sel_invalid_email";
		String userPassword = ExcelUtility.getCellData(7, 2);

		driver.get(Constants.ADMIN_URL);	

		// SignIn with Admin account
		admin.enterAdminEmail(userEmail);
		admin.enterAdminPwd(userPassword);
		Thread.sleep(1000);

		//Three times SignIn button click displays error message
		admin.clickAdminSigninBtn();
		Thread.sleep(1000);
		admin.clickAdminSigninBtn();
		Thread.sleep(1000);
		admin.clickAdminSigninBtn();
		Thread.sleep(1000);

		// Verify error message 
		Assert.assertEquals(admin.VerifyErrOnSignin(), "Hmmm...there's still a problem. Make sure your email address and password are correct.");
		log.info("AdminSignInInvalidEmail - Passed");
	}		

	@Test(groups = { "negative" })
	public void AdminSignInInvalidPwd() throws Exception {
		log.info("Running Admin SignIn from /admin-login link with invalid password Testcase");

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String userEmail = ExcelUtility.getCellData(7, 1);
		String userPassword = "2222222";
		
		driver.get(Constants.ADMIN_URL);	

		// SignIn with Admin account
		admin.enterAdminEmail(userEmail);
		admin.enterAdminPwd(userPassword);
		Thread.sleep(1000);

		//Three times SignIn button click displays error message
		admin.clickAdminSigninBtn();
		Thread.sleep(500);
		admin.clickAdminSigninBtn();
		Thread.sleep(500);
		admin.clickAdminSigninBtn();
		Thread.sleep(1000);

		// Verify error message 
		Assert.assertEquals(admin.VerifyErrOnSignin(), "Hmmm...there's still a problem. Make sure your email address and password are correct.");
		log.info("AdminSignInInvalidPwd - Passed");
	}		
	
	@AfterMethod
	public void afterClass(ITestResult testResult) throws IOException {
		utilities.FullScreenCapture.takeScreenShotOnFailure(testResult, driver);
		driver.close();

	}
}
