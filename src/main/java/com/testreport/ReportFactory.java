package com.testreport;

import org.apache.log4j.Logger;

import com.config.ITestParamsConstants;
import com.testreport.ExtentReporter.ExtentTestVisibilityMode;
import com.utilities.ReusableLibs;

/**
 * 
 * @author E001518  - Debasish Pradhan (Architect)
 *
 */

public class ReportFactory {
	
	private static IReporter testReport = null;	
	private static final Logger LOG = Logger.getLogger(ReportFactory.class);
	
	public enum ReportType
	{
		CignitiHtml,
		ExtentHtml
	}
	
	private ReportFactory()
	{
		
	}
	
	public synchronized static IReporter getInstance(ReportType reportType, ExtentTestVisibilityMode extentTestVisibilityMode) throws Exception
	{
		if(ReportFactory.testReport == null)
		{
			switch(reportType)	
			{
				case ExtentHtml :
					
					String htmlReportName = ReusableLibs.getConfigProperty(ITestParamsConstants.HTML_REPORT);
					String screenShotLocation = ReusableLibs.getConfigProperty(ITestParamsConstants.SCREENSHOT_LOCATION);		
					String strBoolAppendExisting = ReusableLibs.getConfigProperty(ITestParamsConstants.BOOL_APPEND_EXISTING);
					String strIsCignitiLogoRequired = ReusableLibs.getConfigProperty(ITestParamsConstants.IS_CIGNITI_LOGO_REQD);
					String extentConfigFile = ReusableLibs.getConfigProperty(ITestParamsConstants.EXTENT_CONFIG_FILE);	
					boolean boolAppendExisting = false;
					boolean boolIsCignitiLogoRequired = false;
					if(strBoolAppendExisting !=null && strBoolAppendExisting.equalsIgnoreCase("true"))
					{
						boolAppendExisting = true;
					}
					
					if(strIsCignitiLogoRequired !=null && strIsCignitiLogoRequired.equalsIgnoreCase("true"))
					{
						boolIsCignitiLogoRequired = true;
					}
					
					ReusableLibs.makeDir(screenShotLocation);
					String filePath = String.format("%s", htmlReportName);
					ReportFactory.testReport = new ExtentReporter(filePath, extentConfigFile, boolAppendExisting, boolIsCignitiLogoRequired, extentTestVisibilityMode);
										
					break;				
				
				default:
					throw new Exception("Html Report Other Than Extent Is Not Currently Supported...");
					
			}
			
		}
		return ReportFactory.testReport;
			
	}
	
	
}
