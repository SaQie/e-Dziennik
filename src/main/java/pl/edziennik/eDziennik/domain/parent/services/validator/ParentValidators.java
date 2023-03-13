package pl.edziennik.eDziennik.domain.parent.services.validator;

import pl.edziennik.eDziennik.domain.parent.domain.dto.ParentRequestApiDto;
import pl.edziennik.eDziennik.server.basics.validator.AbstractValidator;

public interface ParentValidators extends AbstractValidator<ParentRequestApiDto> {

    String PARENT_PESEL_NOT_UNIQUE_VALIDATOR = ParentPeselNotUniqueValidator.class.getSimpleName();
    String STUDENT_ALREADY_HAS_PARENT_VALIDATOR = StudentAlreadyHasParentValidator.class.getSimpleName();
    String PARENT_STILL_HAS_STUDENT_VALIDATOR = ParentStillHasStudentValidator.class.getSimpleName();

    String EXCEPTION_MESSAGE_PARENT_PESEL_ALREADY_EXISTS = "parent.pesel.not.unique";
    String EXCEPTION_MESSAGE_STUDENT_ALREADY_HAS_PARENT_VALIDATOR = "student.already.has.parent";
    String EXCEPTON_MESSAGE_PARENT_STILL_HAS_STUDENT = "parent.still.has.student";

}
