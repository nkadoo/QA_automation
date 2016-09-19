package utilities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import utilities.Constants;

public class TestUtility {

	public static WebDriver setupDriver(String browser, String envir) {
		WebDriver driver = getDriver(browser);
		driver.get(setupEnvironment(envir));
		maximizeWindow(driver);
		return driver;		
	}

	private static final String FIREFOX = "firefox";
	private static final String CHROME = "chrome";
	private static final String SAFARI = "safari";

	public static WebDriver getDriver(String browser) {
		switch (browser.toLowerCase()) {
		case CHROME:
			System.setProperty("webdriver.chrome.driver",
					Constants.Chrome_driver_location);
			return new ChromeDriver();
		case SAFARI:
			return new SafariDriver();
		case FIREFOX:
			return new FirefoxDriver();
		default:
			return new FirefoxDriver();
		}
	}

	public static String setupEnvironment(String envir) {
		switch (envir.toLowerCase()) {
		case "prod":
			return Constants.PROD_URL;
		case "admin":
			return Constants.ADMIN_URL;
		case "admin_prod":
			return Constants.ADMIN_PROD_URL;
		case "stage":
		default:
			return Constants.URL;	
		}	
	}

	public static WebDriver maximizeWindow(WebDriver driver) {
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}

	public static String getDate() {
		return new SimpleDateFormat("MMddyyyyHHmmss").format(new Date());
	}

	public static String getRandomEmail() {
		return "sel_" + getDate() + "@yopmail.com";

	}
	
	public static String getShortRandomEmail() {
		String shortDate = new SimpleDateFormat("MMddyyss").format(new Date());
		return shortDate + "@yopmail.com";
	}

}
