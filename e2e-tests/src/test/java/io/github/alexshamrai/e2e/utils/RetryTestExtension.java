package io.github.alexshamrai.e2e.utils;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;

public class RetryTestExtension implements TestExecutionExceptionHandler {

    private static final Logger logger = Logger.getLogger(RetryTestExtension.class);
    private static final int MAX_RETRIES = 2;
    private int retryCount = 0;

    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        if (retryCount < MAX_RETRIES) {
            retryCount++;
            logger.info("Retrying test " + context.getDisplayName() + " for the " + retryCount + " time.");
            try {
                Thread.sleep(2000);
                context.getStore(ExtensionContext.Namespace.GLOBAL).put("RETRY_COUNT", retryCount);
                context.getTestMethod().ifPresent(method -> {
                    try {
                        method.invoke(context.getRequiredTestInstance());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
            } catch (Exception e) {
                handleTestExecutionException(context, e);
            }
        } else {
            throw throwable;
        }
    }
}