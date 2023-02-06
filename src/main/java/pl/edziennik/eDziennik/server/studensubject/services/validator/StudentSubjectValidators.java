package pl.edziennik.eDziennik.server.studensubject.services.validator;

import pl.edziennik.eDziennik.server.basics.AbstractValidator;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.request.StudentSubjectRequestDto;

public interface StudentSubjectValidators extends AbstractValidator<StudentSubjectRequestDto> {

    String STUDENT_CANNOT_BE_ASSIGNED_TO_SUBJECT_FRON_DIFFERENT_CLASS_VALIDATOR_NAME =
            StudentCannotBeAssignedToSubjectFromDifferentClassValidator.class.getSimpleName();

    String STUDENT_SUBJECT_ALREADY_EXIST_VALIDATOR_NAME = StudentSubjectAlreadyExistValidator.class.getSimpleName();

    String EXCEPTION_MESSAGE_STUDENT_SUBJECT_ALREADY_EXIST = "student.subject.already.exist";
    String EXCEPTION_MESSAGE_STUDENT_CANNOT_BE_ASSIGNED_TO_SUBJECT_FROM_DIFFERENT_CLASS = "student.assign.subject.from.different.class";

}
