package com.mvn.automation;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pageClasses.MyAccountPageFactory;
import pageClasses.SigninPageFactory;
import pageClasses.SignupPageFactory;
import pageClasses.UpgradeAccountPageFactory;
import utilities.Constants;
import utilities.ExcelUtility;
import utilities.TestUtility;

public class UpgradeAccountTest {

	private WebDriver driver;
	SigninPageFactory signIn;
	UpgradeAccountPageFactory upgrade;
	SignupPageFactory signUp;
	MyAccountPageFactory myAccount;
	static Logger log = Logger.getLogger(UpgradeAccountTest.class);

	Actions action;

	@Parameters({ "browserType", "env" })	
	@BeforeMethod
	public void setUp(String browser, String envir) throws Exception {
		PropertyConfigurator.configure("log4j.properties");

		driver = TestUtility.setupDriver(browser, envir);
		Assert.assertNotNull("Driver can not be null", driver);

		signIn = new SigninPageFactory(driver);
		upgrade = new UpgradeAccountPageFactory(driver);
		signUp = new SignupPageFactory(driver);
		myAccount = new MyAccountPageFactory(driver);
		action = new Actions(driver);
	}	

	@Parameters({ "browserType", "env" })	
	@Test(groups = { "positive", "Smoke" })
	public void upgradeMonthly(String browserType1, String envir1) throws Exception {
		log.info("Running upgradeMonthly Test case");

		switch (envir1.toLowerCase()) {
		case "prod":
			log.info("upgradeMonthly test is not eligible to run in PROD");
			break;
		case "stage":
		default:		
			ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
			String fullName = ExcelUtility.getCellData(1, 0);
			String password = ExcelUtility.getCellData(1, 2);
			String email = TestUtility.getRandomEmail();
	
			signUp.clickSignUpForFree();
			signUp.fullName(fullName);
			Thread.sleep(500);
			log.info("New email generated " + email);
			signUp.emailSignup(email);
			Thread.sleep(500);
			signUp.passwordSignup(password);
			signUp.clickSignUpWithEmail();
			Thread.sleep(3000);
	
			upgrade.closeHelp();
			Thread.sleep(3000);	
			myAccount.openSideBarMenu();
			Thread.sleep(1000);
	
			upgrade.clickUpgrade();
			upgrade.CCDetails();
			upgrade.chooseMonthlyBilling();
			upgrade.clickUpgradeAccount();
			Thread.sleep(5000);
			Assert.assertEquals(upgrade.yourPlan(), "PRO PLAN");
			upgrade.closeUpgradeScreen();
			Thread.sleep(2000);
	
			myAccount.clickSignOut();
			log.info("upgradeMonthly - Passed"); 
			break;
		}
	}

	@Parameters({ "browserType", "env" })	
	@Test(groups = { "positive", "Smoke" })
	public void upgradeAnnual(String browser1, String envir1) throws Exception {
		log.info("Running upgradeAnnual Test case");

		switch (envir1.toLowerCase()) {
		case "prod":
			log.info("upgradeAnnual test is not eligible to run in PROD");
			break;
		case "stage":
			default:				
			ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
			String fullName = ExcelUtility.getCellData(1, 0);
			String password = ExcelUtility.getCellData(1, 2);
			String email = TestUtility.getRandomEmail();
	
			signUp.clickSignUpForFree();
			signUp.fullName(fullName);
			Thread.sleep(500);
			log.info("New email generated " + email);
			signUp.emailSignup(email);
			Thread.sleep(500);
			signUp.passwordSignup(password);
			signUp.clickSignUpWithEmail();
			Thread.sleep(3000);
	
			upgrade.closeHelp();
			Thread.sleep(3000);
	
			myAccount.openSideBarMenu();
			Thread.sleep(1000);			
			upgrade.clickUpgrade();
			upgrade.CCDetails();
			upgrade.clickUpgradeAccount();
			Thread.sleep(5000);
			Assert.assertEquals(upgrade.yourPlan(), "PRO PLAN");
			upgrade.closeUpgradeScreen();
			Thread.sleep(3000);
	
			myAccount.clickSignOut();
			log.info("upgradeAnnual - Passed"); 
		}
	}

	@Test(groups = { "positive", "Smoke" })
	public void keepProAccount() throws Exception {
		log.info("Running keepProAccount Test case");

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String userEmail = ExcelUtility.getCellData(20, 1);
		String userPassword = ExcelUtility.getCellData(20, 2);

		signIn.clickSigninLink();
		signIn.userEmail(userEmail);
		Thread.sleep(500);
		signIn.userPassword(userPassword);
		signIn.clickSignInWithEmail();
		Thread.sleep(3000);

		myAccount.openSideBarMenu();
		Thread.sleep(2000);		
		Assert.assertEquals(upgrade.yourPlan(), "PRO PLAN");
		WebElement toElement = driver.findElement(By.cssSelector(".upgrade"));
		Thread.sleep(2000);
		action.click(toElement).perform();
		Thread.sleep(2000);
		upgrade.clickKeepProButton();
		Thread.sleep(1000);
		
		myAccount.openSideBarMenu();
		Thread.sleep(1000);
		Assert.assertEquals(upgrade.yourPlan(), "PRO PLAN");
		
		myAccount.clickSignOut();
		log.info("keepProAccount - Passed"); 
	}

	@Parameters({ "browserType", "env" })	
	@Test(groups = "positive")
	public void downgradeAccount(String browser1, String envir1) throws Exception {
		log.info("Running downgradeAccount Test case");

		switch (envir1.toLowerCase()) {
		case "prod":
			log.info("downgradeAccount test is not eligible to run in PROD");
			break;
		case "stage":
			default:	
			ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
			String fullName = ExcelUtility.getCellData(1, 0);
			String password = ExcelUtility.getCellData(1, 2);
			String email = "DWNGRD" + TestUtility.getShortRandomEmail();
	
			signUp.clickSignUpForFree();
			signUp.fullName(fullName);
			log.info("New email generated " + email);
			signUp.emailSignup(email);
			Thread.sleep(1000);
			signUp.passwordSignup(password);
			signUp.clickSignUpWithEmail();
			Thread.sleep(3000);
	
			upgrade.closeHelp();
			Thread.sleep(3000);
			myAccount.openSideBarMenu();
			Thread.sleep(1000);
			
			upgrade.clickUpgrade();
			upgrade.CCDetails();
			upgrade.chooseMonthlyBilling();
			upgrade.clickUpgradeAccount();
			Thread.sleep(5000);
			Assert.assertEquals(upgrade.yourPlan(), "PRO PLAN");
			upgrade.closeUpgradeScreen();
			WebElement toElement = driver.findElement(By.cssSelector(".upgrade"));
			Thread.sleep(2000);
	
			action.click(toElement).perform();
			Thread.sleep(2000);
			upgrade.clickGoLite();
			upgrade.clickWorksGreatButton();
			upgrade.enterFeedback();
			upgrade.clickSubmitButton();
			Thread.sleep(5000);
			myAccount.openSideBarMenu();
			Thread.sleep(1000);
			
			Thread.sleep(2000);
			Assert.assertFalse(myAccount.isEditBillingPresent());
			
			myAccount.clickSignOut();
			log.info("downgradeAccount - Passed"); 
		}
	}	
		
	@AfterMethod
	public void afterClass(ITestResult testResult) throws IOException {
		utilities.FullScreenCapture.takeScreenShotOnFailure(testResult, driver);
		driver.close();
	}
}
