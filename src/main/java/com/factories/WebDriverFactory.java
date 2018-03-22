package com.factories;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.config.ITestParamsConstants;
import com.google.common.io.Resources;
import com.testreport.ReportFactory;
import com.utilities.ReusableLibs;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class WebDriverFactory {
		
	private static final Logger LOG = Logger.getLogger(WebDriverFactory.class);
	private WebDriverFactory() {}
	
	public static WebDriver getWebDriver(Map<String, String> dc) throws URISyntaxException, MalformedURLException
	{
		return getLocalWebDriver(dc);
						
	}
	
	private static WebDriver getLocalWebDriver(Map<String, String> dc) throws MalformedURLException, URISyntaxException
	{
		WebDriver wd = null;
		AppiumDriver<MobileElement> appiumDriver = null;
		if(dc.get(ITestParamsConstants.AUTOMATION_KIT).equalsIgnoreCase("selenium"))
		{
			switch(dc.get(ITestParamsConstants.BROWSER).toUpperCase())		
			{
			case "CHROME": 
				String chromeDriverExe = ReusableLibs.getConfigProperty(ITestParamsConstants.CHROME_DRIVER_EXE);
				URL urlFilePath = Resources
						.getResource(String.format("%s%s%s", "drivers", File.separatorChar, chromeDriverExe));
				String chromedriverPath = Paths.get(urlFilePath.toURI()).toFile().getAbsolutePath();
				System.setProperty("webdriver.chrome.driver", chromedriverPath);
		
				/* Chrome Settings */
				Map<String, Object> prefs = new HashMap<String, Object>();
				ChromeOptions options = new ChromeOptions();
				options.addArguments("disable-extensions");
				prefs.put("credentials_enable_service", false);
				prefs.put("profile.password_manager_enabled", false);
				options.setExperimentalOption("prefs", prefs);
				/* Chrome settings Done */
	
				wd = new ChromeDriver(options);			
				break;
				
			case "FF":
			case "FIREFOX":
				String ffDriverExe = ReusableLibs.getConfigProperty(ITestParamsConstants.FF_DRIVER_EXE);
				urlFilePath = Resources
						.getResource(String.format("%s%s%s", "drivers", File.separatorChar, ffDriverExe));
				String ffdriverPath = Paths.get(urlFilePath.toURI()).toFile().getAbsolutePath();
				System.setProperty("webdriver.gecko.driver", ffdriverPath);			
				wd = new FirefoxDriver();
				break;
				
			case "IE":			
			case "INTERNETEXPLORER":
				String ieDriverExe = ReusableLibs.getConfigProperty(ITestParamsConstants.IE_DRIVER_EXE);
				urlFilePath = Resources
						.getResource(String.format("%s%s%s", "drivers", File.separatorChar, ieDriverExe));
				String iedriverPath = Paths.get(urlFilePath.toURI()).toFile().getAbsolutePath();
				System.setProperty("webdriver.ie.driver", iedriverPath);	
				wd = new InternetExplorerDriver();
				break;
			}
		}
		else if(dc.get(ITestParamsConstants.AUTOMATION_KIT).equalsIgnoreCase("appium"))
		{
			DesiredCapabilities cap = new DesiredCapabilities();
			cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, dc.get(ITestParamsConstants.APPIUM_AUTOMATION_NAME));
			cap.setCapability(MobileCapabilityType.PLATFORM_NAME, dc.get(ITestParamsConstants.APPIUM_PLATFORM_NAME));
			cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, dc.get(ITestParamsConstants.APPIUM_PLATFORM_VERSION));			
			cap.setCapability(MobileCapabilityType.DEVICE_NAME, dc.get(ITestParamsConstants.APPIUM_DEVICE_NAME));
			if(dc.get(ITestParamsConstants.APPIUM_AVD) != null && (!dc.get(ITestParamsConstants.APPIUM_AVD).isEmpty()))
			{
				cap.setCapability("avd", dc.get(ITestParamsConstants.APPIUM_AVD));
			}
			cap.setCapability(MobileCapabilityType.BROWSER_NAME, dc.get(ITestParamsConstants.BROWSER));
			String appiumHub = dc.get(ITestParamsConstants.APPIUM_HUB);			
			appiumDriver = new AppiumDriver<MobileElement>(new URL(appiumHub), cap);
			wd = appiumDriver;	
		}	
			
		wd.manage().timeouts().implicitlyWait(Integer.parseInt(ReusableLibs.getConfigProperty(ITestParamsConstants.IMPLICIT_WAIT_IN_SECS)),
				TimeUnit.SECONDS);
		wd.manage().timeouts().pageLoadTimeout(Integer.parseInt(ReusableLibs.getConfigProperty(ITestParamsConstants.PAGE_LOAD_TIME_OUT_IN_SECS)),
				TimeUnit.SECONDS);
		if(!(wd instanceof AppiumDriver))
		{
			wd.manage().window().maximize();
		}
		
		return wd;
	}

}
