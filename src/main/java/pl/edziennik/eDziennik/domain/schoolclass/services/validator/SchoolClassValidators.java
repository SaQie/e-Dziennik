package pl.edziennik.eDziennik.domain.schoolclass.services.validator;

import pl.edziennik.eDziennik.server.basics.validator.AbstractValidator;
import pl.edziennik.eDziennik.domain.schoolclass.dto.SchoolClassRequestApiDto;

public interface SchoolClassValidators extends AbstractValidator<SchoolClassRequestApiDto> {

    String SCHOOL_CLASS_ALREADY_EXISTS_VALIDATOR_NAME = SchoolClassAlreadyExistsValidator.class.getSimpleName();
    String TEACHER_IS_ALREADY_SUPERVISING_TEACHER_VALIDATOR_NAME = TeacherIsAlreadySupervisingTeacherValidator.class.getSimpleName();
    String TEACHER_NOT_BELONGS_TO_SCHOOL_VALIDATOR_NAME = TeacherNotBelongsToSchoolValidator.class.getSimpleName();

    String EXCEPTION_MESSAGE_TEACHER_NOT_BELONG_TO_SCHOOL = "teacher.not.belongs.to.school";
    String EXCEPTION_MESSAGE_TEACHER_IS_ALREADY_SUPERVISING_TEACHER = "teacher.is.already.supervising.teacher";
    String EXCEPTION_MESSAGE_SCHOOL_CLASS_ALREADY_EXIST = "school.class.already.exist";

}
