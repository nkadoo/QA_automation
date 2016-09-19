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
import pageClasses.MyAccountPageFactory;
import pageClasses.AdminPageFactory;
import utilities.Constants;
import utilities.ExcelUtility;
import utilities.TestUtility;

public class AdminResetPwdTest {
	private WebDriver driver;
	SigninPageFactory signIn;
	MyAccountPageFactory myAccount;
	AdminPageFactory admin;
	static Logger log = Logger.getLogger(AdminResetPwdTest.class);

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
	public void AdminResetPwd() throws Exception {
		log.info("Admin SignIn link, forgot password Test case");
		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");

		String userEmail = ExcelUtility.getCellData(7, 1);
		
		driver.get(Constants.ADMIN_URL);	

		admin.enterAdminEmail(userEmail);
		admin.clickResetPwd();
		Thread.sleep(1000);
		Assert.assertEquals(admin.VerifyResetPopupH1(), "Forgot your password?");
		Assert.assertEquals(admin.VerifyResetPopupH2(), "Enter your email associated with your Hightail Spaces account");		
		admin.clickNextBtn();
		Thread.sleep(1000);
		Assert.assertEquals(admin.VerifyConfResetPwdH1(), "Check your inbox");
		Assert.assertEquals(admin.VerifyConfResetPwdH2(), "Click the button in your email to update your password");
		Thread.sleep(1000);
		log.info("AdminResetPwd - Passed"); 
		//***TO DO********CHECK RESET EMAIL AND RESET IT********** 
	}
	
	@Test(groups = { "positive", "Smoke" })
	public void AdminResetPwdResend() throws Exception {
		log.info("Admin SignIn link, Resend reset password Test case");
		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");

		String userEmail = ExcelUtility.getCellData(7, 1);
		
		driver.get(Constants.ADMIN_URL);	

		admin.enterAdminEmail(userEmail);
		admin.clickResetPwd();
		Thread.sleep(1000);
		Assert.assertEquals(admin.VerifyResetPopupH1(), "Forgot your password?");
		Assert.assertEquals(admin.VerifyResetPopupH2(), "Enter your email associated with your Hightail Spaces account");		
		admin.clickNextBtn();
		Thread.sleep(1000);
		Assert.assertEquals(admin.VerifyConfResetPwdH1(), "Check your inbox");
		Assert.assertEquals(admin.VerifyConfResetPwdH2(), "Click the button in your email to update your password");
		admin.clickResendBtn();
		Thread.sleep(1000);
		log.info("AdminResetPwdResend - Passed"); 
		//***TO DO********CHECK RESET EMAIL AND RESET IT********** 	
	}
	
	@AfterMethod
	public void afterClass(ITestResult testResult) throws IOException {
		utilities.FullScreenCapture.takeScreenShotOnFailure(testResult, driver);
		driver.close();
	}
}
