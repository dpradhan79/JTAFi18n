package dweb.test.templates;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.iterators.EntrySetMapIterator;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.excel.Xls_Reader;
import com.gargoylesoftware.htmlunit.javascript.host.Set;
import com.google.common.io.Resources;
import com.testreport.IReporter;
import com.utilities.ReusableLibs;
import com.utilities.TestUtil;

/**
 * 
 * Class for reusable test function to be used in test scripts
 * 
 * @author E001518
 *
 */
public abstract class TestTemplate {

	private static final Logger LOG = Logger.getLogger(TestTemplate.class);
	protected static IReporter testReport = null;
	protected String ChromeDriverExe = null;
	// protected String url = null;
	protected static String implicitWaitInSecs = null;
	protected static String pageLoadTimeOutInSecs = null;
	public static ThreadLocal<WebDriver> threadLocalWebDriver = new ThreadLocal<WebDriver>();

	/**
	 * get parameter from either test context or framework configuration file where
	 * test context parameter overrides framework configuration parameter
	 * 
	 * @param testContext
	 * @param parameter
	 * @return
	 */
	protected String getTestParameter(ITestContext testContext, String parameter) {
		String parameterVal = testContext.getCurrentXmlTest().getParameter(parameter) == null
				? ReusableLibs.getConfigProperty(parameter)
				: testContext.getCurrentXmlTest().getParameter(parameter);
		LOG.info(String.format("Test Execution Input Parameter = %s, Value = %s", parameter, parameterVal));
		return parameterVal;
	}

	protected Map<String, String> getAllTestParameters(ITestContext testContext) {
		return testContext.getCurrentXmlTest().getAllParameters();
	}

	protected Map<String, String> getAllTestParameters(ITestContext testContext, String pattern) {
		Map<String, String> mapMobileParams = new HashMap<String, String>();
		this.getAllTestParameters(testContext).forEach((k, v) -> {
			if (k.matches(pattern)) {
				mapMobileParams.put(k, v);
			}
		});

		return mapMobileParams;
	}

	/**
	 * Dataprovider to return data matrix from excel
	 * 
	 * @return
	 * @throws URISyntaxException
	 */
	@DataProvider(name = "getDataFromExcel", parallel = true)
	protected Object[][] getDataFromExcel() throws URISyntaxException {
		URL urlFilePath = Resources.getResource("testdata/WebAutomationTestData.xlsx");
		String filePath = Paths.get(urlFilePath.toURI()).toFile().getAbsolutePath();
		Xls_Reader xlsReader = new Xls_Reader(filePath);
		Object[][] objMetrics = TestUtil.getData("WriteReview", xlsReader, "ProductItemReview");
		return objMetrics;
	}

	/**
	 * Returns screenshot name for screenshot being captured
	 * 
	 * @return
	 */
	protected String getScreenShotName() {
		String screenShotLocation = ReusableLibs.getConfigProperty("ScreenshotLocation");
		String fileExtension = ReusableLibs.getConfigProperty("ScreenshotPictureFormat");
		synchronized (this) {
			String screenShotName = ReusableLibs.getScreenshotFile(screenShotLocation, fileExtension);
			LOG.debug(String.format("ScreenShot Name For Captured Screen Shot = %s", screenShotName));
			return screenShotName;
		}
	}

}
