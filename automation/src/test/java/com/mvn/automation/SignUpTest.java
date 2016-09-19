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
import pageClasses.SignupPageFactory;
import utilities.Constants;
import utilities.ExcelUtility;
import utilities.TestUtility;

public class SignUpTest {
	private WebDriver driver;
	SignupPageFactory signUp;
	CreateSpacePageFactory spaceCreate;
	MyAccountPageFactory myAccount;
	static Logger log = Logger.getLogger(SignUpTest.class);

	Actions action;

	@Parameters({ "browserType", "env" })
	@BeforeMethod
	public void setUp(String browser, String envir) throws Exception {
		PropertyConfigurator.configure("log4j.properties");

		driver = TestUtility.setupDriver(browser, envir);
		Assert.assertNotNull("Driver can not be null");

		signUp = new SignupPageFactory(driver);
		spaceCreate = new CreateSpacePageFactory(driver);
		myAccount = new MyAccountPageFactory(driver);
		action = new Actions(driver);
	}

	@Test(groups = { "positive", "Smoke" })
	public void signUpNewUser() throws Exception {
		log.info("Running signUpNewUser Test case");

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String fullName = ExcelUtility.getCellData(1, 0);
		String password = ExcelUtility.getCellData(1, 2);
		String email = TestUtility.getRandomEmail();

		signUp.clickSignUpForFree();
		signUp.fullName(fullName);
		signUp.emailSignup(email);
		Thread.sleep(500);
		signUp.passwordSignup(password);
		signUp.clickSignUpWithEmail();
		Thread.sleep(3000);
		log.info("signUpNewUser - Passed");
	}

	@Test(groups = { "negative" })
	public void signUpexistingUser() throws Exception {
		log.info("Running signUpexistingUser Test case");

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String fullName = ExcelUtility.getCellData(1, 0);
		String email = ExcelUtility.getCellData(1, 1);
		String password = ExcelUtility.getCellData(1, 2);

		signUp.clickSignUpForFree();
		signUp.fullName(fullName);
		signUp.emailSignup(email);
		Thread.sleep(500);
		signUp.passwordSignup(password);
		signUp.clickSignUpWithEmail();
		Thread.sleep(3000);
		// Check the error is shown for signing up a registered user
		Assert.assertEquals(signUp.emailExistError(), true);
		log.info("signUpexistingUser - Passed");
	}

	@Test(groups = { "negative" })
	public void signUpInvalidEmail() throws Exception {
		log.info("Running signUpInvalidEmail Testcase ");

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String fullName = ExcelUtility.getCellData(1, 0);
		String email = "sel_invalid_email";
		String password = ExcelUtility.getCellData(1, 2);

		signUp.clickSignUpForFree();
		signUp.fullName(fullName);
		signUp.emailSignup(email);
		Thread.sleep(500);
		signUp.passwordSignup(password);
		signUp.clickSignUpWithEmail();
		Thread.sleep(3000);
		WebElement toElement = driver
				.findElement(By
						.cssSelector("form[name=\'signupForm\'] > div.field > div[ng-show=\'errors.signup.email'"));
		action.moveToElement(toElement).perform();
		Thread.sleep(3000);
		// Check the error is shown for signing up with invalid email
		Assert.assertEquals(signUp.emailInvalidEmailExist(), true);
		log.info("signUpInvalidEmail - Passed");
	}

	@Test(groups = { "negative" })
	public void signUpMissingName() throws Exception {
		log.info("Running signUpMissingName Testcase");

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String fullName = "";
		String password = ExcelUtility.getCellData(1, 2);
		String email = TestUtility.getRandomEmail();

		signUp.clickSignUpForFree();
		signUp.fullName(fullName);
		signUp.emailSignup(email);
		Thread.sleep(500);
		signUp.passwordSignup(password);
		signUp.clickSignUpWithEmail();
		Thread.sleep(3000);
		WebElement toElement = driver
				.findElement(By
						.cssSelector("form[name=\'signupForm\'] > div.field > div[ng-show=\'errors.signup.name'"));
		action.moveToElement(toElement).perform();
		Thread.sleep(3000);
		// Check the error is shown for signing up with missing name
		Assert.assertEquals(signUp.errorMissingName(), true);
		log.info("signUpMissingName - Passed");
	}

	@Test(groups = { "negative" })
	public void signUpMissingPassword() throws Exception {
		log.info("Running signUpMissingPassword Testcase");

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String fullName = ExcelUtility.getCellData(1, 0);
		String email = ExcelUtility.getCellData(1, 1);
		String password = "";

		signUp.clickSignUpForFree();
		signUp.fullName(fullName);
		signUp.emailSignup(email);
		Thread.sleep(500);
		signUp.passwordSignup(password);
		signUp.clickSignUpWithEmail();
		Thread.sleep(3000);
		WebElement toElement = driver
				.findElement(By
						.cssSelector("form[name=\'signupForm\'] > div.field > div[ng-show=\'errors.signup.password'"));
		action.moveToElement(toElement).perform();
		Thread.sleep(3000);
		// Check the error is shown for signing up with missing password
		Assert.assertEquals(signUp.ErrorMissingPassword(), true);
		log.info("signUpMissingPassword - Passed");
	}

	@Test(groups = { "negative" })
	public void signUpBlockedDomain() throws Exception {
		log.info("Running signUpBlockedDomain test case");

		signUp.clickSignUpForFree();
		signUp.fullName("Domain Block");
		signUp.emailSignup("domain_block_test@ras.co");
		Thread.sleep(500);
		signUp.passwordSignup("111111");
		signUp.clickSignUpWithEmail();
		Thread.sleep(1000);

		Assert.assertEquals(signUp.domainBlockTextDisplayed(), true);
		log.info("signUpBlockedDomain - Passed");
	}

	@Test(groups = { "positive", "Smoke" })
	public void coachMarksNewUser() throws Exception {
		log.info("Running coachMarksNewUser test case");

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String fullName = ExcelUtility.getCellData(1, 0);
		String password = ExcelUtility.getCellData(1, 2);
		String email = TestUtility.getRandomEmail();

		signUp.clickSignUpForFree();
		signUp.fullName(fullName);
		signUp.emailSignup(email);
		Thread.sleep(500);
		signUp.passwordSignup(password);
		signUp.clickSignUpWithEmail();
		Thread.sleep(2000);

		spaceCreate.closeOnboarding();
		Thread.sleep(1000);

		String createSpaceCoachMark = spaceCreate.coachMarkTitle();
		Assert.assertEquals(createSpaceCoachMark, "CREATE A SPACE");

		spaceCreate.createNewSpace();
		spaceCreate.uploadFiletoSpace();
		Thread.sleep(3000);
		
		String previewFileCoachMark = spaceCreate.coachMarkTitle();
		Assert.assertEquals(previewFileCoachMark, "CLICK TO PREVIEW AND COMMENT");
		
		spaceCreate.clickFirstFile();
		Thread.sleep(1000);
		
		String addCommentCoachMark = spaceCreate.coachMarkTitle();
		Assert.assertEquals(addCommentCoachMark, "CLICK AND DRAG TO ADD A COMMENT");
		
		spaceCreate.clickDoThisLater();
		Thread.sleep(1000);
 
		spaceCreate.closeFile();
		Thread.sleep(1000);
		
		String shareCoachMark = spaceCreate.coachMarkTitle();
		Assert.assertEquals(shareCoachMark, "SHARE IT NOW");
		
		spaceCreate.clickDoThisLater();
		
		myAccount.openSideBarMenu();
		Thread.sleep(1000);
		myAccount.clickSignOut();
		
		log.info("coachMarksNewUser - Passed");
	}

	@AfterMethod
	public void afterClass(ITestResult testResult) throws IOException {
		utilities.FullScreenCapture.takeScreenShotOnFailure(testResult, driver);
		driver.close();
	}
}
