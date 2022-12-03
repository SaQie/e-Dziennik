package pl.edziennik.eDziennik.server.student.services.validator;

import pl.edziennik.eDziennik.server.basics.AbstractValidator;
import pl.edziennik.eDziennik.server.student.domain.dto.StudentRequestApiDto;

public interface StudentValidators extends AbstractValidator<StudentRequestApiDto> {

    String EXCEPTION_MESSAGE_STUDENT_ALREADY_EXIST = "api.already.exist";


}
