package dweb.aut.pages.templates;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.testreport.IReporter;
import com.utilities.ReusableLibs;

/**
 * 
 * @author E001518  - Debasish Pradhan (Architect)
 *
 */
public abstract class PageTemplate {
	private static final Logger LOG = Logger.getLogger(PageTemplate.class);
	protected WebDriver wd = null;
	protected IReporter testReport =  null;	
	protected int implicitWaitInSecs = 0;
	protected int pageLoadTimeOutInSecs = 0;	
	protected PageTemplate(WebDriver webDriver, IReporter testReport)
	{
		this.wd = webDriver;
		this.testReport = testReport;		
		this.implicitWaitInSecs = Integer.parseInt(ReusableLibs.getConfigProperty("ImplicitWaitInSecs"));
		this.pageLoadTimeOutInSecs = Integer.parseInt(ReusableLibs.getConfigProperty("PageLoadTimeOutInSecs"));
		
	}	
	
	protected void SendKeys(By byLocator, String text)
	{
		try
		{
			this.waitUntilElementIsClickable(byLocator);
			this.wd.findElement(byLocator).sendKeys(text);
			LOG.info(String.format("SendKeys Successful - (By - %s, text - %s)", byLocator, text));
			this.testReport.logSuccess("SendKeys", String.format("Entered Text - <mark>%s</mark> To Locator - <mark>%s</mark>", text, byLocator));
			
		}
		catch(Exception ex)
		{
			LOG.error(String.format("Exception Encountered - %s", ex.getMessage()));
			this.testReport.logFailure("SendKeys", String.format("Failed To Enter Text - <mark>%s</mark> To Locator - <mark>%s</mark>", text, byLocator), this.getScreenShotName());
			this.testReport.logException(ex);
						
		}
		
	}
	
	protected void Click(By byLocator)
	{
		try
		{
			this.waitUntilElementIsClickable(byLocator);
			this.wd.findElement(byLocator).click();
			LOG.info(String.format("Click Successful - (By - %s)", byLocator));
			this.testReport.logSuccess("Click", String.format("Click Performed On Locator - <mark>%s</mark>", byLocator));
			
		}
		catch(Exception ex)
		{
			LOG.error(String.format("Exception Encountered - %s", ex.getMessage()));
			this.testReport.logFailure("Click", String.format("Failed To Perform Click On Locator - <mark>%s</mark>", byLocator), this.getScreenShotName());
			this.testReport.logException(ex);
						
		}
		
	}
	
	protected void SelectDropDownByText(By byLocator, String visibleText)
	{
		try
		{
			this.waitUntilElementIsClickable(byLocator);
			new Select(this.wd.findElement(byLocator)).selectByVisibleText(visibleText);
			LOG.info(String.format("SelectDropDownByText Successful - (By - %s, Visible Text - %s)", byLocator, visibleText));
			this.testReport.logSuccess("SelectDropDownByText", String.format("Selected Visible Text - <mark>%s</mark> On Locator - <mark>%s</mark>", visibleText, byLocator));
			
		}
		catch(Exception ex)
		{
			LOG.error(String.format("Exception Encountered - %s", ex.getMessage()));
			this.testReport.logFailure("SelectDropDownByText", String.format("Failed To Perform Select Visible Text - <mark>%s</mark> On Locator - <mark>%s</mark>", visibleText, byLocator) , this.getScreenShotName());
			this.testReport.logException(ex);
						
		}
	}
	
	protected void SelectDropDownByValue(By byLocator, String value)
	{
		try
		{
			this.waitUntilElementIsClickable(byLocator);
			new Select(this.wd.findElement(byLocator)).selectByValue(value);
			LOG.info(String.format("SelectDropDownByValue Successful - (By - %s, Value - %s)", byLocator, value));
			this.testReport.logSuccess("SelectDropDownByValue", String.format("Selected Value - <mark>%s</mark> On Locator - <mark>%s</mark>", value, byLocator));
			
		}
		catch(Exception ex)
		{
			LOG.error(String.format("Exception Encountered - %s", ex.getMessage()));
			this.testReport.logFailure("SelectDropDownByText", String.format("Failed To Perform Select Value - <mark>%s</mark> On Locator - <mark>%s</mark>", value, byLocator) , this.getScreenShotName());
			this.testReport.logException(ex);
						
		}
	}
	
	protected void SelectDropDownByIndex(By byLocator, int index)
	{
		try
		{
			this.waitUntilElementIsClickable(byLocator);
			new Select(this.wd.findElement(byLocator)).selectByIndex(index);
			LOG.info(String.format("SelectDropDownByIndex Successful - (By - %s, Index - %d)", byLocator, index));
			this.testReport.logSuccess("SelectDropDownByIndex", String.format("Selected Index - <mark>%s</mark> On Locator - <mark>%s</mark>", index, byLocator));
			
		}
		catch(Exception ex)
		{
			LOG.error(String.format("Exception Encountered - %s", ex.getMessage()));
			this.testReport.logFailure("SelectDropDownByText", String.format("Failed To Perform Select Index - <mark>%s</mark> On Locator - <mark>%s</mark>", index, byLocator) , this.getScreenShotName());
			this.testReport.logException(ex);
						
		}
	}

	protected boolean checkCheckBox(By byLocator)
	{
		boolean isSuccess = false;
		try
		{
			//read check box web element and store to check box web element obj
	    	WebElement checkBox = this.wd.findElement(byLocator);
	    	
	    	//Wait until element is clickable
	    	this.waitUntilElementIsClickable(byLocator);
	    	
	    	//Click on check-box
	    	if(checkBox.isSelected())
	    	{
	    		LOG.info("check-box already checked");
	    	}
	    	else
	    	{
	    	Actions act = new Actions(this.wd);
	    	act.moveToElement(checkBox).click().build().perform();
	    	LOG.info("check-box checked successfully");
	    	this.testReport.logSuccess("checkCheckBox", String.format("check-box - <mark>%s</mark> checked successfully", byLocator));
	    	}
	    	isSuccess = true;
		}
		catch(Exception ex)
		{
			isSuccess = false;
			LOG.error(String.format("Exception Encountered - %s", ex.getMessage()));
			this.testReport.logFailure("checkCheckBox", String.format("Exception Encountered for <mark>%s</mark>", "checkCheckBox"), this.getScreenShotName());
			this.testReport.logException(ex);
		}
		return isSuccess;
	}
	
	protected String getAttribute(By byLocator, String attribute)
	{
		String attributeValue = null;
		try
		{
			this.waitUntilElementIsClickable(byLocator);
			attributeValue = this.wd.findElement(byLocator).getAttribute(attribute);
			LOG.info(String.format("Method - %s returns value - %s for attribute - %s for Locator - %s", "getAttribute", attributeValue, attribute, byLocator));
			this.testReport.logInfo(String.format("Method - <mark>%s</mark> returns value - <mark>%s</mark> for attribute - <mark>%s</mark> for Locator - <mark>%s</mark>", "getAttribute", attributeValue, attribute, byLocator));
			
		}
		catch(Exception ex)
		{
			LOG.error(String.format("Exception Encountered - %s", ex.getMessage()));
			this.testReport.logFailure(String.format("getAttribute For Element - %s, For Attribute - %s", byLocator, attribute), String.format("Exception Encountered - %s, StackTrace - %s", ex.getMessage(), ex.getStackTrace()), this.getScreenShotName());
									
		}
		return attributeValue;
		
	}
	
	protected boolean waitUntilElementIsClickable(By byLocator)
	{
		boolean isSuccess = false;
		try
		{		
			WebDriverWait wait = new WebDriverWait(this.wd,this.implicitWaitInSecs);
			wait.until(ExpectedConditions.elementToBeClickable(byLocator));
			LOG.info(String.format("Element clickable - (By - %s)", byLocator));
			this.testReport.logSuccess("waitUntilElementIsClickable", String.format("Element clickable - (By - %s)", byLocator));
			isSuccess = true;
		}
		catch(Exception ex)
		{
			isSuccess = false;			
			LOG.error(String.format("Exception Encountered - %s", ex.getMessage()));
			this.testReport.logWarning("waitUntilElementIsClickable", String.format("Exception Encountered - %s, StackTrace - %s", ex.getMessage(), ex.getStackTrace()), this.getScreenShotName());
		}
		return isSuccess;
	}
	
	protected boolean waitUntilElementIsVisible(By byLocator)
	{
		boolean isSuccess = false;
		try
		{		
			WebDriverWait wait = new WebDriverWait(this.wd,this.implicitWaitInSecs);
			wait.until(ExpectedConditions.visibilityOfElementLocated(byLocator));
			LOG.info(String.format("Element visible - (By - %s)", byLocator));
			this.testReport.logSuccess("waitUntilElementIsVisible", String.format("Element visible - (By - %s)", byLocator));
			isSuccess = true;
		}
		catch(Exception ex)
		{
			isSuccess = false;			
			LOG.error(String.format("Exception Encountered - %s", ex.getMessage()));
			this.testReport.logWarning("waitUntilElementIsVisible", String.format("Exception Encountered - %s, StackTrace - %s", ex.getMessage(), ex.getStackTrace(), this.getScreenShotName()));
		}
		return isSuccess;
	}
	
	protected boolean waitUntilDataUpdatedInBackend(String dbUrl, HashMap<String, String> dbCredentials, String sql)
	{	
			WebDriverWait wait = new WebDriverWait(this.wd,this.implicitWaitInSecs);
			return wait.until(new ExpectedCondition<Boolean>() {

				@Override
				public Boolean apply(WebDriver webDriver) {
					ResultSet resultSet = null;
					boolean isSuccess = false;
					try
					{
						Connection conn = DriverManager.getConnection(dbUrl, dbCredentials.get("userName"), dbCredentials.get("password"));
						Statement statement = conn.createStatement();
						resultSet = statement.executeQuery(sql);						
						if(resultSet.next())
						{
							isSuccess = true;
						}
						
					}
					catch(Exception ex)
					{
						isSuccess = false;
						LOG.error(String.format("Exception Encountered - %s", ex.getMessage()));					
						
					}
					return isSuccess;
					
				}
			});		
				
	}
	
	protected boolean isElementPresent(By byLocator)
	{
		boolean isSuccess = false;
		try
		{
			//validate element is displayed or not
			implicitwait(2);
			Assert.assertEquals(wd.findElements(byLocator).size() > 0, true);
			LOG.info(String.format("Element Present - (By - %s)", byLocator));
			this.testReport.logSuccess("isElementPresent", String.format("Element Present - (By - %s)", byLocator));
			isSuccess = true;
		}
		catch(Exception | AssertionError ex)
		{
			isSuccess = false;
			LOG.info(String.format("Element Not Prensent - (By - %s)", byLocator));			
			this.testReport.logInfo(String.format("Element Not Prensent - (By - %s)", byLocator));			
		}
		
		return isSuccess;
	}
	
	protected void implicitwait(int sec)
	{
		try {
			TimeUnit.SECONDS.sleep(sec);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void dragAndDrop(By source , By target)
	{
		implicitwait(3);
		//Read and store drag location
    	WebElement drag = this.wd.findElement(source);
    	
    	//read and store drop location
    	WebElement drop = this.wd.findElement(target);
    	
    	//drag and drop
    	(new Actions(wd)).dragAndDrop(drag, drop).perform();
    	LOG.info(String.format("Drag element - (By - %s)", source));
    	LOG.info(String.format("drop element at - (By - %s)", target));
    	implicitwait(2);
	}
	
		
	protected synchronized String getScreenShotName()
	{
		String screenShotLocation = ReusableLibs.getConfigProperty("ScreenshotLocation");
		String fileExtension = ReusableLibs.getConfigProperty("ScreenshotPictureFormat");		
		return ReusableLibs.getScreenshotFile(screenShotLocation, fileExtension);
	}
}
