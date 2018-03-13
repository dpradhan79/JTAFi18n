package dweb.test.i18n.breyers.functionaltests;

import java.util.Locale;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import dweb.aut.pages.i18n.breyers.ContactUsPage;
import dweb.test.templates.TestTemplateMethodLevelInit;

public class TestContactUs extends TestTemplateMethodLevelInit {

	@Test
	@Parameters("language")
	public void testDisplay(String language) throws InterruptedException {
		ContactUsPage contactPage = new ContactUsPage(TestTemplateMethodLevelInit.threadLocalWebDriver.get(),
				TestTemplateMethodLevelInit.testReport, new Locale(language), "page_elements.DesktopWebUI");
		contactPage.selectLanguage();
		contactPage.selectInquiryType();	
		contactPage.fillDetails();
		Thread.sleep(10000);

	}

}
