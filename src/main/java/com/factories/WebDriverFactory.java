package com.factories;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.google.common.io.Resources;
import com.utilities.ReusableLibs;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.MobileCapabilityType;

public class WebDriverFactory {
		
	private WebDriverFactory() {}
	
	public static WebDriver getWebDriver(Map<String, String> dc) throws URISyntaxException, MalformedURLException
	{
		return getLocalWebDriver(dc);
						
	}
	
	private static WebDriver getLocalWebDriver(Map<String, String> dc) throws MalformedURLException, URISyntaxException
	{
		WebDriver wd = null;
		AppiumDriver<MobileElement> appiumDriver = null;
		if(dc.get("deviceName").equalsIgnoreCase("desktop"))
		{
			switch(dc.get("browser").toUpperCase())		
			{
			case "CHROME": 
				String chromeDriverExe = ReusableLibs.getConfigProperty("ChromeDriverExe");
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
				String ffDriverExe = ReusableLibs.getConfigProperty("FirefoxDriverExe");
				urlFilePath = Resources
						.getResource(String.format("%s%s%s", "drivers", File.separatorChar, ffDriverExe));
				String ffdriverPath = Paths.get(urlFilePath.toURI()).toFile().getAbsolutePath();
				System.setProperty("webdriver.gecko.driver", ffdriverPath);			
				wd = new FirefoxDriver();
				break;
				
			case "IE":			
			case "INTERNETEXPLORER":
				String ieDriverExe = ReusableLibs.getConfigProperty("IEDriverExe");
				urlFilePath = Resources
						.getResource(String.format("%s%s%s", "drivers", File.separatorChar, ieDriverExe));
				String iedriverPath = Paths.get(urlFilePath.toURI()).toFile().getAbsolutePath();
				System.setProperty("webdriver.ie.driver", iedriverPath);	
				wd = new InternetExplorerDriver();
				break;
			}
		}
		else
		{
			DesiredCapabilities cap = new DesiredCapabilities();
			cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, dc.get("mobileAutomationName"));
			cap.setCapability(MobileCapabilityType.PLATFORM_NAME, dc.get("mobilePlatformName"));
			cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, dc.get("mobilePlatformVersion"));			
			cap.setCapability(MobileCapabilityType.DEVICE_NAME, dc.get("mobileDeviceName"));
			if(dc.get("mobileAVD") != null && (!dc.get("mobileAVD").isEmpty()))
			{
				cap.setCapability("avd", dc.get("mobileAVD"));
			}
			cap.setCapability(MobileCapabilityType.BROWSER_NAME, dc.get("browser"));
			String appiumHub = dc.get("appiumHub");
			appiumDriver = new AppiumDriver<MobileElement>(new URL(appiumHub), cap);
			wd = appiumDriver;	
		}	
			
		wd.manage().timeouts().implicitlyWait(Integer.parseInt(ReusableLibs.getConfigProperty("ImplicitWaitInSecs")),
				TimeUnit.SECONDS);
		wd.manage().timeouts().pageLoadTimeout(Integer.parseInt(ReusableLibs.getConfigProperty("PageLoadTimeOutInSecs")),
				TimeUnit.SECONDS);
		if(!(wd instanceof AppiumDriver))
		{
			wd.manage().window().maximize();
		}
		
		return wd;
	}

}
