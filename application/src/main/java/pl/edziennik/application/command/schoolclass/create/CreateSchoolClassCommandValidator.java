package pl.edziennik.application.command.schoolclass.create;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.IBaseValidator;
import pl.edziennik.application.common.dispatcher.ValidationErrorBuilder;
import pl.edziennik.infrastructure.repository.school.SchoolCommandRepository;
import pl.edziennik.infrastructure.repository.schoolclass.SchoolClassCommandRepository;
import pl.edziennik.infrastructure.repository.teacher.TeacherCommandRepository;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

@Component
@AllArgsConstructor
class CreateSchoolClassCommandValidator implements IBaseValidator<CreateSchoolClassCommand> {

    public static final String MESSAGE_KEY_SCHOOL_CLASS_ALREADY_EXISTS = "school.class.already.exists";
    public static final String MESSAGE_KEY_TEACHER_NOT_BELONGS_TO_SCHOOL = "teacher.not.belongs.to.school";
    public static final String MESSAGE_KEY_TEACHER_IS_ALREADY_SUPERVISING_TEACHER = "teacher.is.already.supervising" + ".teacher";
    public static final String MESSAGE_KEY_ACCOUNT_INACTIVE = "account.inactive";

    private final SchoolClassCommandRepository schoolClassCommandRepository;
    private final SchoolCommandRepository schoolCommandRepository;
    private final TeacherCommandRepository teacherCommandRepository;


    @Override
    public void validate(CreateSchoolClassCommand command, ValidationErrorBuilder errorBuilder) {
        checkSchoolExists(command, errorBuilder);

        if (command.teacherId() != null) {
            checkTeacherExists(command, errorBuilder);
            checkTeacherAccountIsActive(command, errorBuilder);
            checkTeacherIsAssignedToSchool(command, errorBuilder);
            checkTeacherIsAlreadySupervisingTeacher(command, errorBuilder);
        }
        errorBuilder.flush();

        checkSchoolClassExistsByName(command, errorBuilder);


    }

    /**
     * Check if school class with given name already exists in school
     */
    private void checkSchoolClassExistsByName(CreateSchoolClassCommand command, ValidationErrorBuilder errorBuilder) {
        if (schoolClassCommandRepository.existsByName(command.className(), command.schoolId())) {
            errorBuilder.addError(
                    CreateSchoolClassCommand.CLASS_NAME,
                    MESSAGE_KEY_SCHOOL_CLASS_ALREADY_EXISTS,
                    ErrorCode.OBJECT_ALREADY_EXISTS
            );
        }
    }

    /**
     * Check if school with given id exists
     */
    private void checkSchoolExists(CreateSchoolClassCommand command, ValidationErrorBuilder errorBuilder) {
        schoolCommandRepository.findById(command.schoolId())
                .orElseGet(() -> {
                    errorBuilder.addNotFoundError(CreateSchoolClassCommand.SCHOOL_ID);
                    return null;
                });
    }

    /**
     * Check if given teacher is already supervising teacher
     */
    private void checkTeacherIsAlreadySupervisingTeacher(CreateSchoolClassCommand command, ValidationErrorBuilder errorBuilder) {
        if (teacherCommandRepository.isAlreadySupervisingTeacher(command.teacherId())) {
            errorBuilder.addError(
                    CreateSchoolClassCommand.TEACHER_ID,
                    MESSAGE_KEY_TEACHER_IS_ALREADY_SUPERVISING_TEACHER,
                    ErrorCode.SELECTED_TEACHER_IS_ALREADY_SUPERVISING_TEACHER
            );
        }
    }

    /**
     * Check if given teacher is assigned to given school
     */
    private void checkTeacherIsAssignedToSchool(CreateSchoolClassCommand command, ValidationErrorBuilder errorBuilder) {
        if (!teacherCommandRepository.isAssignedToSchool(command.teacherId(), command.schoolId())) {
            errorBuilder.addError(
                    CreateSchoolClassCommand.TEACHER_ID,
                    MESSAGE_KEY_TEACHER_NOT_BELONGS_TO_SCHOOL,
                    ErrorCode.NOT_ASSIGNED_TO_SCHOOL
            );
        }
    }

    /**
     * Check if teacher account is active
     */
    private void checkTeacherAccountIsActive(CreateSchoolClassCommand command, ValidationErrorBuilder errorBuilder) {
        if (teacherCommandRepository.isTeacherAccountNotActive(command.teacherId())) {
            errorBuilder.addError(
                    CreateSchoolClassCommand.TEACHER_ID,
                    MESSAGE_KEY_ACCOUNT_INACTIVE,
                    ErrorCode.ACCOUNT_INACTIVE
            );
        }
    }

    /**
     * Check if teacher with given id exists
     */
    private void checkTeacherExists(CreateSchoolClassCommand command, ValidationErrorBuilder errorBuilder) {
        teacherCommandRepository.findById(command.teacherId())
                .orElseGet(() -> {
                    errorBuilder.addNotFoundError(CreateSchoolClassCommand.TEACHER_ID);
                    return null;
                });

        errorBuilder.flush();
    }
}
