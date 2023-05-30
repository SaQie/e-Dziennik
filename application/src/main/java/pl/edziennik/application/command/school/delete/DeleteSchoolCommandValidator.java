package pl.edziennik.application.command.school.delete;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.ValidationErrorBuilder;
import pl.edziennik.application.common.dispatcher.base.IBaseValidator;
import pl.edziennik.infrastructure.repository.school.SchoolCommandRepository;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

@Component
@AllArgsConstructor
class DeleteSchoolCommandValidator implements IBaseValidator<DeleteSchoolCommand> {

    public static final String MESSAGE_KEY_CANNOT_DELETE_SCHOOL_BECAUSE_OF_TEACHER_EXISTS =
            "cannot.delete.because.of.teacher.exists";
    public static final String MESSAGE_KEY_CANNOT_DELETE_SCHOOL_BECAUSE_OF_STUDENT_EXISTS =
            "cannot.delete.because.of.student.exists";
    public static final String MESSAGE_KEY_CANNOT_DELETE_SCHOOL_BECAUSE_OF_SCHOOL_CLASS_EXISTS =
            "cannot.delete.because.of.school.class.exists";

    private final SchoolCommandRepository schoolCommandRepository;

    @Override
    public void validate(DeleteSchoolCommand command, ValidationErrorBuilder errorBuilder) {
        checkSchoolExists(command, errorBuilder);
        errorBuilder.flush();

        checkTeacherExistsInSchool(command, errorBuilder);
        checkStudentExistsInSchool(command, errorBuilder);
        checkSchoolClassExistsInSchool(command, errorBuilder);
    }

    /**
     * Check school given to delete still have school classes
     */
    private void checkSchoolClassExistsInSchool(DeleteSchoolCommand command, ValidationErrorBuilder errorBuilder) {
        if (schoolCommandRepository.isSchoolClassExistsInSchool(command.schoolId())) {
            errorBuilder.addError(
                    DeleteSchoolCommand.SCHOOL_ID,
                    MESSAGE_KEY_CANNOT_DELETE_SCHOOL_BECAUSE_OF_SCHOOL_CLASS_EXISTS,
                    ErrorCode.STILL_EXISTS_RELATED_OBJECTS_TO_SCHOOL
            );
        }
    }

    /**
     * Check school given to delete still have assigned students
     */
    private void checkStudentExistsInSchool(DeleteSchoolCommand command, ValidationErrorBuilder errorBuilder) {
        if (schoolCommandRepository.isStudentExistsInSchool(command.schoolId())) {
            errorBuilder.addError(
                    DeleteSchoolCommand.SCHOOL_ID,
                    MESSAGE_KEY_CANNOT_DELETE_SCHOOL_BECAUSE_OF_STUDENT_EXISTS,
                    ErrorCode.STILL_EXISTS_RELATED_OBJECTS_TO_SCHOOL
            );
        }
    }


    /**
     * Check school given to delete still have assigned teachers
     */
    private void checkTeacherExistsInSchool(DeleteSchoolCommand command, ValidationErrorBuilder errorBuilder) {
        if (schoolCommandRepository.isTeacherExistsInSchool(command.schoolId())) {
            errorBuilder.addError(
                    DeleteSchoolCommand.SCHOOL_ID,
                    MESSAGE_KEY_CANNOT_DELETE_SCHOOL_BECAUSE_OF_TEACHER_EXISTS,
                    ErrorCode.STILL_EXISTS_RELATED_OBJECTS_TO_SCHOOL
            );
        }
    }


    /**
     * Check school with given name already exists
     */
    private void checkSchoolExists(DeleteSchoolCommand command, ValidationErrorBuilder errorBuilder) {
        schoolCommandRepository.findById(command.schoolId())
                .orElseGet(() -> {
                    errorBuilder.addNotFoundError(DeleteSchoolCommand.SCHOOL_ID);
                    return null;
                });
    }
}
