package com.mvn.automation;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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

import pageClasses.HomePageFactory;
import utilities.Constants;
import utilities.TestUtility;

public class HomePageLinksTest {

	private WebDriver driver;
	private String baseUrl;
	HomePageFactory home;
	static Logger log = Logger.getLogger(HomePageLinksTest.class);

	@Parameters({"browserType", "env"})
	@BeforeMethod
	public void setUp(String browser, String envir) throws Exception {
		PropertyConfigurator.configure("log4j.properties");

		driver = TestUtility.setupDriver(browser, envir);
		Assert.assertNotNull("Driver can not be null", driver);
		
		baseUrl = Constants.URL;
		//If you want to check URl other than Spaces, use below URL
//		baseUrl = "www.sqc.hightail.com";
		home = new HomePageFactory();
	}

	@Test
	public void findHomePageLinks() {
		log.info("Running Find Links on Home page test case");
		driver.get(baseUrl);
		home.navigateToSpacesHomePage(driver);
		List<WebElement> linksList = clickableLinks(driver);
		for (WebElement link : linksList) {
			String href = link.getAttribute("href");
			try {
				System.out.println("URL " + href + " returned " + linkStatus(new URL(href)));
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			log.info("findHomePageLinks test - Passed");
		}
	}

	public static List<WebElement> clickableLinks(WebDriver driver) {
		List<WebElement> linksToClick = new ArrayList<WebElement>();
		List<WebElement> elements = driver.findElements(By.tagName("a"));
		elements.addAll(driver.findElements(By.tagName("img")));

		for (WebElement e : elements) {
			if (e.getAttribute("href") != null) {
				linksToClick.add(e);
			}
		}
		return linksToClick;
	}

	public static String linkStatus(URL url) {
		try {
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			http.connect();
			String responseMessage = http.getResponseMessage();
			http.disconnect();
			return responseMessage;

		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
	@AfterMethod
	public void afterClass(ITestResult testResult) throws IOException {
		utilities.FullScreenCapture.takeScreenShotOnFailure(testResult, driver);
		driver.close();

	}
}