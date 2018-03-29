package dweb.test.i18n.breyers.functionaltests;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.bag.SynchronizedBag;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.config.IConstants;
import com.excel.Xls_Reader;
import com.google.common.io.Resources;
import com.utilities.ReusableLibs;

import dweb.test.templates.TestTemplate;
import dweb.test.templates.TestTemplateMethodLevelInit;

public class TestBrokenLinks extends TestTemplateMethodLevelInit {
	private static final Logger LOG = Logger.getLogger(TestBrokenLinks.class);
	Map<String, Integer> mapUrlStatus = new HashMap<String, Integer>();
	Map<String, Map> mapLinks = new HashMap<String, Map>();
	Integer rowCounter = 2;
	ThreadLocal<Integer> threadLocalRowCounter = new InheritableThreadLocal<>();
	@Test(dataProvider = "getUrlsFromExcel")
	public void testBrokenLinks(Hashtable<String, String> data) throws URISyntaxException
	{
		/** Excel **/
		
		URL urlFilePath = Resources.getResource(String.format("%s/%s", IConstants.TEST_DATA_LOCATION, IConstants.TEST_DATA_EXCEL_FILE));
		String filePath = Paths.get(urlFilePath.toURI()).toFile().getAbsolutePath();
		Xls_Reader xlsReader = new Xls_Reader(filePath);
		
		/****/
		
		String link = data.get("URL");
		LOG.info(String.format("URL Selected To Find Links - %s", link));
		TestTemplate.threadLocalWebDriver.get().get(link);
		
		List<WebElement> listLinks = TestTemplate.threadLocalWebDriver.get().findElements(By.tagName("a"));
		for(WebElement weLink : listLinks)
		{
			String url = weLink.getAttribute("href");
			int respCode = -1;
			try {
				respCode = ReusableLibs.getResponseCode(url);
				mapUrlStatus.put(url, respCode);
				synchronized(this)
				{
					threadLocalRowCounter.set(rowCounter);
					LOG.info(String.format("Link - %s, Status - %d", url, respCode));
					xlsReader.setCellData("LinksStatus", "URL", threadLocalRowCounter.get(), link);
					xlsReader.setCellData("LinksStatus", "Link",threadLocalRowCounter.get(), url);
					xlsReader.setCellData("LinksStatus", "Status", threadLocalRowCounter.get(), String.valueOf(respCode));
					rowCounter ++;
					//threadLocalRowCounter.set(rowCounter);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		mapLinks.put(link, mapUrlStatus);		
		
	}
	

}
