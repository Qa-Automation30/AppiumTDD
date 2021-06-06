package com.qa.pages;

import com.qa.BaseTest;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class ProductsDetailsPage extends BaseTest {
	
	@AndroidFindBy(xpath=("//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView[1]"))
	private MobileElement slbTitle;
	@AndroidFindBy(xpath="//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView[2]")
	private MobileElement slbText;
	@AndroidFindBy(accessibility="test-BACK TO PRODUCTS")private MobileElement backToProductsBtn;
	
	public ProductsPage pressBackToProductsBtn()
	{
		click(backToProductsBtn);
		return new ProductsPage();
	}
	public String getSlbTitle()
	{
		return getText(slbTitle);
	}
	public String getSlbText()
	{
		return getText(slbText);
	}
	
}
