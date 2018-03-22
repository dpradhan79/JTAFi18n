package mweb.test.i18n.breyers.functionaltests;

import java.util.Locale;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.config.IConstants;
import com.config.ITestParamsConstants;

import dweb.test.templates.TestTemplateMethodLevelInit;
import mweb.aut.pages.i18n.breyers.HomePage;

@Test(groups = { "mobile" })
public class TestBreyers extends TestTemplateMethodLevelInit{

	@Test
	public void testMenus(ITestContext testContext) throws Exception {
		
		/*DesiredCapabilities dc = new DesiredCapabilities();
		dc.setCapability(MobileCapabilityType.PLATFORM_VERSION, "8.1");
		dc.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
		dc.setCapability(MobileCapabilityType.DEVICE_NAME, "Android emulator");
		dc.setCapability("avd", "Nexus_5_API_27");
		dc.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");
		AppiumDriver<MobileElement> appiumDriver = null;
		WebDriver wd = null;
		try
		{
		appiumDriver = new AppiumDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), dc);
		wd = appiumDriver;
		wd.get(ReusableLibs.getConfigProperty("APPURL"));
		//HomePage homePage = new HomePage(appiumDriver, null, new Locale("en"), IConstants.PAGE_ELEMENTS_BASEFILENAME);
		wd.findElement(By.xpath("//button[@data-ct-action='Drawer Menu']")).click();
		//select language
		Select select = new Select(appiumDriver.findElement(By.id("language")));
		select.selectByVisibleText("English");
		String byMenu = "//a[starts-with(text(), '%s')]";
		wd.findElement(By.xpath(String.format(byMenu, "ABOUT US"))).click();
		Thread.sleep(1000);
		}
		finally
		{
			appiumDriver.close();
			appiumDriver.quit();
		}*/
		
		HomePage homePage = new HomePage(TestTemplateMethodLevelInit.threadLocalWebDriver.get(),
				TestTemplateMethodLevelInit.testReport, new Locale(this.getTestParameter(testContext, ITestParamsConstants.LOCALE_LANGUAGE)),
				IConstants.PAGE_ELEMENTS_BASEFILENAME);		
		homePage.selectLanguage();
		homePage.clickMenus();
	}
}
