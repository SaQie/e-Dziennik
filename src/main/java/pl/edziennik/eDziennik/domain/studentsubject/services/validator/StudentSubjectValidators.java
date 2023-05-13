package pl.edziennik.eDziennik.domain.studentsubject.services.validator;

import pl.edziennik.eDziennik.domain.studentsubject.dto.request.StudentSubjectRequestDto;
import pl.edziennik.eDziennik.server.basic.validator.AbstractValidator;

public interface StudentSubjectValidators extends AbstractValidator<StudentSubjectRequestDto> {

    String STUDENT_CANNOT_BE_ASSIGNED_TO_SUBJECT_FRON_DIFFERENT_CLASS_VALIDATOR_NAME =
            StudentCannotBeAssignedToSubjectFromDifferentClassValidator.class.getSimpleName();
    String STUDENT_SUBJECT_ALREADY_EXIST_VALIDATOR_NAME = StudentSubjectAlreadyExistValidator.class.getSimpleName();


    String EXCEPTION_MESSAGE_STUDENT_SUBJECT_ALREADY_EXIST = "student.subject.already.exists";
    String EXCEPTION_MESSAGE_STUDENT_CANNOT_BE_ASSIGNED_TO_SUBJECT_FROM_DIFFERENT_CLASS = "student.assign.subject.from.another.class";

}
