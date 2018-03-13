package dweb.aut.i18n.vaseline.regional;

import java.util.Locale;
import java.util.Map;

import org.openqa.selenium.WebDriver;

import com.testreport.IReporter;

import dweb.aut.i18n.vaseline.interfaces.IVaselineUserOperations;
import dweb.aut.pages.i18n.vaseline.canada.HomePage;
import dweb.aut.pages.i18n.vaseline.canada.ProductPage;

public class VaselineCanadaOperations implements IVaselineUserOperations {

	protected HomePage homePage = null;
	protected ProductPage productPage = null;
	
	public VaselineCanadaOperations(WebDriver webDriver, IReporter testReport, Locale locale,
			String languageFileBaseName) {
		this.homePage = new HomePage(webDriver, testReport, locale, languageFileBaseName);
		this.productPage = new ProductPage(webDriver, testReport, locale, languageFileBaseName);
	}

	@Override
	public void selectMenu(String menuLink) {
		this.homePage.selectMenu(menuLink);

	}

	@Override
	public void validateMenuItems(String menuItem) {
		this.homePage.isMenuProductProductPresent(menuItem);
	}

	@Override
	public void selectMenuProduct(String product) {
		this.homePage.selectMenuProduct(product);

	}

	@Override
	public void readReview(String productItem, float expectedRating) {
		this.productPage.readReview(productItem, expectedRating);

	}

	@Override
	public void writeReview(String productItem, Map<String, String> mapKPValues) {
		this.productPage.writeReview(productItem, mapKPValues);

	}

}
