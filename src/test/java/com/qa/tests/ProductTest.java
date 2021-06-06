package com.qa.tests;

import org.json.JSONException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qa.BaseTest;
import com.qa.pages.LoginPage;
import com.qa.pages.ProductsDetailsPage;
import com.qa.pages.ProductsPage;

public class ProductTest extends BaseTest {
	LoginPage login;
	ProductsPage productspage;
	ProductsDetailsPage detailspage;
	
	@BeforeMethod
	@Parameters({"platformName","deviceName","platformVersion","emulator","udidForDevice","udidForSimulator"})
	public void login(String platformName,
			String deviceName,
			String platformVersion,
			String emulator,
			String udidForDevice,
			String udidForSimulator) throws JSONException, Exception
	{
		setAndroidDriver(platformName, deviceName, platformVersion, emulator, udidForDevice, udidForSimulator);
		login = new LoginPage();
		productspage = login.loginIntoApplication(loadFiles().getJSONObject("ValidUser").getString("userName"),
				loadFiles().getJSONObject("ValidUser").getString("password"));
	}
	
	@Test
	public void validateProductOnProductPage() throws JSONException, Exception
	{
		SoftAssert sa = new SoftAssert();
		sa.assertEquals(productspage.getSlbTitle(),"Sauce Labs Backpack");
		sa.assertEquals(productspage.getSlbPrice(), "$29.99");
		sa.assertAll();
	}
	@Test
	public void validateProductOnProductsDeatislPage() throws JSONException, Exception
	{
		SoftAssert sa = new SoftAssert();
		detailspage =productspage.pressSlbTitle();
		sa.assertEquals(detailspage.getSlbTitle(), "Sauce Labs Backpack");
		sa.assertEquals(detailspage.getSlbText(), "carry.allTheThings() with the sleek, streamlined Sly Pack that melds uncompromising style with unequaled laptop and tablet protection.");
		sa.assertAll();
	}
	@AfterMethod
	public void tearDown()
	{
		driver.quit();
	}
}
