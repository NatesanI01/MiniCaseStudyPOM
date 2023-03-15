package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.TestBase;

public class LoginPage extends TestBase{
	@FindBy(xpath = "//input[@id='loginusername']")
	WebElement username;
	
	@FindBy(xpath = "//input[@id='loginpassword']")
	WebElement password;
	
	@FindBy(xpath = "//button[contains(text(),'Log in')]")
	WebElement loginBtn;
	
	@FindBy(xpath = "//a[contains(text(),'Welcome glass')]")
	public WebElement successMsg;
	
	public LoginPage() {
		PageFactory.initElements(driver, this);
	}
	
	public void loginPage(String uname,String pwd) throws InterruptedException {
		extentTest=reports.createTest("Login Page Test");
		username.sendKeys(uname);
		Thread.sleep(1000);
		password.sendKeys(pwd);
		loginBtn.click();
	}

}
