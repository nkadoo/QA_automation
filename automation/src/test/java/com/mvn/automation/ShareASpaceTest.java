package com.mvn.automation;

import java.io.IOException;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pageClasses.CreateSpacePageFactory;
import pageClasses.MenuOptionsPagefactory;
import pageClasses.SigninPageFactory;
import pageClasses.MyAccountPageFactory;
import pageClasses.ShareSpacesFactory;
import utilities.Constants;
import utilities.ExcelUtility;
import utilities.TestUtility;

public class ShareASpaceTest {

	private WebDriver driver;
	SigninPageFactory signIn;
	MyAccountPageFactory myAccount;
	CreateSpacePageFactory spaceCreate;
	ShareSpacesFactory shareSpace;
	MenuOptionsPagefactory spaceMenu;
	static Logger log = Logger.getLogger(ShareASpaceTest.class);

	String email = TestUtility.getRandomEmail();;

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
		spaceMenu = new MenuOptionsPagefactory(driver);
	}

	@Test(groups = { "positive", "Smoke" })
	public void shareAPublicSpaceCanEdit() throws Exception {
		log.info("Running shareAPublicSpaceCanEdit Test case");

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");

		String userEmail = ExcelUtility.getCellData(5, 1);
		String userPassword = ExcelUtility.getCellData(5, 2);
		String recptxlsEmail = ExcelUtility.getCellData(6, 1);
		String shareMsg = ExcelUtility.getCellData(6, 2);

		// Sign-in
		signIn.clickSigninLink();
		signIn.userEmail(userEmail);
		Thread.sleep(500);
		signIn.userPassword(userPassword);
		signIn.clickSignInWithEmail();
		Thread.sleep(3000);

		// Create a Space
		spaceCreate.createNewSpace();
		spaceCreate.spaceName();

		spaceCreate.uploadFiletoSpace();
		Thread.sleep(2000);

		shareSpace.shareBtnClick();
		Thread.sleep(3000);

		shareSpace.enterRcptEmail(recptxlsEmail);
		shareSpace.enterShareMsg(shareMsg);
		Thread.sleep(1000);

		shareSpace.sendShareBtnClick();
		Thread.sleep(1000);

		// Getting success message occur only for few seconds
		Assert.assertEquals("SHARED", shareSpace.getShareBtnText());
		Thread.sleep(4000);

		// Copy shared space link
		// to-do

		// Sign-out with sharer's account
		myAccount.openSideBarMenu();
		Thread.sleep(1000);
		myAccount.clickSignOut();
		Thread.sleep(1000);

		/*
		 * // Check for share email // ---TO-DO-LATER---
		 * 
		 * // Sign-in with sharee's account signIn.clickSigninLink();
		 * signIn.userEmail(recptxlsEmail); signIn.userPassword(userPassword);
		 * signIn.clickSignInWithEmail(); Thread.sleep(3000);
		 * 
		 * // Verify that sharee sees the shared spaces // --to-do--
		 * 
		 * // Sign-out with sharee's account myAccount.openSideBarMenu();
		 * Thread.sleep(1000); myAccount.clicksignOut(); Thread.sleep(1000);
		 */
		log.info("shareAPublicSpaceCanEdit - Passed"); 
	}

	@Test(groups = "positive")
	public void sharePublicSpaceCommentDwnld() throws Exception {
		log.info("Running sharePublicSpaceCommentDwnld Test case");

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");

		String userEmail = ExcelUtility.getCellData(5, 1);
		String userPassword = ExcelUtility.getCellData(5, 2);
		String recptxlsEmail = ExcelUtility.getCellData(6, 1);

		signIn.clickSigninLink();
		signIn.userEmail(userEmail);
		Thread.sleep(500);
		signIn.userPassword(userPassword);
		signIn.clickSignInWithEmail();
		Thread.sleep(3000);
		spaceCreate.createNewSpace();
		spaceCreate.spaceName();
		spaceCreate.uploadFiletoSpace();
		Thread.sleep(2000);

		spaceMenu.clickSpaceMenu();

		// Click space settings and select COMMENT AND DOWNLOAD
		spaceMenu.clickSpaceSettings();
		spaceMenu.openPermissionsDropDown();
		spaceMenu.clickCommentAndDownload();
		spaceMenu.clickSaveSpaceSettingsBtn();
		Thread.sleep(1000);

		// Verify the space permission has changed
		Assert.assertEquals("Space is still Public. Users can also comment and download.", spaceMenu.getNotificationText());

		// Share above created space
		Thread.sleep(3000);
		shareSpace.shareBtnClick();
		Thread.sleep(3000);

		shareSpace.enterRcptEmail(recptxlsEmail);
		shareSpace.enterShareMsg("Sharing with Comment and Download permission");
		Thread.sleep(1000);

		shareSpace.sendShareBtnClick();
		Thread.sleep(1000);

		// Getting success message occur only for few seconds
		Assert.assertEquals("SHARED", shareSpace.getShareBtnText());
		Thread.sleep(4000);

		// Copy shared space link
		// to-do

		// Sign-out with sharer's account
		myAccount.openSideBarMenu();
		Thread.sleep(1000);
		myAccount.clickSignOut();
		Thread.sleep(1000);

		/*
		 * // Check for share email // ---TO-DO-LATER---
		 * 
		 * // Sign-in with sharee's account signIn.clickSigninLink();
		 * signIn.userEmail(recptxlsEmail); signIn.userPassword(userPassword);
		 * signIn.clickSignInWithEmail(); Thread.sleep(3000);
		 * 
		 * // Verify that sharee sees the shared spaces // --to-do--
		 * 
		 * // Sign-out with sharee's account myAccount.openSideBarMenu();
		 * Thread.sleep(1000); myAccount.clicksignOut(); Thread.sleep(1000);
		 */
		log.info("sharePublicSpaceCommentDwnld - Passed"); 
	}

	@Test(groups = "positive")
	public void sharePublicSpaceCommentOnly() throws Exception {
		log.info("Running sharePublicSpaceCommentOnly testcase");

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");

		String userEmail = ExcelUtility.getCellData(5, 1);
		String userPassword = ExcelUtility.getCellData(5, 2);
		String recptxlsEmail = ExcelUtility.getCellData(6, 1);
		String shareMsg = ExcelUtility.getCellData(16, 2);

		// Sign-in
		signIn.clickSigninLink();
		signIn.userEmail(userEmail);
		Thread.sleep(500);
		signIn.userPassword(userPassword);
		signIn.clickSignInWithEmail();
		Thread.sleep(3000);
		spaceCreate.createNewSpace();
		spaceCreate.spaceName();
		spaceCreate.uploadFiletoSpace();
		Thread.sleep(2000);

		spaceMenu.clickSpaceMenu();

		spaceMenu.clickSpaceSettings();
		spaceMenu.openPermissionsDropDown();
		spaceMenu.clickCommentOnly();
		spaceMenu.clickSaveSpaceSettingsBtn();
		Thread.sleep(1000);

		// Verify the space permission has changed
		Assert.assertEquals(
				"Space is still Public. Users can also comment only.",
				spaceMenu.getNotificationText());

		Thread.sleep(3000);
		shareSpace.shareBtnClick();
		Thread.sleep(3000);

		shareSpace.enterRcptEmail(recptxlsEmail);
		shareSpace.enterShareMsg("Sharing with Comment Only permission");
		Thread.sleep(1000);

		shareSpace.sendShareBtnClick();
		Thread.sleep(1000);

		// Getting success message occur only for few seconds
		Assert.assertEquals("SHARED", shareSpace.getShareBtnText());
		Thread.sleep(4000);

		// Copy shared space link
		// to-do

		myAccount.openSideBarMenu();
		Thread.sleep(1000);
		myAccount.clickSignOut();
		Thread.sleep(1000);

		/*
		 * // Check for share email // ---TO-DO-LATER---
		 * 
		 * // Sign-in with sharee's account signIn.clickSigninLink();
		 * signIn.userEmail(recptxlsEmail); signIn.userPassword(userPassword);
		 * signIn.clickSignInWithEmail(); Thread.sleep(3000);
		 * 
		 * // Verify that sharee sees the shared spaces // --to-do--
		 * 
		 * // Sign-out with sharee's account myAccount.openSideBarMenu();
		 * Thread.sleep(1000); myAccount.clicksignOut(); Thread.sleep(1000);
		 */
		log.info("sharePublicSpaceCommentOnly - Passed"); 
	}

	@Test(groups = { "positive", "Smoke" })
	public void sharePrivateSpaceCanEdit() throws Exception {
		log.info("Running sharePrivateSpaceCanEdit testcase");

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String userEmail = ExcelUtility.getCellData(5, 1);
		String userPassword = ExcelUtility.getCellData(5, 2);
		String recptxlsEmail = ExcelUtility.getCellData(6, 1);

		signIn.clickSigninLink();
		signIn.userEmail(userEmail);
		Thread.sleep(500);
		signIn.userPassword(userPassword);
		signIn.clickSignInWithEmail();
		Thread.sleep(3000);
		spaceCreate.createNewSpace();
		spaceCreate.spaceName();
		spaceCreate.uploadFiletoSpace();
		Thread.sleep(2000);

		spaceMenu.clickSpaceMenu();

		spaceMenu.clickSpaceSettings();
		spaceMenu.selectOnlySpecificPeople();
		spaceMenu.clickSaveSpaceSettingsBtn();
		Thread.sleep(1000);

		// Verify the space permission has changed
		Assert.assertEquals("Space is now Private.",
				spaceMenu.getNotificationText());

		// Share above created space
		Thread.sleep(3000);
		shareSpace.shareBtnClick();
		Thread.sleep(3000);

		shareSpace.enterRcptEmail(recptxlsEmail);
		shareSpace.enterShareMsg("Private share with can edit");
		Thread.sleep(1000);

		shareSpace.sendShareBtnClick();
		Thread.sleep(1000);

		// Getting success message occur only for few seconds
		Assert.assertEquals("SHARED", shareSpace.getShareBtnText());
		Thread.sleep(3000);

		// Verify private share notification
		String headerNotification = "Invited 1 person to " + spaceCreate.getSpaceName();
		Assert.assertEquals(headerNotification, spaceMenu.getNotificationText());

		Thread.sleep(4000);

		// Sign-out with sharer's account
		myAccount.openSideBarMenu();
		Thread.sleep(1000);
		myAccount.clickSignOut();
		Thread.sleep(1000);

		/*
		 * // Check for share email // ---TO-DO-LATER---
		 * 
		 * // Sign-in with sharee's account signIn.clickSigninLink();
		 * signIn.userEmail(recptxlsEmail); signIn.userPassword(userPassword);
		 * signIn.clickSignInWithEmail(); Thread.sleep(3000);
		 * 
		 * // Verify that sharee sees the shared spaces // --to-do--
		 * 
		 * // Sign-out with sharee's account myAccount.openSideBarMenu();
		 * Thread.sleep(1000); myAccount.clicksignOut(); Thread.sleep(1000);
		 */
		log.info("sharePrivateSpaceCanEdit - Passed"); 
	}
	
	@Test(groups = "positive")
	public void sharePrivateSpaceCommentDwnld() throws Exception {
		log.info("Running sharePrivateSpaceCommentDwnld testcase");

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String userEmail = ExcelUtility.getCellData(5, 1);
		String userPassword = ExcelUtility.getCellData(5, 2);
		String recptxlsEmail = ExcelUtility.getCellData(6, 1);

		signIn.clickSigninLink();
		signIn.userEmail(userEmail);
		Thread.sleep(500);
		signIn.userPassword(userPassword);
		signIn.clickSignInWithEmail();
		Thread.sleep(3000);
		spaceCreate.createNewSpace();
		spaceCreate.spaceName();
		spaceCreate.uploadFiletoSpace();
		Thread.sleep(2000);

		spaceMenu.clickSpaceMenu();

		spaceMenu.clickSpaceSettings();
		spaceMenu.selectOnlySpecificPeople();
		spaceMenu.clickSaveSpaceSettingsBtn();
		Thread.sleep(1000);

		// Verify the space permission has changed
		Assert.assertEquals("Space is now Private.",
				spaceMenu.getNotificationText());

		// Share above created space
		Thread.sleep(3000);
		shareSpace.shareBtnClick();
		Thread.sleep(3000);

		shareSpace.enterRcptEmail(recptxlsEmail);
		shareSpace.enterShareMsg("Private share with Can comment and download");
		Thread.sleep(1000);
		
		// Select Can comment and download
		shareSpace.selectCommentAndDownload();
		Thread.sleep(1000);

		shareSpace.sendShareBtnClick();
		Thread.sleep(1000);

		// Getting success message occur only for few seconds
		Assert.assertEquals("SHARED", shareSpace.getShareBtnText());
		Thread.sleep(3000);

		// Verify private share notification
		String headerNotification = "Invited 1 person to " + spaceCreate.getSpaceName();
		Assert.assertEquals(headerNotification, spaceMenu.getNotificationText());

		Thread.sleep(4000);

		// Sign-out with sharer's account
		myAccount.openSideBarMenu();
		Thread.sleep(1000);
		myAccount.clickSignOut();
		Thread.sleep(1000);

		/*
		 * // Check for share email // ---TO-DO-LATER---
		 * 
		 * // Sign-in with sharee's account signIn.clickSigninLink();
		 * signIn.userEmail(recptxlsEmail); signIn.userPassword(userPassword);
		 * signIn.clickSignInWithEmail(); Thread.sleep(3000);
		 * 
		 * // Verify that sharee sees the shared spaces // --to-do--
		 * 
		 * // Sign-out with sharee's account myAccount.openSideBarMenu();
		 * Thread.sleep(1000); myAccount.clicksignOut(); Thread.sleep(1000);
		 */
		log.info("sharePrivateSpaceCommentDwnld - Passed"); 
	}
	
	@Test(groups = "positive")
	public void sharePrivateSpaceCommentOnly() throws Exception {
		log.info("Running sharePrivateSpaceCommentOnly testcase");

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String userEmail = ExcelUtility.getCellData(5, 1);
		String userPassword = ExcelUtility.getCellData(5, 2);
		String recptxlsEmail = ExcelUtility.getCellData(6, 1);

		signIn.clickSigninLink();
		signIn.userEmail(userEmail);
		Thread.sleep(500);
		signIn.userPassword(userPassword);
		signIn.clickSignInWithEmail();
		Thread.sleep(3000);
		spaceCreate.createNewSpace();
		spaceCreate.spaceName();
		spaceCreate.uploadFiletoSpace();
		Thread.sleep(2000);

		spaceMenu.clickSpaceMenu();

		spaceMenu.clickSpaceSettings();
		spaceMenu.selectOnlySpecificPeople();
		spaceMenu.clickSaveSpaceSettingsBtn();
		Thread.sleep(1000);

		// Verify the space permission has changed
		Assert.assertEquals("Space is now Private.",
				spaceMenu.getNotificationText());

		// Share above created space
		Thread.sleep(3000);
		shareSpace.shareBtnClick();
		Thread.sleep(3000);

		shareSpace.enterRcptEmail(recptxlsEmail);
		shareSpace.enterShareMsg("Private share with Can comment only");
		Thread.sleep(1000);
		
		// Select Can comment only
		shareSpace.selectCommentOnly();
		Thread.sleep(1000);

		shareSpace.sendShareBtnClick();
		Thread.sleep(1000);

		// Getting success message occur only for few seconds
		Assert.assertEquals("SHARED", shareSpace.getShareBtnText());
		Thread.sleep(3000);

		// Verify private share notification
		String headerNotification = "Invited 1 person to " + spaceCreate.getSpaceName();
		Assert.assertEquals(headerNotification, spaceMenu.getNotificationText());

		Thread.sleep(4000);

		// Sign-out with sharer's account
		myAccount.openSideBarMenu();
		Thread.sleep(1000);
		myAccount.clickSignOut();
		Thread.sleep(1000);

		/*
		 * // Check for share email // ---TO-DO-LATER---
		 * 
		 * // Sign-in with sharee's account signIn.clickSigninLink();
		 * signIn.userEmail(recptxlsEmail); signIn.userPassword(userPassword);
		 * signIn.clickSignInWithEmail(); Thread.sleep(3000);
		 * 
		 * // Verify that sharee sees the shared spaces // --to-do--
		 * 
		 * // Sign-out with sharee's account myAccount.openSideBarMenu();
		 * Thread.sleep(1000); myAccount.clicksignOut(); Thread.sleep(1000);
		 */
		log.info("sharePrivateSpaceCommentOnly - Passed"); 
	}

	// @Test(groups = "positive") //This is TO do test, we have a prob wiht
	// Slack as it is not allowing us to
	// use the pop-up for automation.
	public void shareAspaceonSlack() throws Exception {
		log.info("Running shareAspaceonSlack Test case");

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String userEmail = ExcelUtility.getCellData(5, 1);
		String userPassword = ExcelUtility.getCellData(5, 2);

		signIn.clickSigninLink();
		signIn.userEmail(userEmail);
		Thread.sleep(500);
		signIn.userPassword(userPassword);
		signIn.clickSignInWithEmail();
		Thread.sleep(3000);
		spaceCreate.createNewSpace();
		spaceCreate.spaceName();
		spaceCreate.uploadFiletoSpace();
		Thread.sleep(2000);

		shareSpace.shareBtnClick();
		shareSpace.clickSlackButton();

		// Get the handle
		String parentHandle = driver.getWindowHandle();
		// Get all handles
		Set<String> handles = driver.getWindowHandles();

		for (String handle : handles) {
			if (!handle.equals(parentHandle)) {
				// Switch to the new window
				driver.switchTo().frame(handle);

				Thread.sleep(5000);
				// Enter Slack Team name
				shareSpace.enterSlackTeamName("HTQA");
			}
		}
		// Getting dynamic element attribute
		// WebElement rcptEmail =
		// driver.findElement(By.xpath("//*[starts-with(@id,'as-input-')]"));
		// rcptEmail.sendKeys(recptxlsEmail);
		// shareSpace.enterShareMsg(shareMsg);
		// shareSpace.sendShareBtnClick();
		// // Getting success message occur only for few seconds
		// WebElement spaceShared = driver.findElement(By.className("shared"));
		// Assert.assertEquals(spaceShared.isDisplayed(), true);
		log.info("shareAspaceonSlack - Passed"); 
	}

	@AfterMethod
	public void afterClass(ITestResult testResult) throws IOException {
		utilities.FullScreenCapture.takeScreenShotOnFailure(testResult, driver);
        driver.close();
	} 
}
