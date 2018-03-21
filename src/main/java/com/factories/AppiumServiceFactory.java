package com.factories;

import java.io.IOException;
import java.net.ServerSocket;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

class AppiumServiceFactory {

	private AppiumDriverLocalService service = null;
	private DesiredCapabilities cap = null;
	private String IPAddress = null;
	private int port = -1;

	AppiumServiceFactory() {

	}

	AppiumServiceFactory(String IPAddress) {
		this.IPAddress = IPAddress;
	}

	AppiumServiceFactory(String IPAddress, int port) {
		this(IPAddress);
		this.port = port;
	}

	AppiumServiceFactory(String IPAddress, int port, DesiredCapabilities cap) {
		this(IPAddress, port);
		this.cap = cap;
	}

	void setIPAddress(String IPAddress) {
		this.IPAddress = IPAddress;
	}

	String getIPAddress() {
		return this.IPAddress;
	}

	void setPort(int port) {
		this.port = port;
	}

	int getPort() {
		return this.port;
	}

	void setCapabilities(DesiredCapabilities cap) {
		this.cap = cap;
	}

	DesiredCapabilities getCapabilities() {
		return this.cap;
	}

	void buildAppiumDriverLocalService() {
		AppiumServiceBuilder builder = new AppiumServiceBuilder();
		if (this.port == -1 || checkIfPortInUse(this.port)) {
			builder.usingAnyFreePort();
		} else {
			builder.usingPort(this.port);
		}
		builder.withIPAddress(this.IPAddress);
		builder.withCapabilities(this.cap);
		builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
		builder.withArgument(GeneralServerFlag.LOG_LEVEL, "error");
		this.service = AppiumDriverLocalService.buildService(builder);		
	}
	
	AppiumDriverLocalService getAppiumDriverLocalService()
	{
		return this.service;
	}
	static boolean checkIfPortInUse(int port) {

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
