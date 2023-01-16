package pl.edziennik.eDziennik.server.teacher.services.validator;

import pl.edziennik.eDziennik.server.basics.AbstractValidator;
import pl.edziennik.eDziennik.server.teacher.domain.dto.TeacherRequestApiDto;

public interface TeacherValidators extends AbstractValidator<TeacherRequestApiDto> {

    String EXCEPTION_MESSAGE_TEACHER_ALREADY_EXIST = "api.teacher.already.exist";
    String EXCEPTION_MESSAGE_PESEL_NOT_UNIQUE = "teacher.pesel.not.unique";
    String EXCEPTION_MESSAGE_TEACHER_WITH_EMAIL_ALREADY_EXIST = "teacher.email.already.exist";


}
