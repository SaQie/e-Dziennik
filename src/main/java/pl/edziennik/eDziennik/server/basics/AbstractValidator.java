package pl.edziennik.eDziennik.server.basics;

import pl.edziennik.eDziennik.exceptions.BusinessExceptionPojo;

import java.util.List;

public interface AbstractValidator<T> {


    ValidatorPriority getPriority();

    void validate(T t, List<BusinessExceptionPojo> errors, boolean throwException);


}
