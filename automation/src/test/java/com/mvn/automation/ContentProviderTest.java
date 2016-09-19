package com.mvn.automation;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pageClasses.MyAccountPageFactory;
import pageClasses.SigninPageFactory;
import utilities.Constants;
import utilities.ExcelUtility;
import utilities.ProviderLogInUtility;

public class ContentProviderTest {
	private WebDriver driver;

	SigninPageFactory signIn;
	MyAccountPageFactory myAccount;

	static Logger log = Logger.getLogger(ContentProviderTest.class);

	@Parameters({ "browserType", "env" })
	@BeforeMethod
	public void setUp(String browser, String envir) throws Exception {
		if (browser.equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
		} else if (browser.equalsIgnoreCase("chrome")) {
			// http://chromedriver.storage.googleapis.com/index.html
			System.setProperty("webdriver.chrome.driver",
					Constants.Chrome_driver_location);
			driver = new ChromeDriver();
		}
		if (envir.equalsIgnoreCase("stage")) {
			driver.get(Constants.URL);
		} else if (envir.equalsIgnoreCase("prod")) {
			driver.get(Constants.PROD_URL);
		}

		// INITIALIZE DRIVER AS NEEDED//
		myAccount = new MyAccountPageFactory(driver);
		signIn = new SigninPageFactory(driver);

		// MANDATORY- MAXIMIZE THE BROWSER'S WINDOW
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		PropertyConfigurator.configure("log4j.properties");
	}

	@Test(groups = "positive")
	public void connectToDropbox() throws Exception {
		log.info("Running 'Connect to Dropbox from sidebar' case");

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");

		String userEmail = ExcelUtility.getCellData(13, 1);
		String userPassword = ExcelUtility.getCellData(13, 2);

		signIn.clickSigninLink();
		signIn.userEmail(userEmail);
		Thread.sleep(500);
		signIn.userPassword(userPassword);
		signIn.clickSignInWithEmail();
		Thread.sleep(2000);

		// Opening side bar menu
		myAccount.openSideBarMenu();
		Thread.sleep(2000);

		// Disconnect from dropbox service if already connected.
		if (myAccount.dropboxConnection()) {
			myAccount.clickDropboxDisconnect();
			Thread.sleep(2000);
			Thread.sleep(2000);
		}

		myAccount.clickDropboxDisconnect();
		ProviderLogInUtility.logInToService(driver, "dropbox");
		Thread.sleep(2000);
		
		myAccount.openSideBarMenu();
		Assert.assertEquals(true, myAccount.dropboxConnection());
		log.info("connectToDropbox - Passed"); 
	}

	@Test(groups = "positive")
	public void disconnectDropbox() throws Exception {
		log.info("Running 'Disconnect from Dropbox from sidebar' case");

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String userEmail = ExcelUtility.getCellData(13, 1);
		String userPassword = ExcelUtility.getCellData(13, 2);

		signIn.clickSigninLink();
		signIn.userEmail(userEmail);
		Thread.sleep(500);
		signIn.userPassword(userPassword);
		signIn.clickSignInWithEmail();
		Thread.sleep(2000);

		// Opening side bar menu
		myAccount.openSideBarMenu();
		Thread.sleep(2000);

		// Check to see if you are already connected and if so, disconnect
		if (!myAccount.dropboxConnection()) {
			myAccount.clickDropboxDisconnect();
			ProviderLogInUtility.logInToService(driver, "dropbox");
			myAccount.openSideBarMenu();
		} else {
			log.info("Dropbox is connected");
		}

		Thread.sleep(2000);
		myAccount.clickDropboxDisconnect();

		Thread.sleep(2000);
		Assert.assertEquals(false, myAccount.dropboxConnection());
		log.info("disconnectDropbox - Passed"); 
	}

	@Test(groups = "positive")
	public void connectToGoogleDrive() throws Exception {
		log.info("Running 'Connect to Google Drive from sidebar' case");

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String userEmail = ExcelUtility.getCellData(13, 1);
		String userPassword = ExcelUtility.getCellData(13, 2);

		signIn.clickSigninLink();
		signIn.userEmail(userEmail);
		Thread.sleep(500);
		signIn.userPassword(userPassword);
		signIn.clickSignInWithEmail();
		Thread.sleep(2000);

		// Opening side bar menu
		myAccount.openSideBarMenu();
		Thread.sleep(2000);

		// Disconnect from google service if already connected.
		if (myAccount.googleDriveConnection()) {
			myAccount.clickGoogleDisconnect();
			Thread.sleep(2000);
			Thread.sleep(2000);
		}

		myAccount.clickGoogleDisconnect();
		ProviderLogInUtility.logInToService(driver, "googledrive");

		Thread.sleep(2000);		
		myAccount.openSideBarMenu();

		Assert.assertEquals(true, myAccount.googleDriveConnection());
		log.info("connectToGoogleDrive - Passed"); 
	}

	@Test(groups = "positive")
	public void disconnectGoogle() throws Exception {
		log.info("Running 'Disconnect from Google Drive from sidebar' case");

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String userEmail = ExcelUtility.getCellData(13, 1);
		String userPassword = ExcelUtility.getCellData(13, 2);

		signIn.clickSigninLink();
		signIn.userEmail(userEmail);
		Thread.sleep(500);
		signIn.userPassword(userPassword);
		signIn.clickSignInWithEmail();
		Thread.sleep(2000);

		// Opening side bar menu
		myAccount.openSideBarMenu();
		Thread.sleep(2000);

		// Check to see if you are already connected and if so, disconnect
		if (!myAccount.googleDriveConnection()) {
			myAccount.clickGoogleDisconnect();
			ProviderLogInUtility.logInToService(driver, "googledrive");

			myAccount.openSideBarMenu();
		} else {
			log.info("Google is connected");
		}

		Thread.sleep(2000);
		myAccount.clickGoogleDisconnect();

		Thread.sleep(2000);
		Assert.assertEquals(false, myAccount.googleDriveConnection());
		log.info("disconnectGoogle - Passed"); 
	}

	@Test(groups = "positive")
	public void connectToOneDrive() throws Exception {
		log.info("Running 'Connect to One Drive from sidebar' case");

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String userEmail = ExcelUtility.getCellData(13, 1);
		String userPassword = ExcelUtility.getCellData(13, 2);

		signIn.clickSigninLink();
		signIn.userEmail(userEmail);
		Thread.sleep(500);
		signIn.userPassword(userPassword);
		signIn.clickSignInWithEmail();
		Thread.sleep(2000);

		// Opening side bar menu
		myAccount.openSideBarMenu();
		Thread.sleep(2000);

		// Disconnect from onedrive service if already connected.
		if (myAccount.oneDriveConnection()) {
			myAccount.clickOneDriveDisconnect();
			Thread.sleep(2000);
			Thread.sleep(2000);
		}

		myAccount.clickOneDriveDisconnect();
		ProviderLogInUtility.logInToService(driver, "onedrive");

		Thread.sleep(3000);
		myAccount.openSideBarMenu();

		Assert.assertEquals(true, myAccount.oneDriveConnection());
		log.info("connectToOneDrive - Passed"); 
	}

	@Test(groups = "positive")
	public void disconnectOneDrive() throws Exception {
		log.info("Running 'Disconnect from One Drive from sidebar' case");

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String userEmail = ExcelUtility.getCellData(13, 1);
		String userPassword = ExcelUtility.getCellData(13, 2);

		signIn.clickSigninLink();
		signIn.userEmail(userEmail);
		Thread.sleep(500);
		signIn.userPassword(userPassword);
		signIn.clickSignInWithEmail();
		Thread.sleep(2000);

		// Opening side bar menu
		myAccount.openSideBarMenu();
		Thread.sleep(2000);

		// Check to see if you are already connected and if so, disconnect
		if (!myAccount.oneDriveConnection()) {
			myAccount.clickOneDriveDisconnect();
			ProviderLogInUtility.logInToService(driver, "onedrive");			
			myAccount.openSideBarMenu();
		} else {
			log.info("One Drive is connected");
		}

		Thread.sleep(2000);
		myAccount.clickOneDriveDisconnect();

		Thread.sleep(2000);
		Assert.assertEquals(false, myAccount.oneDriveConnection());
		log.info("disconnectOneDrive - Passed"); 
	}

	@AfterMethod
	public void afterClass(ITestResult testResult) throws IOException {
		utilities.FullScreenCapture.takeScreenShotOnFailure(testResult, driver);
		driver.close();
	}
}
