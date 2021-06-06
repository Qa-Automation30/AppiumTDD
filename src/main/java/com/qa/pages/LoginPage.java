package com.qa.pages;

import com.qa.BaseTest;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class LoginPage extends BaseTest {
	
	@AndroidFindBy(accessibility="test-Username")private MobileElement userName;
	@AndroidFindBy(accessibility="test-Password")private MobileElement password;
	@AndroidFindBy(accessibility="test-LOGIN") private MobileElement loginBtn;
	@AndroidFindBy(xpath="//android.view.ViewGroup[@content-desc=\"test-Error message\"]/android.widget.TextView")private MobileElement errorText;

	public LoginPage enterUserName(String username)
	{
		clear(this.userName);
		sendKeys(this.userName, username);
		return this;
	}
	public LoginPage enterPassword(String password)
	{
		clear(this.password);
		sendKeys(this.password, password);
		return this;
	}
	public ProductsPage pressLoginBtn()
	{
		click(this.loginBtn);
		return new ProductsPage();
	}
	public String getErrorText()
	{
		return getAttribute(this.errorText, "text");
	}
	public ProductsPage loginIntoApplication(String userName,String password)
	{
		enterUserName(userName);
		enterPassword(password);
		pressLoginBtn();
		return new ProductsPage();
	}

}
