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
import pageClasses.MyAccountPageFactory;
import pageClasses.FilePickerPageFactory;
import utilities.Constants;
import utilities.ExcelUtility;
import utilities.ProviderLogInUtility;
import utilities.TestUtility;

public class CreateSpaceViaOauthTest {
	private WebDriver driver;
	SigninPageFactory signIn;
	MyAccountPageFactory myAccount;
	CreateSpacePageFactory spaceCreate;
	ShareSpacesFactory shareSpace;
	FilePickerPageFactory filePicker;
	static Logger log = Logger.getLogger(CreateSpaceViaOauthTest.class);

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
	public void createSpaceDropboxOauth() throws Exception {
		log.info("Running createSpaceDropboxOauth Testcase");

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
		Thread.sleep(3000);

		ProviderLogInUtility.logInToService(driver, "dropbox");

		// Select Dropbox file to upload
		filePicker.SelectFile();
		filePicker.clickAddFilePickerBtn();
		Thread.sleep(2000);

		// Open side bar
		myAccount.openSideBarMenu();
		Thread.sleep(1000);
		// Sign out
		myAccount.clickSignOut();
		log.info("createSpaceDropboxOauth - Passed"); 
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

		ProviderLogInUtility.logInToService(driver, "googledrive");

		// Select Google Drive file to upload
		filePicker.SelectFile();
		filePicker.clickAddFilePickerBtn();
		Thread.sleep(2000);

		// Open side bar
		myAccount.openSideBarMenu();
		Thread.sleep(1000);
		// Sign out
		myAccount.clickSignOut();
		log.info("createSpaceGoogleOauth - Passed"); 
	}

	@Test(groups = { "positive", "Smoke" })
	public void createSpaceMicrosoftOauth() throws Exception {
		log.info("Running createSpaceMicrosoftOauth Testcase");

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String userEmail = ExcelUtility.getCellData(6, 1);
		String userPassword = ExcelUtility.getCellData(6, 2);

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

		ProviderLogInUtility.logInToService(driver, "onedrive");

		// Select Microsoft One Drive file to upload
		Thread.sleep(3000);
		filePicker.SelectFile();
		filePicker.clickAddFilePickerBtn();
		Thread.sleep(3000);

		// Open side bar
		myAccount.openSideBarMenu();
		Thread.sleep(1000);
		// Sign out
		myAccount.clickSignOut();
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

		// Open side bar
		myAccount.openSideBarMenu();
		Thread.sleep(1000);

		// Disconnect from dropbox service if already connected.
		if (myAccount.dropboxConnection()) {
			myAccount.clickDropboxDisconnect();
			Thread.sleep(1000);
		}
		Thread.sleep(2000);

		Assert.assertEquals(false, myAccount.dropboxConnection());

		// Sign out
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

		// Open side bar
		myAccount.openSideBarMenu();
		Thread.sleep(1000);

		// Disconnect from google service if already connected.
		if (myAccount.googleDriveConnection()) {
			myAccount.clickGoogleDisconnect();
			Thread.sleep(1000);
		}
		Thread.sleep(2000);

		Assert.assertEquals(false, myAccount.googleDriveConnection());

		// Sign out
		myAccount.clickSignOut();
		log.info("disconnectGoogleOauth - Passed"); 
	}

	@Test(groups = { "positive" })
	public void disconnectMicrosoftOauth() throws Exception {
		log.info("Running disconnectMicrosoftOauth Testcase");

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String userEmail = ExcelUtility.getCellData(6, 1);
		String userPassword = ExcelUtility.getCellData(6, 2);

		// Sign-in with professional user
		signIn.clickSigninLink();
		signIn.userEmail(userEmail);
		Thread.sleep(500);
		signIn.userPassword(userPassword);
		signIn.clickSignInWithEmail();
		Thread.sleep(3000);

		// Open side bar
		myAccount.openSideBarMenu();
		Thread.sleep(1000);

		// Disconnect from onedrive service if already connected.
		if (myAccount.oneDriveConnection()) {
			myAccount.clickOneDriveDisconnect();
			Thread.sleep(1000);
		}
		Thread.sleep(2000);

		Assert.assertEquals(false, myAccount.oneDriveConnection());

		// Sign out
		myAccount.clickSignOut();
		log.info("disconnectMicrosoftOauth - Passed"); 
	}

	@AfterMethod
	public void afterClass(ITestResult testResult) throws IOException {
		utilities.FullScreenCapture.takeScreenShotOnFailure(testResult, driver);
		driver.close();
	}
}
