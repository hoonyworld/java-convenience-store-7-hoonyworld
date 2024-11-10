package store.template;

import store.view.OutputView;
import java.util.function.Supplier;

public class RetryTemplate {
    private final OutputView outputView;

    public RetryTemplate(OutputView outputView) {
        this.outputView = outputView;
    }

    public <T> T execute(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                outputView.printExceptionMessage(e);
            }
        }
    }

    public void execute(Runnable action) {
        while (true) {
            try {
                action.run();
                break;
            } catch (IllegalArgumentException e) {
                outputView.printExceptionMessage(e);
            }
        }
    }
}
