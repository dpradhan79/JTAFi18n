package dweb.aut.pages.i18n.vaseline.canada;

import java.util.Locale;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.testreport.IReporter;

import dweb.aut.pages.i18n.vaseline.templates.PageTemplateVaseline;

public class MyReviewPage extends PageTemplateVaseline {

	protected String overallRating = "//span[contains(text(),'Overall Rating*')]/parent::span/parent::legend/following::span[1]/following-sibling::span//span[text()='%s']/preceding-sibling::span";
	
	protected MyReviewPage(WebDriver webDriver, IReporter testReport, Locale locale, String languageFileBaseName) {
		super(webDriver, testReport, locale, languageFileBaseName);
		
	}
	
	protected void writeReview(Map<String, String> reviewInfo)
	{
		this.click(By.xpath(String.format(overallRating, "Good")));
	}

}
