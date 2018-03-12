package dweb.aut.pages.templates;

import java.util.Locale;

import org.openqa.selenium.WebDriver;

import com.testreport.IReporter;
import com.utilities.ReusableLibs;

/**
 * 
 * @author E001518 (Debasish Pradhan)
 *
 */
public abstract class PageTemplateI18NSupport extends PageTemplate {

	protected Locale locale = null;
	protected String languageFileBaseName = null;

	/**
	 * 2 argument constructor to set webdriver and testreport
	 * 
	 * @param webDriver
	 * @param testReport
	 */
	protected PageTemplateI18NSupport(WebDriver webDriver, IReporter testReport) {
		super(webDriver, testReport);
	}

	/**
	 * 3 argument constructor to set webdriver, testreport and locale
	 * 
	 * @param webDriver
	 * @param testReport
	 * @param locale
	 */
	protected PageTemplateI18NSupport(WebDriver webDriver, IReporter testReport, Locale locale) {
		super(webDriver, testReport);
		this.locale = locale;

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
	protected PageTemplateI18NSupport(WebDriver webDriver, IReporter testReport, Locale locale,
			String languageFileBaseName) {
		super(webDriver, testReport);
		this.locale = locale;
		this.languageFileBaseName = languageFileBaseName;
	}

	/**
	 * locale to be set in AUT page
	 * 
	 * @param locale
	 */
	public void setPageLocale(Locale locale) {
		this.locale = locale;
	}

	/**
	 * language specific file with key pair values,but without locale and extension,
	 * to be set in AUT page
	 * 
	 * @param languageFileBaseName
	 */
	public void setPageLanguageSpecificFileBaseName(String languageFileBaseName) {
		this.languageFileBaseName = languageFileBaseName;
	}

	/**
	 * 
	 * @return AUT Page Locale
	 */
	public Locale getPageLocale() {
		return this.locale;
	}

	/**
	 * 
	 * @return AUT Page langauge specific file with key pair values, but without
	 *         locale and extension
	 */
	public String getPageLanguageSpecificFileBaseName() {
		return this.languageFileBaseName;
	}

	/**
	 * 
	 * @param sbaseName
	 * @param sKey
	 * @return value stored in language specific file with key pair values.
	 */
	protected String getLocalizedValue(String sbaseName, String sKey) {
		return ReusableLibs.getLocalizedKeyValue(this.locale, sbaseName, sKey);
	}

	/**
	 * 
	 * @param sKey
	 * @return value stored in language specific file with key pair value
	 */
	protected String getLocalizedValue(String sKey) {
		return ReusableLibs.getLocalizedKeyValue(this.locale, this.languageFileBaseName, sKey);
	}

}
