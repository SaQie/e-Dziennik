package pl.edziennik.application.command.schoolclass.delete;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.ValidationErrorBuilder;
import pl.edziennik.application.common.dispatcher.Validator;
import pl.edziennik.infrastructure.repository.schoolclass.SchoolClassCommandRepository;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

@Component
@AllArgsConstructor
class DeleteSchoolClassCommandValidator implements Validator<DeleteSchoolClassCommand> {

    public static final String MESSAGE_KEY_SCHOOL_CLASS_STILL_HAS_ASSIGNED_STUDENTS = "school.class.still.has.assigned.students";
    public static final String MESSAGE_KEY_SCHOOL_CLASS_STILL_HAS_ASSIGNED_SUBJECTS = "school.class.still.has.assigned.subjects";
    private final SchoolClassCommandRepository schoolClassCommandRepository;

    @Override
    public void validate(DeleteSchoolClassCommand command, ValidationErrorBuilder errorBuilder) {
        checkSchoolClassExists(command, errorBuilder);
        checkStudentsExists(command, errorBuilder);
        checkSubjectsExists(command,errorBuilder);
    }

    /**
     * Check subjects assigned to school class exists
     */
    private void checkSubjectsExists(DeleteSchoolClassCommand command, ValidationErrorBuilder errorBuilder) {
        boolean subjectsExists = schoolClassCommandRepository.isSubjectsExists(command.schoolClassId());
        if (subjectsExists){
            errorBuilder.addError(
                    DeleteSchoolClassCommand.SCHOOL_CLASS_ID,
                    MESSAGE_KEY_SCHOOL_CLASS_STILL_HAS_ASSIGNED_SUBJECTS,
                    ErrorCode.RELEATED_OBJECT_EXISTS
            );
        }
    }

    /**
     * Check students assigned to school class exists
     */
    private void checkStudentsExists(DeleteSchoolClassCommand command, ValidationErrorBuilder errorBuilder) {
        boolean studentsExists = schoolClassCommandRepository.isStudentsExists(command.schoolClassId());
        if (studentsExists) {
            errorBuilder.addError(
                    DeleteSchoolClassCommand.SCHOOL_CLASS_ID,
                    MESSAGE_KEY_SCHOOL_CLASS_STILL_HAS_ASSIGNED_STUDENTS,
                    ErrorCode.RELEATED_OBJECT_EXISTS
            );
        }
    }

    /**
     * Check given school class exists
     */
    private void checkSchoolClassExists(DeleteSchoolClassCommand command, ValidationErrorBuilder errorBuilder) {
        schoolClassCommandRepository.findById(command.schoolClassId())
                .orElseGet(() -> {
                    errorBuilder.addNotFoundError(DeleteSchoolClassCommand.SCHOOL_CLASS_ID);
                    return null;
                });
        errorBuilder.flush();
    }


}
