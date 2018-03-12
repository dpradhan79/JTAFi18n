package dweb.aut.pages.i18n.vaseline.us;

import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.testreport.IReporter;

public class HomePage extends dweb.aut.pages.i18n.vaseline.canada.HomePage {

	public HomePage(WebDriver webDriver, IReporter testReport, Locale locale, String languageFileBaseName) {
		super(webDriver, testReport, locale, languageFileBaseName);
		
	}
	
	@Override
	public void selectMenu(String menuLink)
	{		
		this.moveToElement(By.xpath(String.format(this.byMenu, menuLink)));		
	}

}
