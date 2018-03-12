package dweb.test.i18n.vaseline.functionaltests;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import dweb.aut.i18n.vaseline.interfaces.IVaselineUserOperations;
import dweb.test.templates.TestTemplateMethodLevelInit;

public class TestHome extends TestTemplateMethodLevelInit{
	
	@Test	
	public void validateNavigationAndMenus(ITestContext testContext) throws InterruptedException
	{
		String localeCountryCode = this.getTestParameter(testContext, "localeCountry");
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
	
	@Test
	public void validateReviews(ITestContext testContext)
	{
		String localeCountryCode = this.getTestParameter(testContext, "localeCountry");		
		String menuSelection = this.getTestParameter(testContext, "menuSelection");
		String menuItemSelection = this.getTestParameter(testContext, "menuItemSelection");
		String reviewItem =  this.getTestParameter(testContext, "reviewItem");
		String expectedRating =  this.getTestParameter(testContext, "expectedRating");
		
		//get locale specific application to perform operations
		IVaselineUserOperations vCountry = getVaselineLocalizedOperations(localeCountryCode);
		//select Menu
		vCountry.selectMenu(menuSelection);
		//Select Product Item
		vCountry.selectMenuProduct(menuItemSelection);
		//review
		vCountry.readReview(reviewItem, Float.parseFloat(expectedRating));
	}
	
	@Test
	public void writeReview(ITestContext testContext)
	{
		String localeCountryCode = this.getTestParameter(testContext, "localeCountry");		
		String menuSelection = this.getTestParameter(testContext, "menuSelection");
		String menuItemSelection = this.getTestParameter(testContext, "menuItemSelection");
		String reviewItem =  this.getTestParameter(testContext, "reviewItem");
		
		
		//get locale specific application to perform operations
		IVaselineUserOperations vCountry = getVaselineLocalizedOperations(localeCountryCode);
		//select Menu
		vCountry.selectMenu(menuSelection);
		//Select Product Item
		vCountry.selectMenuProduct(menuItemSelection);
		//write review
		vCountry.writeReview(reviewItem, null);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
