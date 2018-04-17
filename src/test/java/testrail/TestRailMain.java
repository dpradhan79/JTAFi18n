package testrail;

import java.net.URISyntaxException;
import java.util.List;

import org.testng.TestNG;

import com.beust.jcommander.internal.Lists;

public class TestRailMain {

	public static void main(String[] args) throws URISyntaxException {

		// Initialize TestNG
		TestNG testNG = new TestNG();

		// add testng xml file
		List<String> listSuites = Lists.newArrayList();
		listSuites.add("testngTestRail.xml");
		testNG.setTestSuites(listSuites);
		testNG.run();

	}

}
