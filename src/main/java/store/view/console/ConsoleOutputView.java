package store.view.console;

import store.view.OutputView;

public class ConsoleOutputView implements OutputView {
    @Override
    public void printExceptionMessage(Exception e) {
        System.out.println(e.getMessage());
    }
}
