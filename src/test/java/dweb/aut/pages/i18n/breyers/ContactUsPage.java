package dweb.aut.pages.i18n.breyers;

import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.testreport.IReporter;

import dweb.aut.pages.templates.PageTemplateI18NSupport;

public class ContactUsPage extends PageTemplateI18NSupport {

	public ContactUsPage(WebDriver webDriver, IReporter testReport, Locale locale, String languageSpecificBaseFile) {
		super(webDriver, testReport, locale, languageSpecificBaseFile);			
	}
	
	public void selectLanguage()
	{
		String languageSelection = this.getLocalizedValue("languageSelection");		
		this.SelectDropDownByText(By.id("language"), languageSelection);
	}
	
	public void selectInquiryType()
	{
		String inquiryType = this.getLocalizedValue("inquiryTypeItem");		
		this.SelectDropDownByText(By.id("idInquiryType"), inquiryType);
		
	}
	
	public void fillDetails()
	{
		this.sendKeys(By.id("emailId"), this.getLocalizedValue("emailId"));
		this.sendKeys(By.id("prefix"), this.getLocalizedValue("prefix"));
		this.sendKeys(By.id("firstName"), this.getLocalizedValue("fName"));
		this.sendKeys(By.id("lastName"), this.getLocalizedValue("lName"));
		this.sendKeys(By.id("address1"), this.getLocalizedValue("address1"));
		this.sendKeys(By.id("address2"), this.getLocalizedValue("address2"));
		this.sendKeys(By.id("city"), this.getLocalizedValue("city"));
		this.SelectDropDownByValue(By.id("state"), this.getLocalizedValue("state"));
		
		
	}
	
	

	

}
