package com.factories;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.google.common.io.Resources;
import com.utilities.ReusableLibs;

import test.templates.TestTemplateDWebMethodLevelInit;

public class WebDriverFactory {
		
	private WebDriverFactory() {}
	
	public static WebDriver getWebDriver(String browser) throws URISyntaxException
	{
		WebDriver wd = null;
		switch(browser.toUpperCase())
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
		}
		wd.manage().timeouts().implicitlyWait(Integer.parseInt(ReusableLibs.getConfigProperty("ImplicitWaitInSecs")),
				TimeUnit.SECONDS);
		wd.manage().timeouts().pageLoadTimeout(Integer.parseInt(ReusableLibs.getConfigProperty("PageLoadTimeOutInSecs")),
				TimeUnit.SECONDS);
		wd.manage().window().maximize();
		return wd;
	}

}
