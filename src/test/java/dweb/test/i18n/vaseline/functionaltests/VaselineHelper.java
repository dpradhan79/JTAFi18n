package dweb.test.i18n.vaseline.functionaltests;

import java.util.Locale;

import com.config.IConstants;

import dweb.aut.i18n.vaseline.interfaces.IVaselineUserOperations;
import dweb.aut.i18n.vaseline.regional.VaselineCanadaOperations;
import dweb.aut.i18n.vaseline.regional.VaselineUSOperations;
import dweb.test.templates.TestTemplate;
import dweb.test.templates.TestTemplateMethodLevelInit;

public class VaselineHelper extends TestTemplateMethodLevelInit{

	protected static IVaselineUserOperations getVaselineLocalizedOperations(String localeLanguageCode,
			String localeCountryCode) {
		IVaselineUserOperations countryVaseline = null;
		Locale locale = new Locale(localeLanguageCode, localeCountryCode);
		if (locale.equals(new Locale("en", "us"))) {
			countryVaseline = new VaselineUSOperations(threadLocalWebDriver.get(), TestTemplate.testReport, locale,
					IConstants.PAGE_ELEMENTS_BASEFILENAME);
		} else if (locale.equals(new Locale("en", "ca"))) {
			countryVaseline = new VaselineUSOperations(threadLocalWebDriver.get(), TestTemplate.testReport, locale,
					IConstants.PAGE_ELEMENTS_BASEFILENAME);
		}

		return countryVaseline;
	}

	protected IVaselineUserOperations getVaselineLocalizedOperations(String localeCountryCode) {
		IVaselineUserOperations countryVaseline = null;
		String defaultLanguage = "en";
		Locale locale = new Locale(defaultLanguage, localeCountryCode);
		if (locale.equals(new Locale(defaultLanguage, "us"))) {
			countryVaseline = new VaselineUSOperations(threadLocalWebDriver.get(), TestTemplate.testReport, locale,
					IConstants.PAGE_ELEMENTS_BASEFILENAME);
		} else if (locale.equals(new Locale(defaultLanguage, "ca"))) {
			countryVaseline = new VaselineCanadaOperations(threadLocalWebDriver.get(), TestTemplate.testReport, locale,
					IConstants.PAGE_ELEMENTS_BASEFILENAME);
		}

		return countryVaseline;
	}
}
