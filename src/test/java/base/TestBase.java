package base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {
	public static WebDriver driver=null;
	public static Properties prop=null;
	public static ExtentReports reports;
	public static ExtentSparkReporter spark;
	public static ExtentTest extentTest;
	
	public TestBase() {
		String path=System.getProperty("user.dir")+"//src//test//resources//configFiles//config.properties";
		prop=new Properties();
		try {
			FileInputStream obtained=new FileInputStream(path);
			prop.load(obtained);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void initialize() {
		reports=new ExtentReports();
		spark=new ExtentSparkReporter("target//ProductStore.html");
		reports.attachReporter(spark);
		String strBrowser=prop.getProperty("browser");
//		if(strBrowser.equalsIgnoreCase("chrome")) {
//			WebDriverManager.chromedriver().setup();
//			ChromeOptions options=new ChromeOptions();
//			options.addArguments("--remote-allow-origins=*");
//			driver=new ChromeDriver(options);
//		}
		if(strBrowser.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			driver=new EdgeDriver();
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofMinutes(1));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofMinutes(1));
		driver.get(prop.getProperty("url"));
	}
	
	public static void finial(){
		driver.close();
	}

}
