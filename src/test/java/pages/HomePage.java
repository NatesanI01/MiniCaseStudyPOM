package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.TestBase;

public class HomePage extends TestBase {
	WebDriverWait wait;
	@FindBy(xpath="//a[contains(text(),'Log in')]")
	WebElement login;
	
	@FindBy(xpath = "//a[contains(text(),'Cart')]")
	public WebElement cartBtn;
	
	@FindBy(xpath = "//a[contains(text(),'Home')]")
	public WebElement homePage;
	
	@FindBy(xpath = "//a[contains(text(),\"Log out\")]")
	WebElement logoutBtn;
	
	@FindBy(xpath = "//a[contains(text(),'Add to cart')]")
	WebElement addcartBtn;
	
	@FindBy(xpath = "//td[2]")
	public List<WebElement> items;
	
	public HomePage() {
		PageFactory.initElements(driver,this);
	}
	
	public void home() {
		homePage.click();
	}
	public LoginPage login() {
		login.click();
		return new LoginPage();
	}
	public HomePage addCart(String catagory,String item) {
		extentTest=reports.createTest("Add Multiple Item To Cart Test");
		wait=new WebDriverWait(driver,Duration.ofMinutes(1));
		homePage.click();
		String catagoryPath="//a[text()='"+catagory+"']";
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(catagoryPath)));
		driver.findElement(By.xpath(catagoryPath)).click();
		String searchPath="//a[text()='"+item+"']";
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(searchPath)));
		driver.findElement(By.xpath(searchPath)).click();
		addcartBtn.click();
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alert=driver.switchTo().alert();
		alert.accept();
		cartBtn.click();
		return new HomePage();
	}
	public CartPage navigateCart() {
		cartBtn.click();
		return new CartPage();
	}
	public void logout() {
		logoutBtn.click();
	}
}