package pl.edziennik.application.mock;

import pl.edziennik.common.valueobject.Identifier;
import pl.edziennik.infrastructure.spring.ResourceCreator;
import pl.edziennik.infrastructure.validator.ValidationError;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

public class ResourceCreatorMock extends ResourceCreator {

    @Override
    public String of(String errorCode, Object... objects) {
        return errorCode;
    }

    @Override
    public String of(String errorMessageKey) {
        return errorMessageKey;
    }

    @Override
    public ValidationError notFoundError(String field, Identifier id) {
        return new ValidationError(field, "not.found.message", ErrorCode.OBJECT_NOT_EXISTS.errorCode());
    }
}
