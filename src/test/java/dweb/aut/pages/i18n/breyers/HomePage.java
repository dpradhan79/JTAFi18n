package dweb.aut.pages.i18n.breyers;

import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.testreport.IReporter;

import dweb.aut.pages.templates.PageTemplateI18NSupport;

public class HomePage extends PageTemplateI18NSupport{
	
	private String byMenu = "//a[starts-with(text(), '%s')]";
	
	public HomePage(WebDriver webDriver, IReporter testReport, Locale locale, String languageFileBaseName) {
		super(webDriver, testReport, locale, languageFileBaseName);
		// TODO Auto-generated constructor stub
	}

	public void selectLanguage()
	{
		String languageSelection = this.getLocalizedValue("languageSelection");		
		this.SelectDropDownByText(By.id("language"), languageSelection);
	}
	
	public void ClickMenus()
	{
		String [] arrayMenuKeys = new String [] {"menu_aboutUs", "menu_pledge", "menu_2in1", "menu_delights", "menu_gelato", "menu_vanillas", "menu_products", "menu_recipes"};
		for(String menuKey : arrayMenuKeys)
		{
			String menuValue =  this.getLocalizedValue(menuKey);
			if(menuValue != null)
			{
				this.click(By.xpath(String.format(this.byMenu, menuValue)));
				this.testReport.logInfo(String.format("Appllication Screen After Clicking Menu - %s", menuValue), this.getScreenShotName());
			}
			else
			{
				this.testReport.logInfo(String.format("FOR LANGUAGE - %s, MENU WAS NOT PRESENT For KEY - %s", this.locale.getLanguage(), menuKey));
			}
		}
	}

}
