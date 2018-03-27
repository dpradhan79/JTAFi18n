package mweb.test.i18n.breyers.functionaltests;

import java.util.Locale;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.config.IConstants;
import com.config.ITestParamsConstants;

import dweb.test.templates.TestTemplateMethodLevelInit;
import mweb.aut.pages.i18n.breyers.HomePage;

@Test(groups = { "mobile" })
public class TestBreyers extends TestTemplateMethodLevelInit {

	@Test
	public void testMenus(ITestContext testContext) throws Exception {

		HomePage homePage = new HomePage(TestTemplateMethodLevelInit.threadLocalWebDriver.get(),
				TestTemplateMethodLevelInit.testReport,
				new Locale(this.getTestParameter(testContext, ITestParamsConstants.LOCALE_LANGUAGE)),
				IConstants.PAGE_ELEMENTS_BASEFILENAME);
		homePage.selectLanguage();
		homePage.clickMenus();
	}
}
