package dweb.test.templates;

import java.lang.reflect.Method;
import java.net.URISyntaxException;

import org.apache.log4j.Logger;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.xml.XmlTest;

import com.config.IConstants;
import com.config.ITestParamsConstants;
import com.factories.AppiumServiceFactory;
import com.factories.WebDriverFactory;
import com.testreport.ExtentReporter;
import com.testreport.ExtentReporter.ExtentTestVisibilityMode;
import com.testreport.ReportFactory;
import com.testreport.ReportFactory.ReportType;
import com.utilities.ReusableLibs;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.service.local.AppiumDriverLocalService;

/**
 * Class with configuration for test execution
 * 
 * @author E001518 - Debasish Pradhan (Architect)
 *
 */
public abstract class TestTemplateMethodLevelInit extends TestTemplate {

	private static final Logger LOG = Logger.getLogger(TestTemplateMethodLevelInit.class);
	private AppiumDriverLocalService appiumDriverLocalService = null;

	/**
	 * Configuration/Initialization before starting suite
	 * 
	 * @param testContext
	 * @param xmlTest
	 * @throws Exception
	 */
	@BeforeSuite
	protected void beforeSuite(ITestContext testContext, XmlTest xmlTest) throws Exception {

		LOG.info(String.format("Suite To Be Executed Next -  %s", testContext.getSuite().getName()));
		TestTemplate.implicitWaitInSecs = ReusableLibs.getConfigProperty(ITestParamsConstants.IMPLICIT_WAIT_IN_SECS);
		TestTemplate.pageLoadTimeOutInSecs = ReusableLibs
				.getConfigProperty(ITestParamsConstants.PAGE_LOAD_TIME_OUT_IN_SECS);
		String extentTestVisibilityMode = ReusableLibs
				.getConfigProperty(ITestParamsConstants.EXTENT_TEST_VISIBILITY_MODE);

		TestTemplate.testReport = ReportFactory.getInstance(ReportType.ExtentHtml,
				ExtentTestVisibilityMode.valueOf(extentTestVisibilityMode));
	}

	@AfterSuite
	protected void afterSuite(ITestContext testContext) {
		LOG.info(String.format("Suite - %s , Completed", testContext.getSuite().getName()));
		TestTemplate.testReport.updateTestCaseStatus();
	}

	/**
	 * Configuration/Initialization before running each test
	 * 
	 * @param testContext
	 */
	@BeforeTest
	protected void beforeTest(ITestContext testContext) {
		LOG.info(String.format("Thread - %d, Executes Next Test - %s ", Thread.currentThread().getId(),
				testContext.getCurrentXmlTest().getName()));
		if (((ExtentReporter) TestTemplate.testReport)
				.getExtentTestVisibilityMode() == ExtentTestVisibilityMode.TestNGTestTagAsTestsAtLeft) {
			TestTemplate.testReport
					.createTestNgXMLTestTag(String.format("%s", testContext.getCurrentXmlTest().getName()));

		}
		String automationKit = this.getTestParameter(testContext, ITestParamsConstants.AUTOMATION_KIT);	
		if (automationKit.equalsIgnoreCase("appium")) {
			int currentAttempt = 1;
			boolean isServerStarted = false;
			while (currentAttempt <= IConstants.APPIUM_START_MAX_ATTEMPT && isServerStarted == false) {
				LOG.info(String.format("Thread - %d, Attempts To Start Appium Server For %d time",
						Thread.currentThread().getId(), currentAttempt));
				// start appium server if automation is on mobile
				if (this.getTestParameter(testContext, ITestParamsConstants.AUTOMATION_KIT)
						.equalsIgnoreCase("appium")) {
					try {
						this.appiumDriverLocalService = new AppiumServiceFactory(
								this.getTestParameter(testContext, "appiumIPAddress"),
								this.convertTestParamsToCapabilities(testContext)).buildAppiumDriverLocalService();
						this.appiumDriverLocalService.start();
						isServerStarted = true;
						LOG.info(String.format("Appium Server Started At URL - %s",
								this.appiumDriverLocalService.getUrl()));
					} catch (Exception ex) {
						LOG.error(ex);

					}
				}
				currentAttempt++;
			}
		}
	}

	/**
	 * Configuration/Initialization after running each test
	 * 
	 * @param testContext
	 */
	@AfterTest
	protected void afterTest(ITestContext testContext) {
		LOG.info(String.format("Test - %s , Completed", testContext.getCurrentXmlTest().getName()));
		TestTemplate.testReport.updateTestCaseStatus();
		if (threadLocalWebDriver.get() != null) {
			threadLocalWebDriver.get().quit();
		}

		// stop appium server, if running
		if (this.appiumDriverLocalService != null) {
			this.appiumDriverLocalService.stop();
		}
	}

	/**
	 * Configuration/Initialization before running each test case Driver is
	 * initialized before running each test method
	 * 
	 * @param testContext
	 * @param m
	 * @throws URISyntaxException
	 */
	@BeforeMethod
	protected void beforeMethod(ITestContext testContext, Method m) throws URISyntaxException {

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
			String url = this.getTestParameter(testContext, ITestParamsConstants.APPURL);

			String automationKit = this.getTestParameter(testContext, ITestParamsConstants.AUTOMATION_KIT);

			if (automationKit.equalsIgnoreCase("selenium")) {
				webDriver = WebDriverFactory.getWebDriver(this.getAllTestParameters(testContext));
			} else if (automationKit.equalsIgnoreCase("appium")) {
				int currentAttempt = 1;
				boolean isAppiumDriverInitSuccessful = false;
				while (currentAttempt <= IConstants.APPIUM_START_MAX_ATTEMPT && isAppiumDriverInitSuccessful == false) {
					LOG.info(String.format("Thread - %d, Attempts To Initialize Appium For %d time",
							Thread.currentThread().getId(), currentAttempt));
					try {
						webDriver = new AppiumDriver<MobileElement>(this.appiumDriverLocalService,
								this.convertTestParamsToCapabilities(testContext));
						isAppiumDriverInitSuccessful = true;
						LOG.info(String.format("Thread - %d, Succeeds To Initialize Appium In %d Attempt",
								Thread.currentThread().getId(), currentAttempt));
					} catch (Exception ex) {
						LOG.error(String.format("Thread - %d Encounters Exception - %s", Thread.currentThread().getId(),
								ex.getMessage()));
						Thread.sleep(1000);
					}
					currentAttempt++;
				}
			}

			try {
				webDriver.get(url);
			} catch (TimeoutException ex) {
				LOG.error(String.format("Browser Takes More Time To Load, Time Out Defined - %s",
						TestTemplate.pageLoadTimeOutInSecs));
			}
			threadLocalWebDriver.set(webDriver);

		} catch (Exception ex) {
			LOG.error(String.format("Exception Encountered - %s", ex.getMessage()));
			TestTemplate.testReport.logException(ex);

		} finally {
			TestTemplate.testReport.logInfo(String.format(
					"Thread - %d , Executes Next Test Method - %s On Browser - %s", Thread.currentThread().getId(),
					m.getName(), this.getTestParameter(testContext, ITestParamsConstants.BROWSER)));
		}

	}

	/**
	 * Configuration/Initialization after running each test case webdriver is killed
	 * after each test case execution
	 * 
	 * @param testContext
	 * @param testResult
	 * @param m
	 * @throws Exception
	 */
	@AfterMethod
	protected void afterMethod(ITestContext testContext, ITestResult testResult, Method m) throws Exception {
		LOG.info(String.format("Thread - %d , Completes Executing Test Method - %s", Thread.currentThread().getId(),
				m.getName()));
		TestTemplate.testReport.logInfo(String.format(
				"Thread - %d , Completes Executing Test Method - %s On Browser - %s", Thread.currentThread().getId(),
				m.getName(), this.getTestParameter(testContext, ITestParamsConstants.BROWSER)));

		try {
			threadLocalWebDriver.get().close();
			if (threadLocalWebDriver.get() instanceof AppiumDriver) {
				((AppiumDriver<?>) threadLocalWebDriver.get()).closeApp();
			}
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
