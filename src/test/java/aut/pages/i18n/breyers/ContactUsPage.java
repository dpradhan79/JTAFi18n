package aut.pages.i18n.breyers;

import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.testreport.IReporter;
import com.utilities.ReusableLibs;

import aut.pages.templates.PageTemplateAUT;

public class ContactUsPage extends PageTemplateAUT {

	public ContactUsPage(WebDriver webDriver, IReporter testReport, Locale locale, String languageSpecificBaseFile) {
		super(webDriver, testReport, locale);
		this.languageFileBaseName = languageSpecificBaseFile;		
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
	
	

	

}
