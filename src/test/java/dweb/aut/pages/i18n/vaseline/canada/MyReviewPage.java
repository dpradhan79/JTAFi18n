package dweb.aut.pages.i18n.vaseline.canada;

import java.util.Locale;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.testreport.IReporter;

import dweb.aut.pages.i18n.vaseline.templates.PageTemplateVaseline;

public class MyReviewPage extends PageTemplateVaseline {

	protected String byOverallRating = "//span[contains(text(),'Overall Rating*')]/parent::span/parent::legend/following::span[1]/following-sibling::span//span[text()='%s']/preceding-sibling::span";
	protected String byRatingNumber = "//label[@class='bv-radio-wrapper-label' and text()='%s']/parent::li";
	protected MyReviewPage(WebDriver webDriver, IReporter testReport, Locale locale, String languageFileBaseName) {
		super(webDriver, testReport, locale, languageFileBaseName);
		
	}
	
	protected void writeReview(Map<String, String> reviewInfo)
	{
		String overallRating = reviewInfo.get("OverallRating");
		this.click(By.xpath(String.format(this.byOverallRating, overallRating)));
		this.sendKeys(By.name("title"), reviewInfo.get("ReviewTitle"));
		this.sendKeys(By.name("reviewtext"), reviewInfo.get("Review"));
		this.sendKeys(By.name("usernickname"), reviewInfo.get("NickName"));
		this.sendKeys(By.name("userlocation"), reviewInfo.get("ZipCode"));
		this.sendKeys(By.name("hostedauthentication_authenticationemail"), reviewInfo.get("Email"));
		this.SelectDropDownByValue(By.name("contextdatavalue_Age"), reviewInfo.get("AgeRange"));
		this.SelectDropDownByValue(By.name("contextdatavalue_Gender"), reviewInfo.get("Gender"));
		this.click(By.xpath(String.format(this.byRatingNumber, reviewInfo.get("RecommendRating"))));
		this.checkCheckBox(By.name("agreedtotermsandconditions"));
		
		
	}

}
