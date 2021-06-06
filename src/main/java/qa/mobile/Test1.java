package qa.mobile;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class Test1 {
	
	AppiumDriver<WebElement> driver;
	
	@Test(priority=1)
	public void invalidUserName() throws InterruptedException
	{
		MobileElement userName = (MobileElement) driver.findElementByAccessibilityId("test-Username");
		MobileElement password = (MobileElement) driver.findElementByAccessibilityId("test-Password");
		MobileElement loginButton = (MobileElement)driver.findElementByAccessibilityId("test-LOGIN");
		userName.sendKeys("Invalid user name");
		password.sendKeys("password");
		loginButton.click();
		MobileElement errorText = (MobileElement) driver.findElementByXPath("//android.view.ViewGroup[@content-desc=\"test-Error message\"]/android.widget.TextView");
		String actualErrorText = errorText.getAttribute("text");
		String expectedErrorText ="Username and password do not match any user in this service.";
	
		Thread.sleep(3000);
		Assert.assertEquals(actualErrorText, expectedErrorText);
		
	}
	
	@Test(priority=2)
	public void InvalidPassword() throws InterruptedException
	{
		MobileElement userName = (MobileElement) driver.findElementByAccessibilityId("test-Username");
		MobileElement password = (MobileElement) driver.findElementByAccessibilityId("test-Password");
		MobileElement loginButton = (MobileElement)driver.findElementByAccessibilityId("test-LOGIN");
		userName.sendKeys("valid user name");
		password.sendKeys("invalid password");
		loginButton.click();
		MobileElement errorText = (MobileElement) driver.findElementByXPath("//android.view.ViewGroup[@content-desc=\"test-Error message\"]/android.widget.TextView");
		String actualErrorText = errorText.getAttribute("text");
		String expectedErrorText ="Username and password do not match any user in this service.";
		
		Thread.sleep(3000);
		Assert.assertEquals(actualErrorText, expectedErrorText);
		
	}
	@Test(priority=3)
	public void successFulLogin() throws InterruptedException
	{
		MobileElement userName = (MobileElement) driver.findElementByAccessibilityId("test-Username");
		MobileElement password = (MobileElement) driver.findElementByAccessibilityId("test-Password");
		MobileElement loginButton = (MobileElement)driver.findElementByAccessibilityId("test-LOGIN");
		userName.sendKeys("standard_user");
		password.sendKeys("secret_sauce");
		loginButton.click();
		MobileElement productTitleText = (MobileElement)driver.findElementByXPath("//android.widget.TextView[@text='PRODUCTS']");
		String actualProductTitle = productTitleText.getAttribute("text");
		String expectedProductTitle = "PRODUCTS";
		Thread.sleep(3000);
		Assert.assertEquals(actualProductTitle, expectedProductTitle);
	}
	
	@BeforeClass
	public void beforeClass() throws MalformedURLException, InterruptedException
	{
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
		caps.setCapability(MobileCapabilityType.DEVICE_NAME, "Mi A2");
		caps.setCapability(MobileCapabilityType.AUTOMATION_NAME,"UiAutomator2");
		caps.setCapability("appPackage","com.swaglabsmobileapp");
		caps.setCapability("appActivity","com.swaglabsmobileapp.SplashActivity");
		caps.setCapability("app", "F:\\Projects\\AppiumTDDFramework\\src\\app\\Android.SauceLabs.Mobile.Sample.app.2.2.1.apk");
		driver = new AndroidDriver<WebElement>(new URL("http://0.0.0.0:4723/wd/hub"),caps);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Thread.sleep(5000);
	}
	
	@AfterClass
	public void afterClass()
	{
		driver.quit();
	}

}
