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
import pageClasses.ShareSpacesFactory;
import pageClasses.SigninPageFactory;
import pageClasses.FilePickerPageFactory;
import pageClasses.MyAccountPageFactory;
import utilities.Constants;
import utilities.ExcelUtility;
import utilities.TestUtility;

public class OauthConnectionTest {

	private WebDriver driver;
	SigninPageFactory signIn;
	MyAccountPageFactory myAccount;
	CreateSpacePageFactory spaceCreate;
	ShareSpacesFactory shareSpace;
	FilePickerPageFactory filePicker;
	static Logger log = Logger.getLogger(OauthConnectionTest.class);

	@Parameters({ "browserType", "env" })
	@BeforeMethod
	public void setUp(String browser, String envir) throws Exception {
		PropertyConfigurator.configure("log4j.properties");

		driver = TestUtility.setupDriver(browser, envir);
		Assert.assertNotNull("Driver can not be null", driver);

		signIn = new SigninPageFactory(driver);
		spaceCreate = new CreateSpacePageFactory(driver);
		myAccount = new MyAccountPageFactory(driver);
		filePicker = new FilePickerPageFactory(driver);
	}

	@Test(groups = { "positive", "Smoke" })
	public void DropboxOauthConnect() throws Exception {
		log.info("Running DropboxOauthConnect Testcase");

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");

		String userEmail = ExcelUtility.getCellData(5, 1);
		String userPassword = ExcelUtility.getCellData(5, 2);

		// Sign-in with professional user
		signIn.clickSigninLink();
		signIn.userEmail(userEmail);
		Thread.sleep(500);
		signIn.userPassword(userPassword);
		signIn.clickSignInWithEmail();
		Thread.sleep(3000);

		// Create a space via Dropbox oAuth
		spaceCreate.createNewSpace();
		spaceCreate.spaceName();
		spaceCreate.clickDropboxUpload();

		// Get the handle to Dropbox window
		String parentHandle = driver.getWindowHandle();
		// Get all handles
		Set<String> handles = driver.getWindowHandles();

		for (String handle : handles) {
			if (!handle.equals(parentHandle)) {
				// Switch to the new window
				driver.switchTo().window(handle);

				Thread.sleep(1000);
				ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
				String dropboxEmail = ExcelUtility.getCellData(8, 1);
				String dropboxPwd = ExcelUtility.getCellData(8, 2);

				// SignIn in Dropbox oAuth
				signIn.clickDropboxOauthEmail();
				signIn.EnterDropboxEmail(dropboxEmail);
				signIn.EnterDropboxPwd(dropboxPwd);
				Thread.sleep(2000);
				signIn.clickDropboxSignIn();
				Thread.sleep(1000);
				signIn.clickDropboxAllow();

				// Switch back to parent window
				driver.switchTo().window(parentHandle);
				Thread.sleep(3000);
			}
		}

		// Select Dropbox file to upload
		filePicker.SelectFile();
		filePicker.clickAddFilePickerBtn();

		// Signing out of the account
		try {
			if (driver.findElement(By.cssSelector(".default")).isDisplayed()) {
				myAccount.clickDefaultAvatar();
			}
		} catch (Exception e) {
			myAccount.clickAvaterImage();
		}
		Thread.sleep(1000);
		log.info("DropboxOauthConnect - Passed"); 
	}

	@Test(groups = { "positive", "Smoke" })
	public void createSpaceGoogleOauth() throws Exception {
		log.info("Running createSpaceGoogleOauth Testcase");

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");

		String userEmail = ExcelUtility.getCellData(5, 1);
		String userPassword = ExcelUtility.getCellData(5, 2);

		// Sign-in with professional user
		signIn.clickSigninLink();
		signIn.userEmail(userEmail);
		Thread.sleep(500);
		signIn.userPassword(userPassword);
		signIn.clickSignInWithEmail();
		Thread.sleep(3000);

		// Create a space via Google oAuth
		spaceCreate.createNewSpace();
		spaceCreate.spaceName();
		spaceCreate.clickGoogleUpload();

		// Get the handle to Google Oauth window
		String parentHandle = driver.getWindowHandle();
		// Get all handles
		Set<String> handles = driver.getWindowHandles();

		for (String handle : handles) {
			if (!handle.equals(parentHandle)) {
				// Switch to the new window
				driver.switchTo().window(handle);

				Thread.sleep(1000);
				ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
				String googleEmail = ExcelUtility.getCellData(10, 1);
				String googlePwd = ExcelUtility.getCellData(10, 2);

				// SignIn in Google oAuth
				signIn.clickGoogleOauthEmail();
				signIn.EnterGoogleEmail(googleEmail);
				signIn.clickgoogleOauthNextBtn();
				signIn.EnterGooglePwd(googlePwd);
				Thread.sleep(2000);
				signIn.clickGoogleSignIn();
				Thread.sleep(1000);
				signIn.clickGoogleAllow();

				// Switch back to parent window
				driver.switchTo().window(parentHandle);
				Thread.sleep(3000);
			}
		}

		// Select Google Drive file to upload
		filePicker.SelectFile();
		filePicker.clickAddFilePickerBtn();

		// Signing out of the account
		try {
			if (driver.findElement(By.cssSelector(".default")).isDisplayed()) {
				myAccount.clickDefaultAvatar();
			}
		} catch (Exception e) {
			myAccount.clickAvaterImage();
		}
		Thread.sleep(1000);
		log.info("createSpaceGoogleOauth - Passed"); 
	}

	@Test(groups = { "positive", "Smoke" })
	public void createSpaceMicrosoftOauth() throws Exception {
		log.info("Running createSpaceMicrosoftOauth Testcase");

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");

		String userEmail = ExcelUtility.getCellData(5, 1);
		String userPassword = ExcelUtility.getCellData(5, 2);

		// Sign-in with professional user
		signIn.clickSigninLink();
		signIn.userEmail(userEmail);
		Thread.sleep(500);
		signIn.userPassword(userPassword);
		signIn.clickSignInWithEmail();
		Thread.sleep(3000);

		// Create a space via Microsoft oAuth
		spaceCreate.createNewSpace();
		spaceCreate.spaceName();
		spaceCreate.clickOneDriveUpload();

		// Get the handle to Microsoft Oauth window
		String parentHandle = driver.getWindowHandle();
		// Get all handles
		Set<String> handles = driver.getWindowHandles();

		for (String handle : handles) {
			if (!handle.equals(parentHandle)) {
				// Switch to the new window
				driver.switchTo().window(handle);

				Thread.sleep(1000);
				ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
				String microsoftEmail = ExcelUtility.getCellData(9, 1);
				String microsoftPwd = ExcelUtility.getCellData(9, 2);

				// SignIn in Google oAuth
				signIn.clickMicrosoftOauthEmail();
				signIn.EnterMicrosoftEmail(microsoftEmail);
				signIn.EnterMicrosoftPwd(microsoftPwd);
				Thread.sleep(2000);
				signIn.clickMicrosoftSignIn();
				Thread.sleep(1000);

				// Switch back to parent window
				driver.switchTo().window(parentHandle);
				Thread.sleep(3000);
			}
		}
		// Select Microsoft One Drive file to upload
		filePicker.SelectFile();
		filePicker.clickAddFilePickerBtn();

		// Signing out of the account
		try {
			if (driver.findElement(By.cssSelector(".default")).isDisplayed()) {
				myAccount.clickDefaultAvatar();
			}
		} catch (Exception e) {
			myAccount.clickAvaterImage();
		}
		Thread.sleep(1000);
		log.info("createSpaceMicrosoftOauth - Passed"); 
	}

	@Test(groups = { "positive" })
	public void disconnectDropboxOauth() throws Exception {
		log.info("Running disconnectDropboxOauth Testcase");

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");

		String userEmail = ExcelUtility.getCellData(5, 1);
		String userPassword = ExcelUtility.getCellData(5, 2);

		// Sign-in with professional user
		signIn.clickSigninLink();
		signIn.userEmail(userEmail);
		Thread.sleep(500);
		signIn.userPassword(userPassword);
		signIn.clickSignInWithEmail();
		Thread.sleep(3000);

		// Disconnect from Dropbox Oauth in case of image avatar OR default
		// avatar
		try {
			if (myAccount.isDefaultAvatarDisplayed()) {
				myAccount.clickDefaultAvatar();
			}
		} catch (Exception e) {
			myAccount.clickAvaterImage();
		}
		Thread.sleep(1000);

		// Disconnect from dropbox service if already connected.
		if (myAccount.dropboxConnection()) {
			myAccount.clickDropboxDisconnect();
			Thread.sleep(1000);
		}

		myAccount.clickSignOut();
		log.info("disconnectDropboxOauth - Passed"); 
	}

	@Test(groups = { "positive" })
	public void disconnectGoogleOauth() throws Exception {
		log.info("Running disconnectGoogleOauth Testcase");

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");

		String userEmail = ExcelUtility.getCellData(5, 1);
		String userPassword = ExcelUtility.getCellData(5, 2);

		// Sign-in with professional user
		signIn.clickSigninLink();
		signIn.userEmail(userEmail);
		Thread.sleep(500);
		signIn.userPassword(userPassword);
		signIn.clickSignInWithEmail();
		Thread.sleep(3000);

		try {
			if (myAccount.isDefaultAvatarDisplayed()) {
				myAccount.clickDefaultAvatar();
			}
		} catch (Exception e) {
			myAccount.clickAvaterImage();
		}
		Thread.sleep(1000);

		// Disconnect from google service if already connected.
		if (myAccount.googleDriveConnection()) {
			myAccount.clickGoogleDisconnect();
			Thread.sleep(1000);
		}

		myAccount.clickSignOut();
		log.info("disconnectGoogleOauth - Passed"); 
	}

	@Test(groups = { "positive" })
	public void disconnectMicrosoftOauth() throws Exception {
		log.info("Running disconnectMicrosoftOauth Testcase");

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");

		String userEmail = ExcelUtility.getCellData(5, 1);
		String userPassword = ExcelUtility.getCellData(5, 2);

		// Sign-in with professional user
		signIn.clickSigninLink();
		signIn.userEmail(userEmail);
		Thread.sleep(500);
		signIn.userPassword(userPassword);
		signIn.clickSignInWithEmail();
		Thread.sleep(3000);

		// Disconnect from Microsoft One Drive Oauth in case of image avatar OR
		// default avatar
		try {
			if (myAccount.isDefaultAvatarDisplayed()) {
				myAccount.clickDefaultAvatar();
			}
		} catch (Exception e) {
			myAccount.clickAvaterImage();
		}
		Thread.sleep(1000);

		// Disconnect from onedrive service if already connected.
		if (myAccount.oneDriveConnection()) {
			myAccount.clickOneDriveDisconnect();
			Thread.sleep(1000);
		}

		myAccount.clickSignOut();
		log.info("disconnectMicrosoftOauth - Passed"); 
	}

	@AfterMethod
	public void afterClass(ITestResult testResult) throws IOException {
		utilities.FullScreenCapture.takeScreenShotOnFailure(testResult, driver);
		driver.close();
	}
}
