package dweb.aut.pages.i18n.vaseline.us;

import java.util.Locale;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.testreport.IReporter;

public class MyReviewPage extends dweb.aut.pages.i18n.vaseline.canada.MyReviewPage {

	protected MyReviewPage(WebDriver webDriver, IReporter testReport, Locale locale, String languageFileBaseName) {
		super(webDriver, testReport, locale, languageFileBaseName);
		
	}
	@Override
	protected void writeReview(Map<String, String> reviewInfo)
	{
		String overallRating = reviewInfo.get("OverallRating");
		this.click(By.xpath(String.format(this.byOverallRating, overallRating)));
		this.sendKeys(By.name("title"), reviewInfo.get("ReviewTitle"));
		this.sendKeys(By.name("reviewtext"), reviewInfo.get("Review"));
		this.sendKeys(By.name("usernickname"), reviewInfo.get("NickName"));
		this.sendKeys(By.name("additionalfield_Zipcode"), reviewInfo.get("ZipCode"));
		this.sendKeys(By.name("hostedauthentication_authenticationemail"), reviewInfo.get("Email"));
		this.SelectDropDownByValue(By.name("contextdatavalue_BirthMonth"), reviewInfo.get("BirthMonth"));
		this.SelectDropDownByValue(By.name("contextdatavalue_BirthYear"), reviewInfo.get("BirthYear"));
		this.SelectDropDownByValue(By.name("contextdatavalue_Gender"), reviewInfo.get("Gender"));
		this.click(By.xpath(String.format(this.byRatingNumber, reviewInfo.get("RecommendRating"))));
		this.checkCheckBox(By.name("agreedtotermsandconditions"));
	}

}
