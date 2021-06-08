package com.qa;

import java.io.File;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeSuite;

import com.qa.utils.TestUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.MobileCapabilityType;

public class BaseTest {
	
	//0-> Parallel execution on two android device with on appium instance
	
	//1->Make the global variable thread safe
	protected static ThreadLocal<AppiumDriver<AndroidElement>> driver = new ThreadLocal<AppiumDriver<AndroidElement>>();
	protected static ThreadLocal<Properties>prop = new ThreadLocal<Properties>();
	protected static ThreadLocal<String> dateTime = new ThreadLocal<String>();
	protected static ThreadLocal<String> platform = new ThreadLocal<String>();
	
	static JSONObject jsonObj;
	public BaseTest() {
		
		//3->So what we need to do, we need to remove class level references and instead of that need to use getter and setter methods, where we are using this global parameters.
		//4-> So this need to be changed->PageFactory.initElements(new AppiumFieldDecorator(driver),this);
		PageFactory.initElements(new AppiumFieldDecorator(getDriver()),this);
	}
	
	//2-> Getter and setter for all global parameters created 
	
	public String getPlatform()
	{
		return platform.get();
	}
	public void setPlatform(String platfrom2)
	{
		platform.set(platfrom2);
	}
	public AppiumDriver<AndroidElement> getDriver()
	{
		return driver.get();
	}
	public void setDriver(AppiumDriver<AndroidElement> driver2)
	{
		driver.set(driver2);
	}
	public Properties getprops()
	{
		return prop.get();
	}
	public void setprops(Properties prop2)
	{
		prop.set(prop2);
	}
	public String getDateTime()
	{
		return dateTime.get();
	}
	public void setDateTime(String dateTime2)
	{
		dateTime.set(dateTime2);
	}
	
	@BeforeSuite
	public void setUp() throws Exception
	{
		loadFiles();
		setDateTime(TestUtils.getDateTime());
	}
	
	public static JSONObject loadFiles() throws Exception
	{
		FileInputStream file = null;
		try {
			file = new FileInputStream(System.getProperty("user.dir")+"\\src\\data\\dataFile.json");
			JSONTokener tokener = new JSONTokener(file);
			jsonObj = new JSONObject(tokener);
			return jsonObj;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			if(file!=null)
			{
				file.close();
			}
		}
	}
	
	public AppiumDriver<AndroidElement> setAndroidDriver(
			String platformName,
			String deviceName,
			String platformVersion,
			String emulator,
			String udidForDevice1,
			String udidForDevice2,
			String udidForSimulator) throws MalformedURLException
	{
		try {
			setPlatform(platformName);
			FileInputStream fis=null;
			Properties prop = new Properties();
			AppiumDriver<AndroidElement> driver;
			prop = new Properties();
			fis = new FileInputStream(System.getProperty("user.dir")+File.separator+"src"+File.separator+"config.properties");
			prop.load(fis);
			setprops(prop);
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("appPackage",prop.getProperty("androidAppPackage"));
			capabilities.setCapability("appActivity",prop.getProperty("androidAppActivity"));
			capabilities.setCapability("app", "F:\\Projects\\AppiumTDDFramework\\src\\app\\Android.SauceLabs.Mobile.Sample.app.2.2.1.apk");
			switch (platformName) {
			case "AndroidWifi":
				/*capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);*/
				capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
				capabilities.setCapability(MobileCapabilityType.UDID,udidForDevice1);
				driver = new AndroidDriver<AndroidElement>(new URL(prop.getProperty("appiumURL")),capabilities);
				setDriver(driver);
				break;

			case "AndroidUSB":
				/*capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);*/
				capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
				capabilities.setCapability(MobileCapabilityType.UDID,udidForDevice2);
				driver = new AndroidDriver<AndroidElement>(new URL(prop.getProperty("appiumURL")),capabilities);
				setDriver(driver);
				break;
			default:
			throw new Exception("Invalid plateform");		
			}
			/*if(emulator.equalsIgnoreCase("true"))
			{
				capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION,platformVersion);
				capabilities.setCapability(MobileCapabilityType.UDID,udidForSimulator);  // Emulator ka UDID jana ho to cmd open karke usme adb devices type kar do bus
				capabilities.setCapability("avd", "Pixel_3_API_30");
				capabilities.setCapability("avdLauchTimeout", 180000);
			}
			else
			{
				
			}*/
			//driver = new AndroidDriver<AndroidElement>(new URL(prop.getProperty("appiumURL")),capabilities);
			//setDriver(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void waitForVisibility(MobileElement element, int timeInSeconds)
	{
		WebDriverWait wait = new WebDriverWait(getDriver(), timeInSeconds);
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	public void click(MobileElement element)
	{
		waitForVisibility(element,10);
		element.click(); 
	}
	public void sendKeys(MobileElement element, String key)
	{
		waitForVisibility(element, 10);
		element.sendKeys(key);
	}
	public String getAttribute(MobileElement element,String attribute)
	{
		waitForVisibility(element, 10);
		return element.getAttribute(attribute);
	}
	public void clear(MobileElement element)
	{
		waitForVisibility(element, 10);
		element.clear();
	}
	public String getText(MobileElement element)
	{
		waitForVisibility(element, 10);
		return element.getText();
	}
}
