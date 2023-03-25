package pl.edziennik.eDziennik.server.utils;

import pl.edziennik.eDziennik.server.basics.dto.ApiValidationResult;
import pl.edziennik.eDziennik.server.exceptions.ExceptionType;

public class ValidatorUtil {

    public static ApiValidationResult makeAndThrowBusinessException(String className, String message){
        return ApiValidationResult.builder()
                .errorThrownedBy(className)
                .exceptionType(ExceptionType.BUSINESS)
                .thrownImmediately(true)
                .cause(message)
                .build();
    }

}
