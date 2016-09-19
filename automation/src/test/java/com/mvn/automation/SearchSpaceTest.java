package com.mvn.automation;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
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
import pageClasses.SearchPageFactory;
import pageClasses.SigninPageFactory;
import pageClasses.SignupPageFactory;
import utilities.Constants;
import utilities.ExcelUtility;
import utilities.TestUtility;

/**
 * Created by neeraj.kadoo on 9/16/16.
 */
public class SearchSpaceTest {

    private WebDriver driver;
    SignupPageFactory signUp;
    CreateSpacePageFactory spaceCreate;
    SigninPageFactory signIn;
    MyAccountPageFactory myAccount;
    Actions action;
    SearchPageFactory search;
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
        search = new SearchPageFactory(driver);
        action = new Actions(driver);
    }

    @Test(groups = { "positive", "Smoke" })
    public void searchNewSpace() throws Exception {
        log.info("Running searchNewSpace testcase");
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
        String searchText = spaceCreate.getSpaceName();
        System.out.println(searchText);
        spaceCreate.uploadFiletoSpace();
        search.clickSearchButton();
        Thread.sleep(3000);
        search.enterSearchText(searchText);
        search.submitSearch();
        Thread.sleep(3000);
        search.getResults();
    }

    @AfterMethod
    public void afterClass(ITestResult testResult) throws IOException {
        utilities.FullScreenCapture.takeScreenShotOnFailure(testResult, driver);
        driver.close();
    }
}
