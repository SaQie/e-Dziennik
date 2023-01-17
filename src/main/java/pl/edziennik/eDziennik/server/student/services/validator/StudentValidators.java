package pl.edziennik.eDziennik.server.student.services.validator;

import pl.edziennik.eDziennik.server.basics.AbstractValidator;
import pl.edziennik.eDziennik.server.student.domain.dto.StudentRequestApiDto;

public interface StudentValidators extends AbstractValidator<StudentRequestApiDto> {

    String EXCEPTION_MESSAGE_SCHOOL_CLASS_NOT_BELONG_TO_SCHOOL = "school.class.not.belong.to.school";
    String EXCEPTION_MESSAGE_PESEL_NOT_UNIQUE = "student.pesel.not.unique";


}
