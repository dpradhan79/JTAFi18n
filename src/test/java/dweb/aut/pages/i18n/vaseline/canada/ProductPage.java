package dweb.aut.pages.i18n.vaseline.canada;

import java.util.Locale;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.testreport.IReporter;

import dweb.aut.pages.i18n.vaseline.templates.PageTemplateVaseline;

public class ProductPage extends PageTemplateVaseline {

	protected String byProductItem = "//a[@title='%s']";
	protected String byProductItemExternalRating = "//a[@title='%s']//span[@class='bv-rating']/span";
	protected String byRatingValue = "//span[@itemprop='ratingValue']";
	protected String byReview = "//div[@class='bv-primarySummary-rating-container']//button[contains(text(), 'Write a review')]";
	public ProductPage(WebDriver webDriver, IReporter testReport, Locale locale, String languageFileBaseName) {
		super(webDriver, testReport, locale, languageFileBaseName);
		
	}
	
	public void readReview(String productItem, float expectedRating)
	{
		//Get The Rating At Product Page
		String externalRating = this.getText(By.xpath(String.format(byProductItemExternalRating, productItem)));
		String externalUrl = this.wd.getCurrentUrl();
		float fExtRating = Float.parseFloat(externalRating);
		this.testReport.logInfo(String.format("Rating At Page - %s For Item - %s Found To Be - %s", externalUrl, productItem, externalRating), this.getScreenShotName());
		this.click(By.xpath(String.format(byProductItem, productItem)));
		String strActualRating = this.getText(By.xpath(byRatingValue));
		float actualRating = Float.parseFloat(strActualRating);
		String currentUrl = this.wd.getCurrentUrl();
		this.testReport.logInfo(String.format("Rating At Page - %s For Item - %s Found To Be - %s", currentUrl, productItem, actualRating), this.getScreenShotName());
		if(fExtRating != actualRating)
		{
			this.testReport.logFailure("Review Rating Mismatch", String.format("Review Rating Mismatch Between Page - %s And Page - %s, Found To Be %s and %s respectively", externalUrl, currentUrl, externalRating, strActualRating), this.getScreenShotName());
		}
		if(expectedRating != actualRating)
		{
			this.testReport.logFailure("Intentional Failure By Wrongly Expecting Rating", String.format("Item - %s Expected Review Score - %f, Does Not Match With Actual Score - %f", productItem, expectedRating, actualRating), this.getScreenShotName());
		}
		else
		{
			this.testReport.logSuccess("readReview", String.format("Item - %s Expected Review Score - %f, Matches With Actual Score - %f", productItem, expectedRating, actualRating), this.getScreenShotName());
		}
	}
	
	public void writeReview(String productItem, Map<String, String> reviewInfo)
	{
		this.click(By.xpath(String.format(byProductItem, productItem)));
		this.click(By.xpath(byReview));
		this.fillReview(reviewInfo);
		
	}
	
	protected void fillReview(Map<String, String> reviewInfo)
	{
		MyReviewPage myReviewPage = new MyReviewPage(this.wd, this.testReport, this.locale, this.languageFileBaseName);
		myReviewPage.writeReview(reviewInfo);
	}

}
