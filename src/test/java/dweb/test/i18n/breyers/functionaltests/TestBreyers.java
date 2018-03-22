package dweb.test.i18n.breyers.functionaltests;

import java.util.Locale;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.config.IConstants;
import com.config.ITestParamsConstants;

import dweb.aut.pages.i18n.breyers.ContactUsPage;
import dweb.aut.pages.i18n.breyers.HomePage;
import dweb.test.templates.TestTemplateMethodLevelInit;

@Test(groups = {"web"})
public class TestBreyers extends TestTemplateMethodLevelInit {	
	
	@Test(priority=1)
	public void testMenus(ITestContext testContext) {
		HomePage homePage = new HomePage(TestTemplateMethodLevelInit.threadLocalWebDriver.get(),
				TestTemplateMethodLevelInit.testReport, new Locale(this.getTestParameter(testContext, ITestParamsConstants.LOCALE_LANGUAGE)),
				IConstants.PAGE_ELEMENTS_BASEFILENAME);
		String url = this.getTestParameter(testContext, ITestParamsConstants.APPURL);
		TestTemplateMethodLevelInit.threadLocalWebDriver.get().get(url.substring(0, url.lastIndexOf("/")));
		homePage.selectLanguage();
		homePage.clickMenus();
	}

	@Test(priority=2)
	public void testContactUs(ITestContext testContext) throws InterruptedException {
		ContactUsPage contactPage = new ContactUsPage(TestTemplateMethodLevelInit.threadLocalWebDriver.get(),
				TestTemplateMethodLevelInit.testReport, new Locale(this.getTestParameter(testContext, ITestParamsConstants.LOCALE_LANGUAGE)),
				IConstants.PAGE_ELEMENTS_BASEFILENAME);
		HomePage homePage = new HomePage(TestTemplateMethodLevelInit.threadLocalWebDriver.get(),
				TestTemplateMethodLevelInit.testReport, new Locale(this.getTestParameter(testContext, ITestParamsConstants.LOCALE_LANGUAGE)),
				IConstants.PAGE_ELEMENTS_BASEFILENAME);
		homePage.selectLanguage();
		contactPage.selectInquiryType();
		contactPage.fillDetails();		

	}

}
