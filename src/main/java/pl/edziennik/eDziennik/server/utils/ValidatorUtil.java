package pl.edziennik.eDziennik.server.utils;

import pl.edziennik.eDziennik.server.basics.dto.ApiErrorDto;
import pl.edziennik.eDziennik.server.exceptions.ExceptionType;

public class ValidatorUtil {

    public static ApiErrorDto makeAndThrowBusinessException(String className, String message){
        return ApiErrorDto.builder()
                .errorThrownedBy(className)
                .exceptionType(ExceptionType.BUSINESS)
                .thrownImmediately(true)
                .cause(message)
                .build();
    }

}
