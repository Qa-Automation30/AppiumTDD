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

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.MobileCapabilityType;

public class BaseTest {
	protected static AppiumDriver<AndroidElement> driver;
	protected static Properties prop;
	FileInputStream fis;
	static JSONObject jsonObj;
	public BaseTest() {
		PageFactory.initElements(new AppiumFieldDecorator(driver),this);
	}
	
	@BeforeSuite
	public void setUp() throws Exception
	{
		loadFiles();
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
			String udidForDevice,
			String udidForSimulator) throws MalformedURLException
	{
		System.out.println(platformName);
		System.out.println(deviceName);
		System.out.println(platformVersion);
		System.out.println(udidForDevice);
		System.out.println(emulator);
		System.out.println(udidForSimulator);
		try {
			prop = new Properties();
			fis = new FileInputStream(System.getProperty("user.dir")+File.separator+"src"+File.separator+"config.properties");
			prop.load(fis);
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
			capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
			capabilities.setCapability("appPackage",prop.getProperty("androidAppPackage"));
			capabilities.setCapability("appActivity",prop.getProperty("androidAppActivity"));
			capabilities.setCapability("app", "F:\\Projects\\AppiumTDDFramework\\src\\app\\Android.SauceLabs.Mobile.Sample.app.2.2.1.apk");
			if(emulator.equalsIgnoreCase("true"))
			{
				capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION,platformVersion);
				capabilities.setCapability(MobileCapabilityType.UDID,udidForSimulator);  // Emulator ka UDID jana ho to cmd open karke usme adb devices type kar do bus
				capabilities.setCapability("avd", "Pixel_3_API_30");
				capabilities.setCapability("avdLauchTimeout", 180000);
			}
			else
			{
				capabilities.setCapability(MobileCapabilityType.UDID,udidForDevice);
			}
			driver = new AndroidDriver<AndroidElement>(new URL(prop.getProperty("appiumURL")),capabilities);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return driver;
	}
	
	public void waitForVisibility(MobileElement element, int timeInSeconds)
	{
		WebDriverWait wait = new WebDriverWait(driver, timeInSeconds);
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
