package pl.edziennik.eDziennik.server.teacher.services.validator;

import pl.edziennik.eDziennik.server.basics.AbstractValidator;
import pl.edziennik.eDziennik.server.teacher.domain.dto.TeacherRequestApiDto;

public interface TeacherValidators extends AbstractValidator<TeacherRequestApiDto> {

    String TEACHER_PESEL_NOT_UNIQUE_VALIDATOR_NAME = TeacherPeselNotUniqueValidator.class.getSimpleName();

    String EXCEPTION_MESSAGE_PESEL_NOT_UNIQUE = "teacher.pesel.not.unique";

}
