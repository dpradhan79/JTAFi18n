
package com.config;

import org.openqa.selenium.remote.CapabilityType;

import io.appium.java_client.remote.MobileCapabilityType;

/**
 * 
 * @author E001518 (Debasish Pradhan, Architect)
 *
 */
public interface ITestParamsConstants {

	String BROWSER = CapabilityType.BROWSER_NAME;
	String APPURL = "appURL";
	String LOCALE_COUNTRY = "localeCountry";
	String LOCALE_LANGUAGE = "localeLanguage";
	String CHROME_DRIVER_EXE = "chromeDriverExe";
	String IE_DRIVER_EXE = "IEDriverExe";
	String FF_DRIVER_EXE = "FFDriverExe";
	String IMPLICIT_WAIT_IN_SECS = "implicitWaitInSecs";
	String PAGE_LOAD_TIME_OUT_IN_SECS = "pageLoadTimeOutInSecs";
	String SCREENSHOT_LOCATION = "screenshotLocation";
	String SCREENSHOT_PICTURE_FORMAT = "screenshotPictureFormat";
	String HTML_REPORT = "htmlReport";
	String BOOL_APPEND_EXISTING = "boolAppendExisting";
	String IS_CIGNITI_LOGO_REQD = "isCignitiLogoRequired";
	String EXTENT_CONFIG_FILE = "extentConfigFile";
	String EXTENT_TEST_VISIBILITY_MODE = "extentTestVisibilityMode";
	String AUTOMATION_KIT = "automationKit";
	String APPIUM_HUB = "appiumHub";
	String APPIUM_IPADDRESS = "appiumIPAddress";
	String APPIUM_PORT = "appiumPort";
	String APPIUM_AVD = "avd";
	String APPIUM_AUTOMATION_NAME = MobileCapabilityType.AUTOMATION_NAME;
	String APPIUM_PLATFORM_NAME = MobileCapabilityType.PLATFORM_NAME;
	String APPIUM_PLATFORM_VERSION = MobileCapabilityType.PLATFORM_VERSION;
	String APPIUM_DEVICE_NAME = MobileCapabilityType.DEVICE_NAME;
	String APPIUM_NORESET = "noReset";
	String APPIUM_DEVICE_READY_TIMEOUT = "deviceReadyTimeout";
	String APPIUM_AVD_READY_TIMEOUT = "avdReadyTimeout";
	String APPIUM_NODEJS_EXECUTABLE_LOCATION = "nodeJSLocation";
	String APPIUM_EXECUTABLE_JS_FILE = "appiumExecutableJS";
	
}
