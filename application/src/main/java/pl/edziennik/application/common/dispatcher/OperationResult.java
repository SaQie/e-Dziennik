package pl.edziennik.application.common.dispatcher;


import pl.edziennik.common.valueobject.Identifier;

public record OperationResult(
        Identifier identifier,
        boolean isSuccess
) {

    public static OperationResult success(Identifier id) {
        return new OperationResult(id, true);
    }

    public static OperationResult success() {
        return new OperationResult(null, true);
    }

}
