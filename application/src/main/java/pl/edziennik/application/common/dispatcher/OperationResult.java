package pl.edziennik.application.common.dispatcher;

public class OperationResult {

    private final boolean success;

    private OperationResult(boolean success) {
        this.success = success;
    }

    public static OperationResult success() {
        return new OperationResult(true);
    }

}
