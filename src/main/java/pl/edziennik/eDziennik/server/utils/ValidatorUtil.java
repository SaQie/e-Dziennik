package pl.edziennik.eDziennik.server.utils;

import pl.edziennik.eDziennik.server.basics.dto.ApiErrorsDto;
import pl.edziennik.eDziennik.server.exceptions.ExceptionType;

public class ValidatorUtil {

    public static ApiErrorsDto makeAndThrowBusinessException(String className, String message){
        return ApiErrorsDto.builder()
                .errorThrownedBy(className)
                .exceptionType(ExceptionType.BUSINESS)
                .thrownImmediately(true)
                .cause(message)
                .build();
    }

}
