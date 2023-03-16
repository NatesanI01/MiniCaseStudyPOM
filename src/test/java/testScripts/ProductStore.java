package testScripts;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import base.TestBase;
import base.Utility;
import pages.CartPage;
import pages.HomePage;
import pages.LoginPage;
import pages.PurchasePage;

public class ProductStore extends TestBase{
  HomePage homePage;
  LoginPage loginPage;
  CartPage cartPage;
  PurchasePage purchasePage;
  
  @BeforeTest
  public void setup() {
	  initialize();
  }
  @Test(priority=1)
  public void login() throws InterruptedException {
	  homePage=new HomePage();
	  loginPage=homePage.login();
	  loginPage.loginPage(prop.getProperty("username"), prop.getProperty("pwd"));
	  Assert.assertEquals(loginPage.successMsg.getText(), "Welcome glass");  
  }
  
  @Test(priority=2, dataProvider="search")
  public void addItem(String catagory,String item) {
	  homePage=homePage.addCart(catagory, item);
	  cartPage=homePage.navigateCart();
	  boolean flag=false;
	  for(WebElement test:homePage.items) {
		  if(test.getText().equalsIgnoreCase(item)) {
			  Assert.assertEquals(test.getText(), item);
			  flag=true;
		  }
	  }
	  Assert.assertTrue(flag);  
  }
  
  @DataProvider(name="search")
  public Object[][] getData() throws CsvValidationException, IOException{
	  String path=System.getProperty("user.dir")+"//src//test//resources//testdata//productstore.csv";
	  CSVReader reader=new CSVReader(new FileReader(path));
	  String[] cols;
	  ArrayList<Object> dataList=new ArrayList<Object>();
	  while((cols=reader.readNext())!=null) {
		  Object[] records= {cols[0],cols[1]};
		  dataList.add(records);
	  }
	  return dataList.toArray(new Object[dataList.size()][]);
  }
  
  @Test(priority=3)
  public void deleteItemcart() throws InterruptedException{
	  homePage=new HomePage();
	  cartPage=homePage.navigateCart();
	  cartPage.deleteItemTest();
	  Assert.assertNotEquals(cartPage.beforePrice, cartPage.afterPrice);
  }
  
  @Test(priority=4)
  public void placeOrder() throws InterruptedException {
	  cartPage=new CartPage();
	  purchasePage=cartPage.placeorder();
	  purchasePage.purchase();
	  Assert.assertEquals(purchasePage.ordermsg.getText(), "Thank you for your purchase!");
	  Thread.sleep(1000);
	  purchasePage.successMsg.click();
//	  Thread.sleep(1000);
  }
  
  @AfterMethod
  public void finishExtent(ITestResult results) throws IOException {
	  if(ITestResult.FAILURE== results.getStatus()) {
		  extentTest.log(Status.FAIL, results.getThrowable().getMessage());
		  String path=Utility.getScreenshotPath(driver);
		  extentTest.addScreenCaptureFromPath(path);
	  }
  }
  
  @AfterTest
  public void finish(){
	  finial();
	  reports.flush();
  }

}
