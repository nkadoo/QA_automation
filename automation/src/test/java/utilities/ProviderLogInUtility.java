/*
 *  Utility to handle logging into external storage providers.  
 */

package utilities;

import java.util.Set;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ProviderLogInUtility {
	
	
	static Logger log = Logger.getLogger(ProviderLogInUtility.class);
	
	public static class HightailOAuthElements {
		public static WebElement hightailEmailField(WebDriver driver) {
			WebElement element = null;
			element = driver.findElement(By.cssSelector("input#ysi_email"));
			return element;
		}
		
		public static WebElement hightailPswdField(WebDriver driver) {
			WebElement element = null;
			element = driver.findElement(By.cssSelector("input#ysi_password"));
			return element;
		}
		
		public static WebElement hightailSignInBtn(WebDriver driver) {
			WebElement element = null;
			element = driver.findElement(By.cssSelector("button.btnLogin"));
			return element;
		}
		
		public static WebElement hightailOAuthAcceptBtn(WebDriver driver) {
			WebElement element= null;
			element = driver.findElement(By.cssSelector("div#btnAccept"));
			return element;
		}
	}

	public static class DropBoxOAuthElements {
		public static WebElement dropBoxEmailField(WebDriver driver){
			WebElement element = null;
			element = driver.findElement(By.cssSelector("div.text-input-wrapper >input[name=login_email]"));
			return element;
		}
		
		public static WebElement dropBoxPswdField(WebDriver driver){
			WebElement element = null;
			element = driver.findElement(By.cssSelector("div.text-input-wrapper >input[name=login_password]"));
			return element;
		}
		
		public static WebElement dropBoxSignInButton(WebDriver driver){
			WebElement element = null;
			element = driver.findElement(By.cssSelector("button.login-button.button-primary"));
			return element;
		}
		
		public static WebElement dropBoxAllowButton(WebDriver driver){
			WebElement element = null;
			element = driver.findElement(By.cssSelector("button.auth-button.button-primary"));
			return element;
		}
	}
	
	public static class GoogleOAuthElements {
		public static WebElement googleDriveEmailField(WebDriver driver){
			WebElement element = null;
			element = driver.findElement(By.cssSelector("input#Email"));
			return element;
		}
		
		public static WebElement googleDriveNextButton(WebDriver driver){
			WebElement element = null;
			element = driver.findElement(By.cssSelector("input#next"));
			return element;
		}
		
		public static WebElement googleDrivePswdField(WebDriver driver){
			WebElement element = null;
			element = driver.findElement(By.cssSelector("input#Passwd"));
			return element;
		}
		
		public static WebElement googleDriveSignInButton(WebDriver driver){
			WebElement element = null;
			element = driver.findElement(By.cssSelector("input#signIn"));
			return element;
		}
		
		public static WebElement googleDriveAllowButton(WebDriver driver){
			WebElement element = null;
			element = driver.findElement(By.cssSelector("button#submit_approve_access"));
			return element;
		}
	}
	
	public static class OneDriveOAuthElements {
		public static WebElement oneDriveEmailField(WebDriver driver) {
			WebElement element = null;
			//element = driver.findElement(By.cssSelector("div#idDiv_PWD_UsernameTb > div > input"));
			//Microsoft OAuth elements may have changed
			element = driver.findElement(By.cssSelector("input#i0116"));
			return element;
		}
		
		public static WebElement oneDrivePswdField(WebDriver driver) {
			WebElement element = null;
			//element = driver.findElement(By.cssSelector("div#idDiv_PWD_PasswordTb > div > input"));
			//Microsoft OAuth elements may have changed
			element = driver.findElement(By.cssSelector("input#i0118"));
			return element;
		}
		
		public static WebElement oneDriveSignInButton(WebDriver driver) {
			WebElement element = null;
			element = driver.findElement(By.cssSelector("input#idSIButton9"));
			return element;
		}
	}

	// Log into external service provider
	public static void logInToService(WebDriver driver, String serviceProvider)
			throws Exception {

		// Get the handle
		String parentHandle = driver.getWindowHandle();
		// Get all handles
		Set<String> handles = driver.getWindowHandles();

		for (String handle : handles) {
			if (!handle.equals(parentHandle)) {
				// Switch to OAuth window
				driver.switchTo().window(handle);

				Thread.sleep(2000);
				ExcelUtility.setExcelFile(Constants.FILE_PATH, "Sheet1");

				switch (serviceProvider.toLowerCase()) {
				case "hightail":
					String hightailEmail = ExcelUtility.getCellData(3, 1);
					String hightailPswd = ExcelUtility.getCellData(3, 2);
					
					Thread.sleep(2000);
					HightailOAuthElements.hightailEmailField(driver).sendKeys(hightailEmail);
					HightailOAuthElements.hightailPswdField(driver).sendKeys(hightailPswd);
					Thread.sleep(1000);
					HightailOAuthElements.hightailSignInBtn(driver).click();
					Thread.sleep(1000);
					HightailOAuthElements.hightailOAuthAcceptBtn(driver).click();
					
				case "dropbox":
					String dropBoxEmail = ExcelUtility.getCellData(8, 1);
					String dropBoxPswd = ExcelUtility.getCellData(8, 2);

					Thread.sleep(2000);

					DropBoxOAuthElements.dropBoxEmailField(driver).sendKeys(dropBoxEmail);

					DropBoxOAuthElements.dropBoxPswdField(driver).sendKeys(dropBoxPswd);
					Thread.sleep(1000);

					DropBoxOAuthElements.dropBoxSignInButton(driver).click();
					Thread.sleep(1000);

					DropBoxOAuthElements.dropBoxAllowButton(driver).click();
					break;

				case "googledrive":
					String googleEmail = ExcelUtility.getCellData(10, 1);
					String googlePswd = ExcelUtility.getCellData(10, 2);

					Thread.sleep(2000);

					GoogleOAuthElements.googleDriveEmailField(driver).sendKeys(googleEmail);

					GoogleOAuthElements.googleDriveNextButton(driver).click();
					Thread.sleep(1000);

					GoogleOAuthElements.googleDrivePswdField(driver).sendKeys(googlePswd);

					GoogleOAuthElements.googleDriveSignInButton(driver).click();
					Thread.sleep(1000);

					GoogleOAuthElements.googleDriveAllowButton(driver).click();
					break;

				case "onedrive":
					String oneDriveEmail = ExcelUtility.getCellData(9, 1);
					String oneDrivePswd = ExcelUtility.getCellData(9, 2);

					Thread.sleep(2000);

					OneDriveOAuthElements.oneDriveEmailField(driver).sendKeys(oneDriveEmail);

					OneDriveOAuthElements.oneDrivePswdField(driver).sendKeys(oneDrivePswd);
					Thread.sleep(1000);

					OneDriveOAuthElements.oneDriveSignInButton(driver).click();
					break;
				}

				// Switch back to parent window
				driver.switchTo().window(parentHandle);
			}
		}
	}
}
