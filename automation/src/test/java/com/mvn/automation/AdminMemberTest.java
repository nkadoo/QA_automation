package com.mvn.automation;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pageClasses.SigninPageFactory;
import pageClasses.MyAccountPageFactory;
import pageClasses.AdminPageFactory;
import utilities.Constants;
import utilities.ExcelUtility;
import utilities.TestUtility;

public class AdminMemberTest {
	private WebDriver driver;
	SigninPageFactory signIn;
	MyAccountPageFactory myAccount;
	AdminPageFactory admin;
	static Logger log = Logger.getLogger(AdminMemberTest.class);
	
	// Use this class for mouse-over actions
	Actions action;

	String subUserName = "Sub User";
	String subUserEmail = TestUtility.getRandomEmail();
	
	@Parameters({"browserType", "env"})
	@BeforeMethod
	public void setUp(String browser, String envir) throws Exception {
		PropertyConfigurator.configure("log4j.properties");

		switch (envir.toLowerCase()) {
			case "prod":
				driver = TestUtility.setupDriver(browser, "admin_prod");
				Assert.assertNotNull("Driver can not be null", driver);
				break;
			case "stage":
			default:
				driver = TestUtility.setupDriver(browser, "admin");
				Assert.assertNotNull("Driver can not be null", driver);
				break;
			}
		
		myAccount = new MyAccountPageFactory(driver);
		signIn = new SigninPageFactory(driver);
		admin = new AdminPageFactory(driver);
	}
	
	@Test(groups = { "positive", "Smoke" })
	public void AdminMemberAddSearchDelete() throws Exception {
		log.info("Running AdminMemberAddSearchDelete Test case");

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String userEmail = ExcelUtility.getCellData(7, 1);
		String userPassword = ExcelUtility.getCellData(7, 2);

		// SignIn with Admin account
		admin.enterAdminEmail(userEmail);
		admin.enterAdminPwd(userPassword);
		admin.clickAdminSigninBtn();
		Thread.sleep(3000);
		Assert.assertEquals(admin.VerifyAdminHeader(), "MEMBERS");

		//Add New Member
		admin.clickAddMembers();
		admin.enterMemberName(subUserName);
		
		admin.enterMemberEmail(subUserEmail);
		Thread.sleep(2000);
		admin.clickAddMembersBtn();
		Thread.sleep(2000);

		Assert.assertEquals(admin.VerifyConfMsg(), "Everything look okay?");
		admin.clickaddMemberConfBtn();
		Thread.sleep(2000);
		Assert.assertEquals(admin.VerifySuccessMsg(), "1 members added");
		Thread.sleep(2000);
		
		//Search for added user 
		admin.clickSearchBox();
		admin.enterSearchBox(subUserEmail);
		Thread.sleep(2000);

		//Click on action icon and cancel invite
		admin.clickActionIcon();
		Thread.sleep(1000);
		admin.clickActionCancel();
		Thread.sleep(1000);
		Assert.assertEquals(admin.VerifyNoUserFnd(), "No users found");

		//Sign-out from Admin
		admin.clickAdminSignout();		
		log.info("AdminMemberAddSearchDelete - Passed"); 
	}
		
	@Test(groups = { "positive" })
	public void AdminMemberActivity() throws Exception {
		log.info("Running AdminMemberActivity Test case");

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String userEmail = ExcelUtility.getCellData(7, 1);
		String userPassword = ExcelUtility.getCellData(7, 2);

		// SignIn with Admin account
		admin.enterAdminEmail(userEmail);
		admin.enterAdminPwd(userPassword);
		admin.clickAdminSigninBtn();
		Thread.sleep(3000);
		Assert.assertEquals(admin.VerifyAdminHeaderActivity(), "ACTIVITY");
		
		//Click on Activity tab and Verify is Activity page is open
		admin.clickAdminHeaderActivity();		
		Thread.sleep(1000);
		Assert.assertEquals(admin.VerifyActivityTab(), "CREATED BY");

		//Sign-out from Admin
		admin.clickAdminSignout();		
		log.info("AdminMemberActivity - Passed"); 
	}

	@Test(groups = { "negative" })
	public void AdminMemberSearchNotFound() throws Exception {
		log.info("Running AdminMemberSearchNotFound Test case");

		ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");
		String userEmail = ExcelUtility.getCellData(7, 1);
		String userPassword = ExcelUtility.getCellData(7, 2);

		// SignIn with Admin account
		admin.enterAdminEmail(userEmail);
		admin.enterAdminPwd(userPassword);
		admin.clickAdminSigninBtn();
		Thread.sleep(3000);
		Assert.assertEquals(admin.VerifyAdminHeader(), "MEMBERS");
		
		//Search for invalid user 
		admin.clickSearchBox();
		admin.enterSearchBox("invalid@yopmail.com");
		Thread.sleep(2000);

		//Verify no user is found
		Assert.assertEquals(admin.VerifyNoUserFnd(), "No users found");

		//Sign-out from Admin
		admin.clickAdminSignout();		
		log.info("AdminMemberSearchNotFound - Passed"); 
	}

	@AfterMethod
	public void afterClass(ITestResult testResult) throws IOException {
		utilities.FullScreenCapture.takeScreenShotOnFailure(testResult, driver);
		driver.close();
	}
}
