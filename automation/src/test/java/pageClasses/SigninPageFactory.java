package pageClasses;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SigninPageFactory {

	WebDriver driver;

	// Xpath for Sign-up link on home page
	@FindBy(css = ".sign-in")
	WebElement SignIn;

	// Click on Sign-in link on Home page
	public void clickSigninLink() {
		SignIn.click();
	}

	// Email address location
	@FindBy(css = "form[name=\'loginForm\'] > div.field > input[name=\'email\'")
	WebElement email;

	// User email to sign In
	public void userEmail(String userEmail) {
		email.sendKeys(userEmail);
	}

	// Password field location
	@FindBy(css = "form[name=\'loginForm\'] > div.field > input[name=\'password'")
	WebElement password;

	// User password for sign In
	public void userPassword(String userPassword) {
		password.sendKeys(userPassword);
	}

	// Sign up with email button location
	// @FindBy(xpath =
	// "id('ng-app')/body/div/div[2]/div[1]/div/div[2]/div/div[1]/button")
	@FindBy(css = ".login-form > button:nth-child(3)")
	WebElement signInWithEmail;

	// CLick on Sign In with email button
	public void clickSignInWithEmail() {
		signInWithEmail.click();
	}

	public SigninPageFactory(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// Sign-up from Hightail oAuth on home page
	//@FindBy(css = ".signup > div:nth-child(3) > button:nth-child(3)")
	@FindBy(css = "div.login.open > div.content > button.auth.hightail")
	WebElement oAuthHtl;

	// Click on Hightail oAuth button
	public void clickoAuthHtl() {
		oAuthHtl.click();
	}

	// Hightail oAuth email
	@FindBy(css = "#ysi_email")
	WebElement oAuthemail;

	public void EnteroAuthHtlEmail(String oAuthHtlEmail) {
		oAuthemail.sendKeys(oAuthHtlEmail);
	}

	public void clickoAuthHtlEmail() {
		oAuthemail.click();
	}

	// Hightail oAuth password
	@FindBy(css = "#ysi_password")
	WebElement oAuthpwd;

	public void EnteroAuthHtlpwd(String oAuthHtlpwd) {
		oAuthpwd.sendKeys(oAuthHtlpwd);
	}

	// Hightail oAuth SignIn button
	@FindBy(css = ".btnLogin")
	WebElement oAuthsignIn;

	public void clickoAuthHtlsubmit() {
		oAuthsignIn.click();
	}

	// Hightail oAuth accept button
	@FindBy(css = "#btnAccept")
	WebElement oAuthAccept;

	public void clickoAuthHtlAccept() {
//		oAuthAccept.click();
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(oAuthAccept)).click();
	}

	// Error for entering invalid credentials in oAuth
	@FindBy(css = "#loginServerError")
	WebElement oAuthSigninError;

	// Check if error exist on oAuth page
	public boolean oAuthSigninErrorExist() {
		oAuthSigninError.isDisplayed();
		return true;
	}

	// Error for empty email Id in oAuth
	@FindBy(css = "#validationEmailMessage")
	WebElement oAuthNoemailError;

	// Check if error exist on oAuth page
	public boolean oAuthNoemailErrorExist() {
		oAuthNoemailError.isDisplayed();
		return true;
	}

	// Dropbox oAuth email
	//@FindBy(css = "id^='pyx'")
	@FindBy(xpath = ".//*[contains(text(), 'Email')]" )
	WebElement dropboxOauthEmail;
	public void EnterDropboxEmail(String dropboxEmail) {
		dropboxOauthEmail.sendKeys(dropboxEmail);
	}
	public void clickDropboxOauthEmail() {
		dropboxOauthEmail.click();
	}

	// Dropbox oAuth password
	@FindBy(xpath = ".//*[contains(text(), 'Password')]" )	
	WebElement dropboxOauthpwd;
	public void EnterDropboxPwd(String dropboxPwd) {
		dropboxOauthpwd.sendKeys(dropboxPwd);
	}

	// Signin in Dropbox Oauth
	@FindBy(css = "button.login-button:nth-child(2)")
	WebElement dropboxSignIn;	
	// click on dropbox SignIn
	public void clickDropboxSignIn() {
		dropboxSignIn.click();
	}

	// Accept Dropbox agreement -"Allow"
	@FindBy(css = "button.auth-button:nth-child(4)")
	WebElement dropboxAllow;	
	// click on dropbox Allow button
	public void clickDropboxAllow() {
		dropboxAllow.click();
	}

	// SignIn in Add Files"
	@FindBy(css = ".not-connected > button:nth-child(2)	")
	WebElement addFilesSignIn;	
	// click on SingIn button on Add Files dialog
	public void clickAddFilesSignIn() {
		addFilesSignIn.click();
	}
	
	
	// Google oAuth email
	@FindBy(css = "#Email")
	WebElement googleOauthEmail;
	public void EnterGoogleEmail(String googleEmail) {
		googleOauthEmail.sendKeys(googleEmail);
	}
	public void clickGoogleOauthEmail() {
		googleOauthEmail.click();
	}

	// Google oAuth Next button
	@FindBy(css = "#next")
	WebElement googleOauthNextBtn;
	public void clickgoogleOauthNextBtn() {
		googleOauthNextBtn.click();
	}
	
	// Google oAuth password
	@FindBy(css = "#Passwd")
	WebElement googleOauthpwd;
	public void EnterGooglePwd(String googlePwd) {
		googleOauthpwd.sendKeys(googlePwd);
	}

	// Signin in Google Oauth
	@FindBy(css = "#signIn")
	WebElement googleSignIn;	
	// click on google SignIn
	public void clickGoogleSignIn() {
		googleSignIn.click();
	}

	// Accept google agreement -"Allow"
	@FindBy(css = "#submit_approve_access")
	WebElement googleAllow;	
	// click on google Allow button
	public void clickGoogleAllow() {
		googleAllow.click();
	}	
	
	// Microsoft One Drive oAuth email
	@FindBy(css = "#i0116")
	WebElement microsoftOauthEmail;
	public void EnterMicrosoftEmail(String microsoftEmail) {
		microsoftOauthEmail.sendKeys(microsoftEmail);
	}
	public void clickMicrosoftOauthEmail() {
		microsoftOauthEmail.click();
	}
	
	// Microsoft oAuth password
	@FindBy(css = "#i0118")
	WebElement microsoftOauthpwd;
	public void EnterMicrosoftPwd(String microsoftPwd) {
		microsoftOauthpwd.sendKeys(microsoftPwd);
	}

	// Sign-in in Microsoft Oauth
	@FindBy(css = "#idSIButton9")
	WebElement microsoftSignIn;	
	// click on Microsoft SignIndiv.content:nth-child(2) > div:nth-child(1) > form:nth-child(2) > div:nth-child(2) > input:nth-child(1)
	public void clickMicrosoftSignIn() {
		microsoftSignIn.click();
	}

	// Sign-in email to request access 
	@FindBy(css = "div.content:nth-child(2) > div:nth-child(1) > form:nth-child(2) > div:nth-child(1) > input:nth-child(1)")
	WebElement requestAccessSignInEmail;	
	// click on request access SignIn Email
	public void EnterRequestAccessSignInEmail(String email) {
		requestAccessSignInEmail.sendKeys(email);
	}	
	
	// Sign-in password to request access 
	@FindBy(css = "div.content:nth-child(2) > div:nth-child(1) > form:nth-child(2) > div:nth-child(2) > input:nth-child(1)")
	WebElement requestAccessSignInPwd;	
	// click on request access SignIn password
	public void EnterRequestAccessSignInPwd(String pwd) {
		requestAccessSignInPwd.sendKeys(pwd);
	}	
	
	// Sign-in button on request access sign-in page
	@FindBy(css = "div.content:nth-child(2) > div:nth-child(1) > button:nth-child(3)")
	WebElement requestSignInBtn;	
	// Sign-in button on request access sign-in page
	public void clickrequestSignInBtn() {
		requestSignInBtn.click();
	}	

	// Sign-in Email in VRI page 
	@FindBy(css = ".login-form > form:nth-child(2) > div:nth-child(1) > input:nth-child(1)")
	WebElement vriEmail;	
	// Enter Sign-in Email in VRI page
	public void EnterVriEmail(String email) {
		vriEmail.sendKeys(email);
	}			
	
	// Sign-in Password in VRI page 
	@FindBy(css = "form.ng-dirty > div:nth-child(2) > input:nth-child(1)")	
	WebElement vriPwd;	
	// Enter Sign-in Password in VRI page 
	public void EnterVriPwd(String pwd) {
		vriPwd.sendKeys(pwd);
	}			

	//Error on VRI page for incorrect VRI
	@FindBy(css = "body > div > div.ht-receive.ng-scope > div.anim-fade.ht-login.ng-isolate-scope.Login > div.login.open > div > div.login-form > div > p")
	WebElement vriSigninError;
	// Check if error exist on VRI page
	public String vriSigninErrorExist() {
		return vriSigninError.getText();
	}

	// Sign-in email in approval file page
	@FindBy(css = "div.content:nth-child(2) > div:nth-child(1) > form:nth-child(2) > div:nth-child(1) > input:nth-child(1)")
	WebElement approvalFileSignInEmail;	
	// click on request access SignIn Email
	public void enterApprovalFileSignInEmail(String email) {
		approvalFileSignInEmail.sendKeys(email);
	}	
	
	// Sign-in password in approval file page 
	@FindBy(css = "div.content:nth-child(2) > div:nth-child(1) > form:nth-child(2) > div:nth-child(2) > input:nth-child(1)")
	WebElement approvalFileSignInPwd;	
	// click on request access SignIn password
	public void enterApprovalFileSignInPwd(String pwd) {
		approvalFileSignInPwd.sendKeys(pwd);
	}	

	// Sign-in button on approval file sign-in page
	@FindBy(css = "div.content:nth-child(2) > div:nth-child(1) > button:nth-child(3)")
	WebElement approvalSignInBtn;	
	// Sign-in button on request access sign-in page
	public void clickapprovalSignInBtn() {
		approvalSignInBtn.click();
	}	

	//Error in recipient approval sign-in page
	@FindBy(css="div.content:nth-child(2) > div:nth-child(1) > div:nth-child(1) > p:nth-child(1)")
	WebElement errorApprovalSignIn;	
	public String ErrorApprovalSignIn() {
		 return errorApprovalSignIn.getText();
	}	
	
	/********************************************************************
	 * Corporate Sign In
	 *********************************************************************/
	// Find corporate sign in link
	@FindBy(css=".corp-login>a")
	WebElement corporateSignInLink;
	
	// Click corporate sign in link
	public void corporateSignIn() {
		corporateSignInLink.click();
	}
	
	// Find corporate email text box
	@FindBy(css=".ng-pristine.ng-untouched.ng-valid")
	WebElement corporateEmailTextBox;
	
	// Enter coporate email
	public void enterCorporateEmail(String email) {
		corporateEmailTextBox.sendKeys(email);
	}
	
	// Find Next button
	@FindBy(css=".next.button")
	WebElement nextButton;
	
	// Click Next
	public void clickNextButton() {
		nextButton.click();
	}
	
	// Find Okta username text box
	@FindBy(css="#user-signin")
	WebElement oktaUsername;
	
	// Enter Okta username
	public void enterOktaUsername(String email) {
		oktaUsername.sendKeys(email);
	}
	
	// Find Okta password text box
	@FindBy(css="#pass-signin")
	WebElement oktaPassword;
	
	// Enter Okta password
	public void enterOktaPassword(String password) {
		oktaPassword.sendKeys(password);
	}
	
	// Find Okta Sign In button
	@FindBy(css="#signin-button")
	WebElement oktaSignInButton;
	
	// Click Sign In
	public void clickOktaSignInBtn() {
		oktaSignInButton.click();
	}
}
