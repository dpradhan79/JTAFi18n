package com.factories;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

import org.openqa.selenium.remote.DesiredCapabilities;

import com.config.ITestParamsConstants;
import com.utilities.ReusableLibs;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class AppiumServiceFactory {

	private AppiumDriverLocalService service = null;
	private DesiredCapabilities cap = null;
	private String IPAddress = null;
	private int port = -1;

	public 	AppiumServiceFactory() {

	}

	public AppiumServiceFactory(String IPAddress) {
		this.IPAddress = IPAddress;
	}

	public AppiumServiceFactory(String IPAddress, int port) {
		this(IPAddress);
		this.port = port;
	}

	public AppiumServiceFactory(String IPAddress, int port, DesiredCapabilities cap) {
		this(IPAddress, port);
		this.cap = cap;
	}
	
	public AppiumServiceFactory(String IPAddress, DesiredCapabilities cap) {
		this(IPAddress);
		this.cap = cap;
	}

	public void setIPAddress(String IPAddress) {
		this.IPAddress = IPAddress;
	}

	public String getIPAddress() {
		return this.IPAddress;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getPort() {
		return this.port;
	}

	public void setCapabilities(DesiredCapabilities cap) {
		this.cap = cap;
	}

	public DesiredCapabilities getCapabilities() {
		return this.cap;
	}

	public AppiumDriverLocalService buildAppiumDriverLocalService() {
		AppiumServiceBuilder builder = new AppiumServiceBuilder();
		if (this.port == -1 || checkIfPortInUse(this.port)) {
			builder.usingAnyFreePort();
		} else {
			builder.usingPort(this.port);
		}
		builder.withIPAddress(this.IPAddress);
		builder.usingDriverExecutable(new File(ReusableLibs.getConfigProperty(ITestParamsConstants.APPIUM_NODEJS_EXECUTABLE_LOCATION)));
		builder.withAppiumJS(new File(ReusableLibs.getConfigProperty(ITestParamsConstants.APPIUM_EXECUTABLE_JS_FILE)));
		builder.withCapabilities(this.cap);
		builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
		builder.withArgument(GeneralServerFlag.LOG_LEVEL, "error");
		this.service = AppiumDriverLocalService.buildService(builder);		
		return this.service;
	}
	
	public AppiumDriverLocalService getAppiumDriverLocalService()
	{
		return this.service;
	}
	private static boolean checkIfPortInUse(int port) {

		boolean isPortInUse = false;
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(port);
			serverSocket.close();
		} catch (IOException e) {
			// If control comes here, then it means that the port is in use
			isPortInUse = true;
		} finally {
			serverSocket = null;
		}
		return isPortInUse;
	}

}
