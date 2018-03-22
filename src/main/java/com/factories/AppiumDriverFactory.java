package com.factories;

import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.service.local.AppiumDriverLocalService;

public class AppiumDriverFactory {

	private AppiumServiceFactory appServiceFactory = null;

	public AppiumDriverFactory(String IPAddress, int port, DesiredCapabilities cap) {
		this.appServiceFactory = new AppiumServiceFactory(IPAddress, port, cap);
	}

	AppiumServiceFactory getAppiumServiceFactory()
	{
		return this.appServiceFactory;
	}
	public AppiumDriver<MobileElement> getAppiumDriver() throws Exception {
		this.appServiceFactory.buildAppiumDriverLocalService();
		AppiumDriverLocalService appiumDriverLocalService = this.appServiceFactory.getAppiumDriverLocalService();
		appiumDriverLocalService.start();
		URL url = appiumDriverLocalService.getUrl();
		AppiumDriver<MobileElement> appiumDriver = new AppiumDriver<MobileElement>(new URL (String.format("http://%s:%s/wd/hub", this.appServiceFactory.getIPAddress(), this.appServiceFactory.getPort())), this.appServiceFactory.getCapabilities());
		return appiumDriver;

	}

}
