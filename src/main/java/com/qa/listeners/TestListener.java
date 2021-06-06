package com.qa.listeners;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.qa.BaseTest;

public class TestListener implements ITestListener {

	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onTestFailure(ITestResult result) {
		if(result.getThrowable()!=null)
		{
			StringWriter swrite = new StringWriter();
			PrintWriter pwrite = new PrintWriter(swrite);
			result.getThrowable().printStackTrace(pwrite);
			System.out.println(swrite.toString());
		}
		
		BaseTest test = new BaseTest();
		File file = test.getDriver().getScreenshotAs(OutputType.FILE);
		Map<String, String> params = new HashMap<String, String>();
		params=	result.getTestContext().getCurrentXmlTest().getAllParameters();
		
		String imgpath ="screenShots"+File.separator+params.get("platformName")+"_"+params.get("platformVersion")+"_"+params.get("deviceName")
		+File.separator+test.getDateTime()+File.separator+result.getTestClass().getRealClass().getSimpleName()+File.separator+result.getName()+".png";
		try {
			FileUtils.copyFile(file, new File(imgpath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

}
