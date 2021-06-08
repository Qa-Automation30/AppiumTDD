package com.qa.tests;

import java.net.MalformedURLException;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.qa.BaseTest;
import com.qa.pages.LoginPage;
import com.qa.pages.ProductsPage;

public class LoginTests extends BaseTest {
	LoginPage loginpage;
	ProductsPage productspage;

	@BeforeClass
	@Parameters({"platformName","deviceName","platformVersion","emulator","udidForDevice1","udidForDevice2","udidForSimulator"})
	public void beforeMethod(String platformName,
			String deviceName,
			String platformVersion,
			String emulator,
			@Optional("udidForDevice1")String udidForDevice1,
			@Optional("udidForDevice2")String udidForDevice2,
			String udidForSimulator) throws MalformedURLException {
		setAndroidDriver(platformName, deviceName, platformVersion, emulator, udidForDevice1,udidForDevice2, udidForSimulator);
		loginpage = new LoginPage();
	}

	@Test(priority = 1)
	public void invalidUserName() throws Exception {
			loginpage.enterUserName(loadFiles().getJSONObject("InvalidUser").getString("userName"));
			loginpage.enterPassword(loadFiles().getJSONObject("InvalidUser").getString("password"));
			loginpage.pressLoginBtn();
			String actualErrorText = loginpage.getErrorText();
			String expectedErrorText = "Username and password do not match any user in this service.";
			Thread.sleep(3000);
			Assert.assertEquals(actualErrorText, expectedErrorText);
	}

	@Test(priority = 2)
	public void InvalidPassword() throws InterruptedException {
		loginpage.enterUserName("standard_user");
		loginpage.enterPassword("invalidPassword");
		loginpage.pressLoginBtn();
		String actualErrorText = loginpage.getErrorText();
		String expectedErrorText = "Username and password do not match any user in this service.";
		Thread.sleep(3000);
		Thread.sleep(3000);
		Assert.assertEquals(actualErrorText, expectedErrorText);

	}

	@Test(priority = 3)
	public void successFulLogin() throws InterruptedException {
		loginpage.enterUserName("standard_user");
		loginpage.enterPassword("secret_sauce");
		productspage = loginpage.pressLoginBtn();
		String actualProductTitle = productspage.getTitle();
		String expectedProductTitle = "PRODUCTS";
		Thread.sleep(3000);
		Assert.assertEquals(actualProductTitle, expectedProductTitle);
	}
	@AfterClass
	public void tearDown()
	{
		getDriver().quit();
	}

}
