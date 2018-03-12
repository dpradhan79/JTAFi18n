package dweb.aut.i18n.vaseline.operations;

import java.util.Locale;

import org.openqa.selenium.WebDriver;

import com.testreport.IReporter;

import dweb.aut.pages.i18n.vaseline.us.HomePage;

public class VaselineUSOperations extends VaselineCanadaOperations{

	public VaselineUSOperations(WebDriver webDriver, IReporter testReport, Locale locale, String languageFileBaseName) {
		super(webDriver, testReport, locale, languageFileBaseName);
		this.homePage = new HomePage(webDriver, testReport, locale, languageFileBaseName);
	}

	

}
