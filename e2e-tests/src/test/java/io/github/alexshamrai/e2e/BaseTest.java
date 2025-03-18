package io.github.alexshamrai.e2e;

import io.github.alexshamrai.e2e.testData.CommonTestData;
import io.github.alexshamrai.e2e.utils.ExtentManager;
import io.github.alexshamrai.e2e.utils.RetryTestExtension;
import io.restassured.RestAssured;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@ExtendWith(RetryTestExtension.class)
public abstract class BaseTest {
    public static final Logger logger = Logger.getLogger(BaseTest.class);

    @BeforeAll
    public void setUp() {
        logger.info("Initializing test environment...");
        RestAssured.baseURI = CommonTestData.ORDER_SERVICE_URL;
        ExtentManager.init();
        logger.info("Test environment setup complete.");
    }

    @BeforeEach
    public void logTestStart(TestInfo testInfo) {
        logger.info("Start test: " + testInfo.getDisplayName());
        ExtentManager.createTest(testInfo.getDisplayName());
    }

    @AfterEach
    public void logTestFinish(TestInfo testInfo) {
        logger.info("Test finished: " + testInfo.getDisplayName());
        ExtentManager.getTest().info("Test completed: " + testInfo.getDisplayName());
        logger.info("------------------------------------------------");
    }

    @AfterAll
    public void tearDown() {
        ExtentManager.flush();
        logger.info("All tests completed.");
    }
}
