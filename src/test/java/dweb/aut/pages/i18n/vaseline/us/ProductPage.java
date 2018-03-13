package dweb.aut.pages.i18n.vaseline.us;

import java.util.Locale;
import java.util.Map;

import org.openqa.selenium.WebDriver;

import com.testreport.IReporter;

import dweb.aut.pages.i18n.vaseline.us.MyReviewPage;

public class ProductPage extends dweb.aut.pages.i18n.vaseline.canada.ProductPage {

	public ProductPage(WebDriver webDriver, IReporter testReport, Locale locale, String languageFileBaseName) {
		super(webDriver, testReport, locale, languageFileBaseName);
		
	}
	
	@Override
	protected void fillReview(Map<String, String> reviewInfo)
	{
		MyReviewPage myReviewPage = new MyReviewPage(this.wd, this.testReport, this.locale, this.languageFileBaseName);
		myReviewPage.writeReview(reviewInfo);
	}

}
