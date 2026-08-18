package zazerutus.java.core.allure;

import io.qameta.allure.listener.TestLifecycleListener;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.TestResult;

public class AllureAttachmentListener implements TestLifecycleListener {

    @Override
    public void beforeTestStop(TestResult result) {
        if (result.getStatus() == Status.FAILED) {
            AllureUtils.attachScreenshot();
            AllureUtils.attachPageSource();
        }
    }
}
