package utilities;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestFailureLogger implements ITestListener {

    private static final String LOG_FILE = "test-output/failed-tests-log.txt";

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String reason = result.getThrowable() != null ? result.getThrowable().toString() : "Unknown Reason";

        String logEntry = String.format("[%s] Test FAILED - %s: %s%n",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                testName,
                reason);

        // Print to console
        System.out.println(logEntry);

        // Append to log file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            writer.write(logEntry);
        } catch (IOException e) {
            System.err.println("Error writing to failed-tests-log.txt: " + e.getMessage());
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("Test execution finished: " + context.getName());
    }
}
