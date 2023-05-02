package pl.edziennik.eDziennik.domain.teacher.services.validator;

import pl.edziennik.eDziennik.server.basic.validator.AbstractValidator;
import pl.edziennik.eDziennik.domain.teacher.dto.TeacherRequestApiDto;

public interface TeacherValidators extends AbstractValidator<TeacherRequestApiDto> {

    String TEACHER_PESEL_NOT_UNIQUE_VALIDATOR_NAME = TeacherPeselNotUniqueValidator.class.getSimpleName();

    String EXCEPTION_MESSAGE_PESEL_NOT_UNIQUE = "teacher.pesel.not.unique";

}
