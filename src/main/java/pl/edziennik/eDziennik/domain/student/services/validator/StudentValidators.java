package pl.edziennik.eDziennik.domain.student.services.validator;

import pl.edziennik.eDziennik.domain.student.dto.StudentRequestApiDto;
import pl.edziennik.eDziennik.server.basics.validator.AbstractValidator;

public interface StudentValidators extends AbstractValidator<StudentRequestApiDto> {

    String STUDENT_PESEL_NOT_UNIQUE_VALIDATOR_NAME = StudentPeselNotUniqueValidator.class.getSimpleName();
    String STUDENT_SCHOOL_CLASS_NOT_BELONGS_TO_SCHOOL_VALIDATOR_NAME = StudentSchoolClassNotBelongsToSchoolValidator.class.getSimpleName();
    String STUDENT_STILL_HAS_PARENT_VALIDATOR = StudentStillHasParentValidator.class.getSimpleName();

    String EXCEPTION_MESSAGE_SCHOOL_CLASS_NOT_BELONG_TO_SCHOOL = "school.class.not.belong.to.school";
    String EXCEPTION_MESSAGE_PESEL_NOT_UNIQUE = "student.pesel.not.unique";
    String EXCEPTION_MESSAGE_STUDENT_STILL_HAS_PARENT = "student.still.has.parent";


}
