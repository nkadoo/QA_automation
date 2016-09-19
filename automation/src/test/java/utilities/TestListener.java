package utilities;

import java.util.Set;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

	@Override
	public void onFinish(ITestContext context) {

		Set<ITestResult> failedTests = context.getSkippedTests().getAllResults();
		for (ITestResult temp : failedTests) {
			ITestNGMethod method = temp.getMethod();
			if (context.getSkippedTests().getResults(method).size() > 1) {
				failedTests.remove(temp);
			} 
			
			if (context.getPassedTests().getResults(method).size() > 0) {
					failedTests.remove(temp);
				}
				
			if (context.getFailedTests().getResults(method).size() > 0) {
					failedTests.remove(temp);
				}
			}
	}

	@Override
	public void onStart(ITestContext arg0) {

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {

	}

	@Override
	public void onTestFailure(ITestResult result) {

	}

	@Override
	public void onTestSkipped(ITestResult arg0) {

	}

	@Override
	public void onTestStart(ITestResult arg0) {

	}

	@Override
	public void onTestSuccess(ITestResult arg0) {

	}

}
