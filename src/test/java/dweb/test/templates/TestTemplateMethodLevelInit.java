package dweb.test.templates;

import java.lang.reflect.Method;
import java.net.URISyntaxException;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.xml.XmlTest;

import com.factories.WebDriverFactory;
import com.testreport.ExtentReporter;
import com.testreport.ExtentReporter.ExtentTestVisibilityMode;
import com.testreport.ReportFactory;
import com.testreport.ReportFactory.ReportType;
import com.utilities.ReusableLibs;

/**
 * Class with configuration for test execution
 * @author E001518 - Debasish Pradhan (Architect)
 *
 */
public abstract class TestTemplateMethodLevelInit extends TestTemplate {

	private static final Logger LOG = Logger.getLogger(TestTemplateMethodLevelInit.class);	

/**
 * Configuration/Initialization before starting suite
 * @param testContext
 * @param xmlTest
 * @throws Exception
 */
	@BeforeSuite
	public void beforeSuite(ITestContext testContext, XmlTest xmlTest) throws Exception {

		LOG.info(String.format("Suite To Be Executed Next -  %s", testContext.getSuite().getName()));		
		TestTemplate.implicitWaitInSecs = ReusableLibs.getConfigProperty("ImplicitWaitInSecs");
		TestTemplate.pageLoadTimeOutInSecs = ReusableLibs.getConfigProperty("PageLoadTimeOutInSecs");
		String extentTestVisibilityMode = ReusableLibs.getConfigProperty("extentTestVisibilityMode");

		TestTemplate.testReport = ReportFactory.getInstance(ReportType.ExtentHtml,
				ExtentTestVisibilityMode.valueOf(extentTestVisibilityMode));
	}
	
	/**
	 * Configuration/Initialization before running each test
	 * @param testContext
	 */
	@BeforeTest
	public void beforeTest(ITestContext testContext) {
		LOG.info(String.format("Thread - %d, Executes Next Test - %s ", Thread.currentThread().getId(),
				testContext.getCurrentXmlTest().getName()));
		if (((ExtentReporter) TestTemplate.testReport)
				.getExtentTestVisibilityMode() == ExtentTestVisibilityMode.TestNGTestTagAsTestsAtLeft) {
			TestTemplate.testReport
					.createTestNgXMLTestTag(String.format("%s", testContext.getCurrentXmlTest().getName()));

		}
	}

	/**
	 * Configuration/Initialization after running each test
	 * @param testContext
	 */
	@AfterTest
	public void afterTest(ITestContext testContext) {
		LOG.info(String.format("Test - %s , Completed", testContext.getCurrentXmlTest().getName()));
		TestTemplate.testReport.updateTestCaseStatus();
	}

	/**
	 * Configuration/Initialization before running each test case
	 * Driver is initialized before running each test method
	 * @param testContext
	 * @param m
	 * @throws URISyntaxException
	 */
	@BeforeMethod
	public void beforeMethod(ITestContext testContext, Method m) throws URISyntaxException {
		try {
			LOG.info(String.format("Thread - %d , Executes Next Test Method - %s", Thread.currentThread().getId(),
					m.getName()));

			WebDriver webDriver = null;

			if (TestTemplate.testReport instanceof ExtentReporter) {

				if (((ExtentReporter) TestTemplate.testReport)
						.getExtentTestVisibilityMode() == ExtentTestVisibilityMode.TestNGTestTagAsTestsAtLeft) {
					TestTemplate.testReport.initTestCase(String.format("%s", m.getName()));
				} else if (((ExtentReporter) TestTemplate.testReport)
						.getExtentTestVisibilityMode() == ExtentTestVisibilityMode.TestNGTestMethodsAsTestAtLeft) {
					TestTemplate.testReport.initTestCase(
							String.format("%s-%s", testContext.getCurrentXmlTest().getName(), m.getName()));
				}
			}

			// Use APPURL if provided in Test Suite XML
			this.url = this.getTestParameter(testContext, "APPURL");

			// Use browser specific wd as provided in Test Suite XML or else use
			// chromedriver
			String browser = this.getTestParameter(testContext, "Browser");
			webDriver = WebDriverFactory.getWebDriver(browser);
			webDriver.get(this.url);
			threadLocalWebDriver.set(webDriver);	

		} catch (Exception ex) {
			LOG.error(String.format("Exception Encountered - %s", ex.getMessage()));
			TestTemplate.testReport.logException(ex);

		} finally {
			TestTemplate.testReport.logInfo(String.format("Thread - %d , Executes Next Test Method - %s",
					Thread.currentThread().getId(), m.getName()));
		}

	}

	/**
	 * Configuration/Initialization after running each test case
	 * webdriver is killed after each test case execution
	 * @param testContext
	 * @param testResult
	 * @param m
	 * @throws Exception
	 */
	@AfterMethod
	public void afterMethod(ITestContext testContext, ITestResult testResult, Method m) throws Exception {
		LOG.info(String.format("Thread - %d , Completes Executing Test Method - %s", Thread.currentThread().getId(),
				m.getName()));
		TestTemplate.testReport.logInfo(String.format("Thread - %d , Completes Executing Test Method - %s",
				Thread.currentThread().getId(), m.getName()));
		
		try {
			threadLocalWebDriver.get().close();
		} catch (Exception ex) {
			LOG.error(
					String.format("Exception Encountered - %s, StackTrace - %s", ex.getMessage(), ex.getStackTrace()));
		}

		try {
			threadLocalWebDriver.get().quit();
		} catch (Exception ex) {
			LOG.error(
					String.format("Exception Encountered - %s, StackTrace - %s", ex.getMessage(), ex.getStackTrace()));
		}

		finally {
			TestTemplate.testReport.updateTestCaseStatus();
		}
	}

}