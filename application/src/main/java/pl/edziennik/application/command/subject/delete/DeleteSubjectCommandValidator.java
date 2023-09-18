package pl.edziennik.application.command.subject.delete;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.ValidationErrorBuilder;
import pl.edziennik.application.common.dispatcher.Validator;
import pl.edziennik.common.valueobject.vo.Name;
import pl.edziennik.infrastructure.repository.subject.SubjectCommandRepository;
import pl.edziennik.infrastructure.spring.ResourceCreator;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

@Component
@AllArgsConstructor
class DeleteSubjectCommandValidator implements Validator<DeleteSubjectCommand> {

    public static final String MESSAGE_KEY_EXISTS_GRADES_ASSIGNED_TO_SUBJECT = "exists.grades.assigned.to.subject";

    private final SubjectCommandRepository subjectCommandRepository;
    private final ResourceCreator res;

    @Override
    public void validate(DeleteSubjectCommand command, ValidationErrorBuilder errorBuilder) {
        checkSubjectExists(command, errorBuilder);
        checkSubjectIsInUse(command, errorBuilder);
    }

    private void checkSubjectExists(DeleteSubjectCommand command, ValidationErrorBuilder errorBuilder) {
        subjectCommandRepository.findById(command.subjectId())
                .orElseGet(() -> {
                    errorBuilder.addNotFoundError(DeleteSubjectCommand.SUBJECT_ID);
                    return null;
                });
        errorBuilder.flush();
    }

    /**
     * Check subject is in use (subject is assigned to student and grades exists)
     */
    private void checkSubjectIsInUse(DeleteSubjectCommand command, ValidationErrorBuilder errorBuilder) {
        boolean existsGradesAssignedToSubject = subjectCommandRepository.existsGradesAssignedToSubject(command.subjectId());
        if (existsGradesAssignedToSubject) {
            Name subjectName = subjectCommandRepository.getNameBySubjectId(command.subjectId());
            errorBuilder.addError(
                    DeleteSubjectCommand.SUBJECT_ID,
                    MESSAGE_KEY_EXISTS_GRADES_ASSIGNED_TO_SUBJECT,
                    ErrorCode.RELEATED_OBJECT_EXISTS,
                    subjectName
            );
        }
    }
}
