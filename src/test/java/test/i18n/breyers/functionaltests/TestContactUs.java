package test.i18n.breyers.functionaltests;

import java.util.Locale;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import aut.pages.i18n.breyers.ContactUsPage;
import test.templates.TestTemplateDWebMethodLevelInit;

public class TestContactUs extends TestTemplateDWebMethodLevelInit {

	@Test
	@Parameters("language")
	public void testDisplay(String language) throws InterruptedException {
		ContactUsPage contactPage = new ContactUsPage(TestTemplateDWebMethodLevelInit.threadLocalWebDriver.get(),
				TestTemplateDWebMethodLevelInit.testReport, new Locale(language), "page_elements.DesktopWebUI");
		contactPage.selectLanguage();
		contactPage.selectInquiryType();
		Thread.sleep(10000);

	}

}
