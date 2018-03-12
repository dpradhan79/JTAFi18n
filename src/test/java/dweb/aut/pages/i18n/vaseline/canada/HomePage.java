package dweb.aut.pages.i18n.vaseline.canada;

import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.testreport.IReporter;

import dweb.aut.pages.i18n.vaseline.templates.PageTemplateVaseline;

public class HomePage extends PageTemplateVaseline {

	protected String byMenu =  "//a[contains(text(),'%s')]";
	protected String byMenuProduct = "//img[contains(@title,'%s')]";
	/**
	 * 4 argument constructor to set webdriver, testreport, locale and language
	 * specific file with key pair values, but without locale information and file
	 * extension
	 * @param webDriver
	 * @param testReport
	 * @param locale
	 * @param languageFileBaseName
	 */
	public HomePage(WebDriver webDriver, IReporter testReport, Locale locale, String languageFileBaseName) {
		super(webDriver, testReport, locale, languageFileBaseName);
		
	}
		
	public void selectMenu(String menuLink)
	{
		this.click(By.xpath(String.format(this.byMenu, menuLink)));
	}
	
	public void isMenuProductProductPresent(String menuProduct)
	{
		boolean isPresent = this.isElementPresent(By.xpath(String.format(this.byMenuProduct, menuProduct)));
		if(!isPresent)
		{
			this.testReport.logFailure("isElementPresent", String.format("Menu Product - %s Not Present", menuProduct), this.getScreenShotName());
		}
		else
		{
			this.testReport.logSuccess("isElementPresent", String.format("Menu Product - %s Present", menuProduct), this.getScreenShotName());
		}
	}
	
	public void selectMenuProduct(String product)
	{
		this.click(By.xpath(String.format(this.byMenuProduct, product)));
	}

	
}
