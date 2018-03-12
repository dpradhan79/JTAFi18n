package dweb.aut.pages.i18n.vaseline.templates;

import java.util.Locale;

import org.openqa.selenium.WebDriver;

import com.testreport.IReporter;

import dweb.aut.pages.templates.PageTemplateI18NSupport;

public abstract class PageTemplateVaseline extends PageTemplateI18NSupport {

	/**
	 * 2 argument constructor to set webdriver and testreport
	 * 
	 * @param webDriver
	 * @param testReport
	 */
	protected PageTemplateVaseline(WebDriver webDriver, IReporter testReport) {
		super(webDriver, testReport);
	}

	/**
	 * 3 argument constructor to set webdriver, testreport and locale
	 * 
	 * @param webDriver
	 * @param testReport
	 * @param locale
	 */
	protected PageTemplateVaseline(WebDriver webDriver, IReporter testReport, Locale locale) {
		super(webDriver, testReport, locale);
	}

	/**
	 * 4 argument constructor to set webdriver, testreport, locale and language
	 * specific file with key pair values, but without locale information and file
	 * extension
	 * 
	 * @param webDriver
	 * @param testReport
	 * @param locale
	 * @param languageFileBaseName
	 */
	protected PageTemplateVaseline(WebDriver webDriver, IReporter testReport, Locale locale,
			String languageFileBaseName) {
		super(webDriver, testReport, locale, languageFileBaseName);
	}

}
