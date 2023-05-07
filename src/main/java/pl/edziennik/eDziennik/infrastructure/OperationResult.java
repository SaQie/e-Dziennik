package pl.edziennik.eDziennik.infrastructure;

public class OperationResult {

    private final boolean success;

    private OperationResult(boolean success) {
        this.success = success;
    }

    public static OperationResult success() {
        return new OperationResult(true);
    }

}
