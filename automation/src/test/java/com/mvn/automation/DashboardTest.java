package com.mvn.automation;

import java.io.IOException;
import java.util.Set;

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

import pageClasses.CreateSpacePageFactory;
import pageClasses.SigninPageFactory;
import pageClasses.MyAccountPageFactory;
import pageClasses.ShareSpacesFactory;
import pageClasses.DashboardPageFactory;
import utilities.Constants;
import utilities.ExcelUtility;
import utilities.TestUtility;

public class DashboardTest {
	private WebDriver driver;
	SigninPageFactory signIn;
	MyAccountPageFactory myAccount;
	CreateSpacePageFactory spaceCreate;
	ShareSpacesFactory shareSpace;
	DashboardPageFactory dashboard;

	static Logger log = Logger.getLogger(DashboardTest.class);

	@Parameters({ "browserType", "env" })
	@BeforeMethod
	public void setUp(String browser, String envir) throws Exception {
		PropertyConfigurator.configure("log4j.properties");

		driver = TestUtility.setupDriver(browser, envir);
		Assert.assertNotNull("Driver can not be null", driver);

		myAccount = new MyAccountPageFactory(driver);
		signIn = new SigninPageFactory(driver);
		spaceCreate = new CreateSpacePageFactory(driver);
		shareSpace = new ShareSpacesFactory(driver);
		dashboard = new DashboardPageFactory(driver);
	}

	@Test(groups = { "positive", "Smoke" })
	public void dashboardCreation() throws Exception {
		log.info("dashboardCreation testcase");

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String userEmail = ExcelUtility.getCellData(5, 1);
		String userPassword = ExcelUtility.getCellData(5, 2);

		signIn.clickSigninLink();
		signIn.userEmail(userEmail);
		Thread.sleep(500);
		signIn.userPassword(userPassword);
		signIn.clickSignInWithEmail();
		Thread.sleep(3000);

		// Upload file to New space
		spaceCreate.createNewSpace();
		Thread.sleep(2000);
		spaceCreate.spaceName();
		spaceCreate.uploadFiletoSpace();
		Thread.sleep(2000);

		// Refresh page to get dashboard icon
		driver.navigate().refresh();
		Thread.sleep(5000);
		
		spaceCreate.clickSpaceMenu();

		// Click on dashboard icon
		spaceCreate.openDashboard();
		Thread.sleep(2000);

		// Get the handle
		String parentHandle = driver.getWindowHandle();
		// Get all handles
		Set<String> handles = driver.getWindowHandles();

		for (String handle : handles) {
			if (!handle.equals(parentHandle)) {

				// Switch to the new window
				driver.switchTo().window(handle);
				Thread.sleep(3000);

				// Verify if dashboard appeared
				Assert.assertEquals(dashboard.VerifySpaceHealth(),
						"Space Health");

				// Switch back to parent window
				driver.close();
				driver.switchTo().window(parentHandle);
				Thread.sleep(3000);
			}
		}

		// Logout from account
		// Open side bar menu
		myAccount.openSideBarMenu();
		Thread.sleep(2000);
		myAccount.clickSignOut();
		Thread.sleep(1000);
		log.info("dashboardCreation - Passed"); 
	}

	@Test(groups = { "positive", "Smoke" })
	public void dashboardHealthCheck() throws Exception {
		log.info("Running dashboardHealthCheck testcase");

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String userEmail = ExcelUtility.getCellData(5, 1);
		String userPassword = ExcelUtility.getCellData(5, 2);

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
		Thread.sleep(2000);

		// Refresh page to get dashboard icon
		driver.navigate().refresh();
		Thread.sleep(5000);
		
		spaceCreate.clickSpaceMenu();

		// Click on dashboard link
		spaceCreate.openDashboard();
		Thread.sleep(2000);

		// Get the handle
		String parentHandle = driver.getWindowHandle();
		// Get all handles
		Set<String> handles = driver.getWindowHandles();

		for (String handle : handles) {
			if (!handle.equals(parentHandle)) {

				// Switch to the new window
				driver.switchTo().window(handle);
				Thread.sleep(3000);

				// Verify dash-board has positive space health
				Assert.assertTrue(dashboard.getSpaceHealthScoreNo() != null);

				// Switch back to parent window
				driver.close();
				driver.switchTo().window(parentHandle);
				Thread.sleep(3000);
			}
		}

		myAccount.openSideBarMenu();
		Thread.sleep(2000);
		myAccount.clickSignOut();
		Thread.sleep(1000);
		log.info("dashboardHealthCheck - Passed"); 
	}

	@AfterMethod
	public void afterClass(ITestResult testResult) throws IOException {
		utilities.FullScreenCapture.takeScreenShotOnFailure(testResult, driver);
		driver.close();
	}
}
