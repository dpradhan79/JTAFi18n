package mweb.aut.pages.i18n.breyers;

import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.testreport.IReporter;
public class HomePage extends dweb.aut.pages.i18n.breyers.HomePage {

	public HomePage(WebDriver webDriver, IReporter testReport, Locale locale, String languageFileBaseName) {
		super(webDriver, testReport, locale, languageFileBaseName);		
	}
	
	@Override
	public void selectLanguage()	{		
		this.click(By.xpath("//button[@data-ct-action='Drawer Menu']"));
		super.selectLanguage();
	}
	
	@Override
	public void clickMenus()
	{
		for(String menuKey : this.arrayMenuKeys)
		{
			this.click(By.xpath("//button[@data-ct-action='Drawer Menu']"));
			this.clickMenu(menuKey);
		}
	}

}
