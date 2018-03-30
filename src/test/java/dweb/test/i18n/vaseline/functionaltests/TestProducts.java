package dweb.test.i18n.vaseline.functionaltests;

import java.util.Hashtable;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.config.ITestParamsConstants;

import dweb.aut.i18n.vaseline.interfaces.IVaselineUserOperations;

@Test(groups = {"web"})
public class TestProducts extends VaselineHelper{
	
	@Test (enabled = true)
	public void validateNavigationAndMenus(ITestContext testContext) throws InterruptedException
	{
		String localeCountryCode = this.getTestParameter(testContext, ITestParamsConstants.LOCALE_COUNTRY);
		String menuLinksCommaSeparated = this.getTestParameter(testContext, "Menus");		
		String [] arrayMenuLinks = menuLinksCommaSeparated.split(",");
		String [] arrayMenuItems = this.getTestParameter(testContext, "menuItems").split(",");
		String menuSelection = this.getTestParameter(testContext, "menuSelection");
		String menuItemSelection = this.getTestParameter(testContext, "menuItemSelection");
		
		
		//get locale specific application to perform operations
		IVaselineUserOperations vCountry = getVaselineLocalizedOperations(localeCountryCode);
		
		//validate menuLink on Home Page
		for(String menuLink : arrayMenuLinks)
		{
			vCountry.selectMenu(menuLink);
		}
		
		//select Menu
		vCountry.selectMenu(menuSelection);
		//validate presence of products		
		for(String menuProduct : arrayMenuItems)
		{
			vCountry.validateMenuItems(menuProduct);
		}
		
		//Select Product Item
		vCountry.selectMenuProduct(menuItemSelection);
		
	}
	
	@Test(enabled = true)
	public void validateReviews(ITestContext testContext) throws InterruptedException
	{
		String localeCountryCode = this.getTestParameter(testContext, ITestParamsConstants.LOCALE_COUNTRY);		
		String menuSelection = this.getTestParameter(testContext, "menuSelection");
		String menuItemSelection = this.getTestParameter(testContext, "menuItemSelection");
		String reviewItem =  this.getTestParameter(testContext, "reviewItem");
		String expectedRating =  this.getTestParameter(testContext, "expectedRating");
		
		//get locale specific application to perform operations
		IVaselineUserOperations vCountry = getVaselineLocalizedOperations(localeCountryCode);
		//select Menu
		vCountry.selectMenu(menuSelection);
		Thread.sleep(10000);
		//Select Product Item
		vCountry.selectMenuProduct(menuItemSelection);
		Thread.sleep(10000);
		//review
		vCountry.readReview(reviewItem, Float.parseFloat(expectedRating));
	}
	
	@Test(dataProvider = "getDataFromExcel", enabled = true)
	public void writeReview(Hashtable<String, String> data, ITestContext testContext) throws InterruptedException
	{
		String localeCountryCode = this.getTestParameter(testContext, ITestParamsConstants.LOCALE_COUNTRY);		
		String menuSelection = this.getTestParameter(testContext, "menuSelection");
		String menuItemSelection = this.getTestParameter(testContext, "menuItemSelection");
		String reviewItem =  this.getTestParameter(testContext, "reviewItem");
		
		
		//get locale specific application to perform operations
		IVaselineUserOperations vCountry = getVaselineLocalizedOperations(localeCountryCode);
		//select Menu
		vCountry.selectMenu(menuSelection);
		Thread.sleep(5000);
		//Select Product Item
		vCountry.selectMenuProduct(menuItemSelection);
		Thread.sleep(5000);
		//write review
		Map<String, String> mapReviewInfo = new Hashtable<String, String>();
		mapReviewInfo.put("OverallRating", data.get("OverallRating"));
		mapReviewInfo.put("ReviewTitle", data.get("ReviewTitle"));
		mapReviewInfo.put("Review", data.get("Review"));
		mapReviewInfo.put("NickName", data.get("NickName"));
		mapReviewInfo.put("ZipCode", data.get("ZipCode"));
		mapReviewInfo.put("Email", data.get("Email"));
		mapReviewInfo.put("BirthMonth", data.get("BirthMonth"));
		mapReviewInfo.put("BirthYear", data.get("BirthYear"));		
		mapReviewInfo.put("AgeRange", data.get("AgeRange"));
		mapReviewInfo.put("Gender", data.get("Gender"));
		mapReviewInfo.put("RecommendRating", data.get("RecommendRating"));
		
		vCountry.writeReview(reviewItem, mapReviewInfo);		
		Thread.sleep(10000);
	
	}

}
