package com.qa.pages;

import com.qa.BaseTest;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class ProductsPage extends BaseTest {
	
	@AndroidFindBy(xpath="//android.widget.TextView[@text='PRODUCTS']")
	private MobileElement productTitleText;
	
	@AndroidFindBy(xpath="(//android.widget.TextView[@content-desc=\"test-Item title\"])[1]")
	private MobileElement slbTitle;
	
	@AndroidFindBy(xpath="(//android.widget.TextView[@content-desc=\"test-Price\"])[1]")
	private MobileElement slbPrice;
	
	public String getTitle()
	{
		return getAttribute(this.productTitleText, "text");
	}
	public String getSlbTitle()
	{
		System.out.println(slbTitle.getText());
		return slbTitle.getText();
	}
	public String getSlbPrice()
	{
		System.out.println(slbPrice.getText());
		return slbPrice.getText();
	}
	public ProductsDetailsPage pressSlbTitle()
	{
		click(slbTitle);
		return new ProductsDetailsPage();
	}
	

}
