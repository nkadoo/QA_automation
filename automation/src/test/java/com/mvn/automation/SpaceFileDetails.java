package com.mvn.automation;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pageClasses.CreateSpacePageFactory;
import pageClasses.MyAccountPageFactory;
import pageClasses.ShareSpacesFactory;
import pageClasses.SigninPageFactory;
import pageClasses.SignupPageFactory;
import pageClasses.VersionViewPageFactory;
import utilities.Constants;
import utilities.ExcelUtility;
import utilities.TestUtility;

public class SpaceFileDetails {

	private WebDriver driver;
	SignupPageFactory signUp;
	CreateSpacePageFactory spaceCreate;
	SigninPageFactory signIn;
	VersionViewPageFactory versionView;
	ShareSpacesFactory share;
	MyAccountPageFactory myAccount;
	Actions action;
	Actions action2;
	static Logger log = Logger.getLogger(CreateSpaceTest.class);
	String email = TestUtility.getRandomEmail();

	@Parameters({ "browserType", "env" })
	@BeforeMethod
	public void setUp(String browser, String envir) throws Exception {
		PropertyConfigurator.configure("log4j.properties");

		driver = TestUtility.setupDriver(browser, envir);
		Assert.assertNotNull("Driver can not be null");

		signUp = new SignupPageFactory(driver);
		spaceCreate = new CreateSpacePageFactory(driver);
		signIn = new SigninPageFactory(driver);
		versionView = new VersionViewPageFactory(driver);
		share = new ShareSpacesFactory(driver);
		myAccount = new MyAccountPageFactory(driver);
		action = new Actions(driver);

		// Maximize the browser's window
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		PropertyConfigurator.configure("log4j.properties");
		driver.get(Constants.URL);
	}

	@Test(groups = { "positive", "Smoke" })
	public void approveFile() throws Exception {
		log.info("Running approveFile testcase");
		Logger log1 = Logger
				.getLogger(Thread.currentThread().getStackTrace()[1]
						.getMethodName());

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");

		String userEmail = ExcelUtility.getCellData(2, 1);
		String userPassword = ExcelUtility.getCellData(2, 2);

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
		Thread.sleep(3000);

		spaceCreate.clickFirstFile();

		spaceCreate.clickApproveFile();
		Thread.sleep(3000);
		Assert.assertTrue(spaceCreate.isFileApproved());
		log.info("approveFile - Passed"); 
	}

	@Test(groups = { "positive", "Smoke" })
	public void deleteFile() throws Exception {
		log.info("Running deleteFile testcase");
		Logger log1 = Logger
				.getLogger(Thread.currentThread().getStackTrace()[1]
						.getMethodName());
		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String userEmail = ExcelUtility.getCellData(2, 1);
		String userPassword = ExcelUtility.getCellData(2, 2);

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
		Thread.sleep(3000);

		spaceCreate.clickFirstFile();
		
		spaceCreate.openFileDetailsOptions();
		spaceCreate.deleteFile();
		Thread.sleep(2000);
		Assert.assertTrue(spaceCreate.isDeleteConfirmationPresent());
		log.info("deleteFile - Passed"); 
	}

	@Test(groups = { "positive", "Smoke" })
	public void addFileVersion() throws Exception {
		log.info("Running addFileVersion testcase");
		Logger log1 = Logger
				.getLogger(Thread.currentThread().getStackTrace()[1]
						.getMethodName());

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String userEmail = ExcelUtility.getCellData(2, 1);
		String userPassword = ExcelUtility.getCellData(2, 2);

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
		Thread.sleep(3000);

		spaceCreate.clickFirstFile();
		
		spaceCreate.clickAddFileOptions();
		spaceCreate.addFileVersion();
		Thread.sleep(3000);

		Assert.assertEquals(spaceCreate.versionNumber(), "VERSION 2");
		log.info("addFileVersion - Passed"); 
	}

	@Test(groups = { "positive", "Smoke" })
	public void unApproveFile() throws Exception {
		log.info("Running unApproveFile testcase");
		Logger log1 = Logger
				.getLogger(Thread.currentThread().getStackTrace()[1]
						.getMethodName());

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String userEmail = ExcelUtility.getCellData(2, 1);
		String userPassword = ExcelUtility.getCellData(2, 2);

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
		Thread.sleep(3000);

		spaceCreate.clickFirstFile();

		spaceCreate.clickApproveFile();
		Thread.sleep(3000);
		Assert.assertTrue(spaceCreate.isFileApproved());

		spaceCreate.clickUnapprove();
		Thread.sleep(1000);
		Assert.assertTrue(spaceCreate.isVersionTextAvailable());
		log.info("unApproveFile - Passed"); 
	}

	@Test(groups = { "positive", "Smoke" })
	public void mentionUser() throws Exception {
		log.info("Running mentionUser testcase");
		Logger log1 = Logger
				.getLogger(Thread.currentThread().getStackTrace()[1]
						.getMethodName());

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String userEmail = ExcelUtility.getCellData(2, 1);
		String userPassword = ExcelUtility.getCellData(2, 2);

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
		Thread.sleep(3000);

		spaceCreate.clickFirstFile();
		
		Thread.sleep(1000);
		spaceCreate.clickOnImage();
		spaceCreate.enterMention();
		spaceCreate.clickPostComment();
		Thread.sleep(3000);
		log.info("mentionUser - Passed"); 
	}

	@Test(groups = { "positive", "Smoke" })
	public void followUp() throws Exception {
		log.info("Running followUp testcase");
		Logger log1 = Logger
				.getLogger(Thread.currentThread().getStackTrace()[1]
						.getMethodName());

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String userEmail = ExcelUtility.getCellData(2, 1);
		String userPassword = ExcelUtility.getCellData(2, 2);

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
		Thread.sleep(3000);

		spaceCreate.clickFirstFile();		
		Thread.sleep(1000);
		spaceCreate.clickOnImage();
		spaceCreate.enterMention();
		spaceCreate.requestFollowUp();
		Thread.sleep(3000);
		spaceCreate.clickPostComment();
		Thread.sleep(1000);
		
		Assert.assertTrue(spaceCreate.isFollowUpDisplayed());
		log.info("followUp - Passed"); 
	}

	@Test(groups = { "positive", "Smoke" })
	public void addComment() throws Exception {
		log.info("Running addComment testcase");
		Logger log1 = Logger
				.getLogger(Thread.currentThread().getStackTrace()[1]
						.getMethodName());

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String userEmail = ExcelUtility.getCellData(2, 1);
		String userPassword = ExcelUtility.getCellData(2, 2);

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
		Thread.sleep(3000);

		spaceCreate.clickFirstFile();
		
		Thread.sleep(1000);
		spaceCreate.clickOnImage();
		spaceCreate.enterComment();
		spaceCreate.clickPostComment();
		Thread.sleep(3000);
		log.info("addComment - Passed"); 
	}

	@Test(groups = { "positive", "Smoke" })
	public void makeOldVersionCurrent() throws Exception {
		log.info("Running makeOldVersionCurrent testcase");
		Logger log1 = Logger
				.getLogger(Thread.currentThread().getStackTrace()[1]
						.getMethodName());
		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String userEmail = ExcelUtility.getCellData(2, 1);
		String userPassword = ExcelUtility.getCellData(2, 2);

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
		Thread.sleep(3000);

		spaceCreate.clickFirstFile();
		spaceCreate.clickAddFileOptions();
		spaceCreate.addFileVersion();
		Thread.sleep(3000);
		spaceCreate.clickVersionText();
		versionView.clickOlderVersion();
		Thread.sleep(1000);
		versionView.makeCurrentVersion();
		Thread.sleep(2000);

		Assert.assertEquals(spaceCreate.getFileName(), "Desert.bmp");
		log.info("makeOldVersionCurrent - Passed"); 
	}

	@Test(groups = { "positive", "Smoke" })
	public void deleteOldVersion() throws Exception {
		log.info("Running deleteOldVersion testcase");
		Logger log1 = Logger
				.getLogger(Thread.currentThread().getStackTrace()[1]
						.getMethodName());
		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String userEmail = ExcelUtility.getCellData(2, 1);
		String userPassword = ExcelUtility.getCellData(2, 2);

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
		Thread.sleep(3000);

		spaceCreate.clickFirstFile();
		spaceCreate.clickAddFileOptions();
		spaceCreate.addFileVersion();
		Thread.sleep(3000);
		spaceCreate.clickVersionText();
		versionView.clickOlderVersion();
		versionView.deleteOldVersion();
		Thread.sleep(1000);
		spaceCreate.clickDeleteConfirmationBtn();
		// Verify that the version view returns "1 VERSION"
		Assert.assertEquals(spaceCreate.getFileName(), "Jellyfish.bmp");
		log.info("deleteOldVersion - Passed"); 	
		}

	@Test(groups = { "positive", "Smoke" })
	public void requestApprovalFromSpace() throws Exception {
		Logger log1 = Logger
				.getLogger(Thread.currentThread().getStackTrace()[1]
						.getMethodName());
		log.info("Running requestApprovalFromSpace testcase");

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String userEmail = ExcelUtility.getCellData(7, 1);
		String userPassword = ExcelUtility.getCellData(7, 2);
		String recptxlsEmail = ExcelUtility.getCellData(1, 1);

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
		Thread.sleep(3000);

		// Open space settings and select "only specific people"
		spaceCreate.clickSpaceMenu();
		spaceCreate.clickSpaceSettings();
		spaceCreate.selectOnlySpecificPeople();

		// Select only followers requested to approve
		spaceCreate.selectApproveOnlyRequested();

		// Save share and approval settings
		spaceCreate.clickSaveSpaceSettingsBtn();
		Thread.sleep(8000);

		// Share private space so an approval can be requested
		share.shareBtnClick();
		share.enterRcptEmail(recptxlsEmail);
		share.enterShareMsg("Private share");
		Thread.sleep(1000);
		share.sendShareBtnClick();
		Thread.sleep(8000);

		// Open space menu and select Request approval
		spaceCreate.clickSpaceMenu();
		spaceCreate.clickRequestApproval();
		Thread.sleep(1000);

		// Select follower and file for approval
		spaceCreate.selectFollower();
		spaceCreate.selectFileToApprove();
		Thread.sleep(1000);

		Assert.assertEquals(spaceCreate.approvalHeaderText(),
				"1 PERSON NEEDS TO APPROVE 1 FILE");

		// Send for approval
		spaceCreate.sendForApproval();
		Thread.sleep(2000);

		Assert.assertEquals(spaceCreate.getNotificationText(),
				"APPROVAL REQUESTED. We'll notify you as approvals are added.");
		Thread.sleep(4000);

		myAccount.openSideBarMenu();
		Thread.sleep(1000);
		myAccount.clickSignOut();
		log.info("requestApprovalFromSpace - Passed"); 	
	}

	@Test(groups = { "positive", "Smoke" })
	public void requestApprovalFromFile() throws Exception {
		Logger log1 = Logger
				.getLogger(Thread.currentThread().getStackTrace()[1]
						.getMethodName());
		log.info("Running requestApprovalFromFile testcase");

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");

		String userEmail = ExcelUtility.getCellData(7, 1);
		String userPassword = ExcelUtility.getCellData(7, 2);
		String recptxlsEmail = ExcelUtility.getCellData(1, 1);

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
		Thread.sleep(3000);

		// Open space settings and select "only specific people"
		spaceCreate.clickSpaceMenu();
		spaceCreate.clickSpaceSettings();
		spaceCreate.selectOnlySpecificPeople();

		// Select only followers requested to approve
		spaceCreate.selectApproveOnlyRequested();

		// Save share and approval settings
		spaceCreate.clickSaveSpaceSettingsBtn();
		Thread.sleep(9000);

		// Share private space so an approval can be requested
		share.shareBtnClick();
		share.enterRcptEmail(recptxlsEmail);
		share.enterShareMsg("Private share");
		Thread.sleep(1000);
		share.sendShareBtnClick();
		Thread.sleep(8000);

		// Open the first file in the space
		spaceCreate.clickFirstFile();

		// Open file menu and select request for approval
		spaceCreate.openFileDetailsOptions();
		spaceCreate.fileMenuRequestApproval();
		Thread.sleep(1000);

		// Select follower and file for approval
		spaceCreate.selectFollower();
		Thread.sleep(1000);

		Assert.assertEquals(spaceCreate.approvalHeaderText(),
				"1 PERSON NEEDS TO APPROVE 1 FILE");

		// Send for approval
		spaceCreate.sendForApproval();

		Assert.assertEquals(spaceCreate.getPendingApprovalText(),
				"VERSION 1 IS PENDING APPROVAL");
		Thread.sleep(1000);
		
		spaceCreate.closeFile();
		Thread.sleep(3000);
		
		myAccount.openSideBarMenu();
		Thread.sleep(1000);
		myAccount.clickSignOut();
		log.info("requestApprovalFromFile - Passed"); 
	}
	
	@Test(groups = { "positive", "Smoke" })
	public void zoomImage() throws Exception {
		log.info("Running zoomImage testcase");

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");

		String userEmail = ExcelUtility.getCellData(2, 1);
		String userPassword = ExcelUtility.getCellData(2, 2);

		signIn.clickSigninLink();
		signIn.userEmail(userEmail);
		Thread.sleep(500);
		signIn.userPassword(userPassword);
		signIn.clickSignInWithEmail();
		Thread.sleep(3000);

		// Upload file to New space
		spaceCreate.createNewSpace();
		spaceCreate.spaceName("Zoom test " + TestUtility.getDate());
		spaceCreate.uploadFiletoSpace();
		Thread.sleep(3000);

		// Open the first file in the space
		spaceCreate.clickFirstFile();
		Thread.sleep(2000);

		String zoomPercent = spaceCreate.currentZoomPercent();
		spaceCreate.zoomIn(5);
		Thread.sleep(1000);
		spaceCreate.zoomOut(1);
		Thread.sleep(1000);
		spaceCreate.zoomToFitImage();
		
		Assert.assertEquals(spaceCreate.currentZoomPercent(), zoomPercent);
		
		spaceCreate.closeFile();
		Thread.sleep(3000);
		
		myAccount.openSideBarMenu();
		Thread.sleep(1000);
		myAccount.clickSignOut();
		log.info("zoomImage - Passed"); 
	}

	@AfterMethod
	public void afterClass(ITestResult testResult) throws IOException {
		utilities.FullScreenCapture.takeScreenShotOnFailure(testResult, driver);
		driver.close();
	}
}
