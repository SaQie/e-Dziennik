package pl.edziennik.eDziennik.server.schoolclass.services.validator;

import pl.edziennik.eDziennik.server.basics.AbstractValidator;
import pl.edziennik.eDziennik.server.schoolclass.domain.dto.SchoolClassRequestApiDto;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.request.StudentSubjectRequestDto;

public interface SchoolClassValidators extends AbstractValidator<SchoolClassRequestApiDto> {

    String EXCEPTION_MESSAGE_TEACHER_NOT_BELONG_TO_SCHOOL = "teacher.not.belongs.to.school";
    String EXCEPTION_MESSAGE_TEACHER_IS_ALREADY_SUPERVISING_TEACHER = "teacher.is.already.supervising.teacher";
    String EXCEPTION_MESSAGE_SCHOOL_CLASS_ALREADY_EXIST = "school.class.already.exist";

}
