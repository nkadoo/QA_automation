package com.mvn.automation;

import java.io.IOException;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pageClasses.CreateSpacePageFactory;
import pageClasses.SigninPageFactory;
import pageClasses.MyAccountPageFactory;
import utilities.Constants;
import utilities.ExcelUtility;
import utilities.TestUtility;

public class OAuth_signInTest {

	private WebDriver driver;
	SigninPageFactory oAuth_signIn;
	CreateSpacePageFactory spaceCreate;
	MyAccountPageFactory myAccount;
	static Logger log = Logger.getLogger(OAuth_signInTest.class);

	@Parameters({"browserType", "env"})
	@BeforeMethod
	public void setUp(String browser, String envir) throws Exception {
		PropertyConfigurator.configure("log4j.properties");

		driver = TestUtility.setupDriver(browser, envir);
		Assert.assertNotNull("Driver can not be null");
		
		oAuth_signIn = new SigninPageFactory(driver);
		spaceCreate = new CreateSpacePageFactory(driver);
		myAccount = new MyAccountPageFactory(driver);
	}

	@Test(groups = { "negative" })
	public void oAuth_noPwdSignIn() throws Exception {
		log.info("Running oAuth_noPwdSignIn Testcase");

		// CLicking Sign-up for Free button
		oAuth_signIn.clickSigninLink();
		oAuth_signIn.clickoAuthHtl();

		// Get the handle
		String parentHandle = driver.getWindowHandle();
		// Get all handles
		Set<String> handles = driver.getWindowHandles();

		for (String handle : handles) {
			if (!handle.equals(parentHandle)) {
				// Switch to the new window
				driver.switchTo().window(handle);

				Thread.sleep(1000);
				ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
				String oAuthHtlEmail = ExcelUtility.getCellData(3, 1);
				String password = "";

				Thread.sleep(2000);
				oAuth_signIn.EnteroAuthHtlEmail(oAuthHtlEmail);
				oAuth_signIn.EnteroAuthHtlpwd(password);
				oAuth_signIn.clickoAuthHtlsubmit();
				Thread.sleep(2000);

				// Check the error is shown
				Assert.assertEquals(oAuth_signIn.oAuthSigninErrorExist(), true);
				Thread.sleep(2000);

				// Close oAuth window
				driver.close();
				Thread.sleep(2000);

				// Switch back to parent window
				driver.switchTo().window(parentHandle);
				Thread.sleep(3000);
			}
		}
		driver.switchTo().window(parentHandle);
		Thread.sleep(2000);
		log.info("oAuth_noPwdSignIn - Passed"); 
	}

	@Test(groups = { "negative" })
	public void oAuth_noEmailSignIn() throws Exception {
		log.info("Running oAuth_noEmailSignIn Testcase");

		// Clicking Sign-up for Free button
		oAuth_signIn.clickSigninLink();
		oAuth_signIn.clickoAuthHtl();

		// Get the handle
		String parentHandle = driver.getWindowHandle();
		// Get all handles
		Set<String> handles = driver.getWindowHandles();

		for (String handle : handles) {
			if (!handle.equals(parentHandle)) {
				// Switch to the new window
				driver.switchTo().window(handle);

				Thread.sleep(1000);
				ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
				String email = "";
				String oAuthHtlpwd = ExcelUtility.getCellData(3, 2);
				Thread.sleep(2000);

				// Fill credentials on oAuth page
				oAuth_signIn.EnteroAuthHtlEmail(email);
				oAuth_signIn.EnteroAuthHtlpwd(oAuthHtlpwd);
				oAuth_signIn.clickoAuthHtlsubmit();
				Thread.sleep(2000);

				// Check the error is shown
				Assert.assertEquals(oAuth_signIn.oAuthNoemailErrorExist(), true);
				Thread.sleep(2000);

				// Close oAuth window
				driver.close();
				Thread.sleep(2000);

				// Switch back to parent window
				driver.switchTo().window(parentHandle);
				Thread.sleep(3000);
			}
		}
		driver.switchTo().window(parentHandle);
		Thread.sleep(2000);
		log.info("oAuth_noEmailSignIn - Passed"); 
	}

	@Test(groups = { "negative" })
	public void oAuth_incorrectPwdSignIn() throws Exception {
		log.info("Running oAuth_incorrectPwdSignIn Testcase");

		// CLicking Sign-up for Free button
		oAuth_signIn.clickSigninLink();
		oAuth_signIn.clickoAuthHtl();

		// Get the handle
		String parentHandle = driver.getWindowHandle();
		// Get all handles
		Set<String> handles = driver.getWindowHandles();

		for (String handle : handles) {
			if (!handle.equals(parentHandle)) {
				// Switch to the new window
				driver.switchTo().window(handle);

				Thread.sleep(1000);
				ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
				String oAuthHtlEmail = ExcelUtility.getCellData(3, 1);
				String password = "222222";
				Thread.sleep(2000);

				// Fill credentials on oAuth page
				oAuth_signIn.EnteroAuthHtlEmail(oAuthHtlEmail);
				oAuth_signIn.EnteroAuthHtlpwd(password);
				oAuth_signIn.clickoAuthHtlsubmit();
				Thread.sleep(2000);

				// Check the error is shown
				Assert.assertEquals(oAuth_signIn.oAuthSigninErrorExist(), true);
				Thread.sleep(2000);

				// Close oAuth window
				driver.close();
				Thread.sleep(2000);

				// Switch back to parent window
				driver.switchTo().window(parentHandle);
				Thread.sleep(3000);
			}
		}
		driver.switchTo().window(parentHandle);
		Thread.sleep(2000);
		log.info("oAuth_incorrectPwdSignIn - Passed"); 
	}

	@Test(groups = { "negative" })
	public void oAuth_incorrectEmailSignIn() throws Exception {
		log.info("Running oAuth_incorrectEmailSignIn Testcase");

		// Clicking Sign-up for Free button
		oAuth_signIn.clickSigninLink();
		oAuth_signIn.clickoAuthHtl();

		// Get the handle
		String parentHandle = driver.getWindowHandle();
		// Get all handles
		Set<String> handles = driver.getWindowHandles();

		for (String handle : handles) {
			if (!handle.equals(parentHandle)) {
				// Switch to the new window
				driver.switchTo().window(handle);

				Thread.sleep(1000);
				ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
				String email = "ras.1yopmail.com";
				String oAuthHtlpwd = ExcelUtility.getCellData(3, 2);
				Thread.sleep(2000);

				// Fill credentials on oAuth page
				oAuth_signIn.EnteroAuthHtlEmail(email);
				oAuth_signIn.EnteroAuthHtlpwd(oAuthHtlpwd);
				oAuth_signIn.clickoAuthHtlsubmit();
				Thread.sleep(2000);

				// Check the error is shown
				Assert.assertEquals(oAuth_signIn.oAuthNoemailErrorExist(), true);
				Thread.sleep(2000);

				// Close oAuth window
				driver.close();
				Thread.sleep(2000);

				// Switch back to parent window
				driver.switchTo().window(parentHandle);
				Thread.sleep(3000);
			}
		}
		driver.switchTo().window(parentHandle);
		Thread.sleep(2000);
		log.info("oAuth_incorrectEmailSignIn - Passed"); 
	}

	@Test(groups = { "negative" })
	public void oAuth_nonExistingEmailSignIn() throws Exception {
		log.info("Running oAuth_nonExistingEmailSignIn Testcase");

		// Clicking Sign-up for Free button
		oAuth_signIn.clickSigninLink();
		oAuth_signIn.clickoAuthHtl();

		// Get the handle
		String parentHandle = driver.getWindowHandle();
		// Get all handles
		Set<String> handles = driver.getWindowHandles();

		for (String handle : handles) {
			if (!handle.equals(parentHandle)) {
				// Switch to the new window
				driver.switchTo().window(handle);

				Thread.sleep(1000);
				ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
				String email = "ras.100001@yopmail.com";
				String oAuthHtlpwd = ExcelUtility.getCellData(3, 2);
				Thread.sleep(2000);

				// Fill credentials on oAuth page
				oAuth_signIn.EnteroAuthHtlEmail(email);
				oAuth_signIn.EnteroAuthHtlpwd(oAuthHtlpwd);
				oAuth_signIn.clickoAuthHtlsubmit();
				Thread.sleep(2000);

				// Check the error is shown
				Assert.assertEquals(oAuth_signIn.oAuthNoemailErrorExist(), true);
				Thread.sleep(2000);

				// Close oAuth window
				driver.close();
				Thread.sleep(2000);

				// Switch back to parent window
				driver.switchTo().window(parentHandle);
				Thread.sleep(3000);
			}
		}
		driver.switchTo().window(parentHandle);
		Thread.sleep(2000);
		log.info("oAuth_nonExistingEmailSignIn - Passed"); 
	}

	@Test(groups = { "positive", "Smoke" })
	public void oAuth_validSignIn() throws Exception {
		log.info("Running oAuth_validSignIn Testcase");

		// CLicking Signup for Free button
		oAuth_signIn.clickSigninLink();
		oAuth_signIn.clickoAuthHtl();

		// Get the handle
		String parentHandle = driver.getWindowHandle();
		// Get all handles
		Set<String> handles = driver.getWindowHandles();

		for (String handle : handles) {
			if (!handle.equals(parentHandle)) {
				// Switch to the new window
				driver.switchTo().window(handle);

				Thread.sleep(1000);
				ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
				String oAuthHtlEmail = ExcelUtility.getCellData(3, 1);
				String oAuthHtlpwd = ExcelUtility.getCellData(3, 2);

				Thread.sleep(5000);
				oAuth_signIn.clickoAuthHtlEmail();
				oAuth_signIn.EnteroAuthHtlEmail(oAuthHtlEmail);
				oAuth_signIn.EnteroAuthHtlpwd(oAuthHtlpwd);

				Thread.sleep(1000);
				oAuth_signIn.clickoAuthHtlsubmit();
				Thread.sleep(1000);
				oAuth_signIn.clickoAuthHtlAccept();

				// Switch back to parent window
				driver.switchTo().window(parentHandle);
				Thread.sleep(5000);

				// Signing out of the account
				myAccount.openSideBarMenu();
				Thread.sleep(1000);
				myAccount.clickSignOut();
				Thread.sleep(1000);
			}
		}
		log.info("oAuth_validSignIn - Passed"); 
	}
	
	@AfterMethod
	public void afterClass(ITestResult testResult) throws IOException {
		utilities.FullScreenCapture.takeScreenShotOnFailure(testResult, driver);
		driver.close();
	}
}
