package com.mvn.automation;

import java.io.IOException;

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
import pageClasses.SigninPageFactory;
import pageClasses.SignupPageFactory;
import utilities.Constants;
import utilities.ExcelUtility;
import utilities.TestUtility;

public class CreateSpaceTest {

	private WebDriver driver;
	SignupPageFactory signUp;
	CreateSpacePageFactory spaceCreate;
	SigninPageFactory signIn;
	MyAccountPageFactory myAccount;
	Actions action;
	static Logger log = Logger.getLogger(CreateSpaceTest.class);

	String email = TestUtility.getRandomEmail();

	@Parameters({"browserType", "env"})
	@BeforeMethod
	public void setUp(String browser, String envir) throws Exception {
		PropertyConfigurator.configure("log4j.properties");

		driver = TestUtility.setupDriver(browser, envir);
		Assert.assertNotNull("Driver can not be null");
		
		signUp = new SignupPageFactory(driver);
		spaceCreate = new CreateSpacePageFactory(driver);
		signIn = new SigninPageFactory(driver);
		myAccount = new MyAccountPageFactory(driver);
		action = new Actions(driver);
	}

	@Test(groups = { "positive", "Smoke" })
	public void createNewSpace() throws Exception {
		log.info("Running createNewSpace testcase");
		Logger log1 = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getMethodName());

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
		log.info("createNewSpace - Passed"); 
	}

	@Test(groups = { "positive", "Smoke" })
	public void addMoreFilestoSpace() throws Exception {
		log.info("Running addMoreFilestoSpace to space testcase");

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String userEmail = ExcelUtility.getCellData(2, 1);
		String userPassword = ExcelUtility.getCellData(2, 2);

		signIn.clickSigninLink();
		signIn.userEmail(userEmail);
		Thread.sleep(500);
		signIn.userPassword(userPassword);
		signIn.clickSignInWithEmail();
		Thread.sleep(3000);

		spaceCreate.createNewSpace();
		spaceCreate.spaceName();
		spaceCreate.uploadFiletoSpace();
		Thread.sleep(3000);

		spaceCreate.clickAddFileButton();
		spaceCreate.uploadMoreFilesFromDesktop();
		Thread.sleep(3000);
		log.info("addMoreFilestoSpace - Passed"); 
	}

	@Test(groups = { "positive", "Smoke" })
	public void deleteSpace() throws Exception {
		log.info("Running deleteSpace testcase");

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String userEmail = ExcelUtility.getCellData(2, 1);
		String userPassword = ExcelUtility.getCellData(2, 2);

		signIn.clickSigninLink();
		signIn.userEmail(userEmail);
		Thread.sleep(500);
		signIn.userPassword(userPassword);
		signIn.clickSignInWithEmail();
		Thread.sleep(5000);

		WebElement firstSpace = driver.findElement(By.cssSelector("li.fadeIn"));
		action.moveToElement(firstSpace).perform();
		Thread.sleep(3000);
		
		spaceCreate.clickDeleteOnSpaceCard();		
		spaceCreate.clickDeleteConfirmButton();
		Thread.sleep(2000);
		log.info("deleteSpace - Passed"); 
	}

	@Test(groups = { "positive", "Smoke" })
	public void backgroundImageSpace() throws Exception {
		log.info("Running backgroundImageSpace testcase");
		
		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String userEmail = ExcelUtility.getCellData(2, 1);
		String userPassword = ExcelUtility.getCellData(2, 2);

		signIn.clickSigninLink();
		signIn.userEmail(userEmail);
		Thread.sleep(500);
		signIn.userPassword(userPassword);
		signIn.clickSignInWithEmail();
		Thread.sleep(5000);

		// Upload file to New space
		spaceCreate.createNewSpace();
		spaceCreate.spaceName();
		spaceCreate.uploadFiletoSpace();
		Thread.sleep(3000);

		spaceCreate.uploadSpacesBackgroundImage();
		Thread.sleep(6000);
		spaceCreate.saveBackgroundImageButton();
		Thread.sleep(3000);
		log.info("backgroundImageSpace - Passed"); 
	}

	@Test(groups = { "positive" })
	public void RemoveBackgroundImageSpace() throws Exception {
		log.info("Running RemoveBackgroundImageSpace testcase");

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
		Thread.sleep(5000);

		spaceCreate.uploadSpacesBackgroundImage();
		Thread.sleep(6000);
		spaceCreate.saveBackgroundImageButton();
		Thread.sleep(3000);
		spaceCreate.removeBackgroundImageSpace();
		Thread.sleep(3000);

		log.info("RemoveBackgroundImageSpace - Passed"); 
	}
	
	@Test(groups = { "positive" })
	public void copyASpace() throws Exception {
		log.info("Running copyASpace testcase");

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
		Thread.sleep(5000);
//		driver.navigate().refresh();
		
		spaceCreate.clickSpaceMenu();
		String spaceName = spaceCreate.getSpaceName();
		
		spaceCreate.copyTheSpace();
		spaceCreate.copySpaceConfirm();
		Thread.sleep(2000);
		driver.navigate().refresh();
		Thread.sleep(2000);
		
		Assert.assertEquals(spaceCreate.getSpaceName(), "Copy of " + spaceName);
		
		myAccount.openSideBarMenu();
		Thread.sleep(1000);
		myAccount.clickSignOut();
		log.info("copyASpace - Passed"); 
	}
	
	@AfterMethod
	public void afterClass(ITestResult testResult) throws IOException {
		utilities.FullScreenCapture.takeScreenShotOnFailure(testResult, driver);
		driver.close();
	}
}
