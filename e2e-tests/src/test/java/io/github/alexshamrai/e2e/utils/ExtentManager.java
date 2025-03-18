package io.github.alexshamrai.e2e.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.log4j.Logger;

public class ExtentManager {
    private static final Logger logger = Logger.getLogger(ExtentManager.class);
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static final String reportFilePath = "src/report/extent-report.html";

    public static void init() {
        if (extent == null) {
            createInstance();
        }
    }

    private static void createInstance() {
        logger.info("Initializing Extent Reports instance");
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportFilePath);
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        logger.info("Extent Reports instance created successfully");
    }

    public static void createTest(String testName) {
        logger.info("Creating test: " + testName);
        ExtentTest extentTest = extent.createTest(testName);
        test.set(extentTest);
    }

    public static ExtentTest getTest() {
        return test.get();
    }

    public static void flush() {
        if (extent != null) {
            logger.info("Flushing Extent Reports");
            extent.flush();
        }
    }

    public static void logStep(String stepDescription) {
        logger.info("Logging step: " + stepDescription);
        getTest().info(stepDescription);
    }

    public static void logError(Throwable throwable) {
        logger.error("Logging error: ", throwable);
        getTest().fail(throwable);
    }
}