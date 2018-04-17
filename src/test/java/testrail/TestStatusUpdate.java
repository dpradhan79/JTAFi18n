package testrail;

import java.util.List;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.github.ppadial.testrail.client.TestRailClient;
import com.github.ppadial.testrail.client.TestRailException;
import com.github.ppadial.testrail.client.apiClient.ApiClient;
import com.github.ppadial.testrail.client.model.TRCase;
import com.github.ppadial.testrail.client.model.TRRun;
import com.github.ppadial.testrail.client.model.TRTest;

import dweb.test.templates.TestTemplate;

public class TestStatusUpdate extends TestTemplate {
	String testRailUrl = "https://mychgstage.testrail.net";
	String userName = "debasish.pradhan@cigniti.com";
	String password = "!Mpr0veMent";
	final int testRunId = 933;

	@Test(enabled = true)
	public void UpdateTestStatus(ITestContext testContext) {
		this.getAllTestParameters(testContext).forEach((k, v) -> {
			String testCaseName = k;
			Integer testCaseStatus = Integer.parseInt(v);
			ApiClient apiClient = new ApiClient(this.testRailUrl, this.userName, this.password);
			TestRailClient testRailClient = new TestRailClient(apiClient);

			try {
				TRRun testRun = testRailClient.runApi().getRun(this.testRunId);

				List<TRCase> listTRCase = testRailClient.caseApi().getCases(testRun.projectId, testRun.suiteId, 0,
						null);
				for (TRCase testCase : listTRCase) {
					System.out.println(String.format("Test Case Title = %s, Id = %s", testCase.title, testCase.id));
					// Update Automation Status
					if (testCase.title.equalsIgnoreCase(testCaseName)) {
						List<TRTest> listTests = testRailClient.testApi().getTests(this.testRunId);
						for (TRTest test : listTests) {
							if (test.caseId == testCase.id) {
								// update status here
								testRailClient.resultApi().addResultForCase(this.testRunId, test.caseId, testCaseStatus,
										null, null, (long) 0, null, null, null);
								break;
							}
						}
					}
				}

			} catch (TestRailException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

	}

}
