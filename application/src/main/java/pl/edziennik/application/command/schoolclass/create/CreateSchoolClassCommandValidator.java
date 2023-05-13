package pl.edziennik.application.command.schoolclass.create;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.ValidationErrorBuilder;
import pl.edziennik.application.common.dispatcher.base.IBaseValidator;
import pl.edziennik.infrastructure.repositories.school.SchoolCommandRepository;
import pl.edziennik.infrastructure.repositories.schoolclass.SchoolClassCommandRepository;
import pl.edziennik.infrastructure.repositories.teacher.TeacherCommandRepository;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

@Component
@AllArgsConstructor
class CreateSchoolClassCommandValidator implements IBaseValidator<CreateSchoolClassCommand> {

    public static final String MESSAGE_KEY_SCHOOL_CLASS_ALREADY_EXISTS = "school.class.already.exists";
    public static final String MESSAGE_KEY_TEACHER_NOT_BELONGS_TO_SCHOOL = "teacher.not.belongs.to.school";
    public static final String MESSAGE_KEY_TEACHER_IS_ALREADY_SUPERVISING_TEACHER = "teacher.is.already.supervising" + ".teacher";

    private final SchoolClassCommandRepository schoolClassCommandRepository;
    private final SchoolCommandRepository schoolCommandRepository;
    private final TeacherCommandRepository teacherCommandRepository;


    @Override
    public void validate(CreateSchoolClassCommand command, ValidationErrorBuilder errorBuilder) {
        schoolCommandRepository.findById(command.schoolId())
                .orElseGet(() -> {
                    errorBuilder.addNotFoundError(CreateSchoolClassCommand.SCHOOL_ID);
                    return null;
                });

        validateTeacherIfNeeded(command, errorBuilder);

        errorBuilder.flush();

        if (schoolClassCommandRepository.existsByName(command.className(), command.schoolId())) {
            errorBuilder.addError(
                    CreateSchoolClassCommand.CLASS_NAME,
                    MESSAGE_KEY_SCHOOL_CLASS_ALREADY_EXISTS,
                    ErrorCode.OBJECT_ALREADY_EXISTS
            );
        }

    }

    private void validateTeacherIfNeeded(CreateSchoolClassCommand command, ValidationErrorBuilder errorBuilder) {
        if (command.teacherId() != null) {
            teacherCommandRepository.findById(command.teacherId())
                    .orElseGet(() -> {
                        errorBuilder.addNotFoundError(CreateSchoolClassCommand.TEACHER_ID);
                        return null;
                    });

            errorBuilder.flush();

            if (!teacherCommandRepository.isAssignedToSchool(command.teacherId(), command.schoolId())) {
                errorBuilder.addError(
                        CreateSchoolClassCommand.TEACHER_ID,
                        MESSAGE_KEY_TEACHER_NOT_BELONGS_TO_SCHOOL,
                        ErrorCode.NOT_ASSIGNED_TO_SCHOOL
                );
            }

            if (teacherCommandRepository.isAlreadySupervisingTeacher(command.teacherId())) {
                errorBuilder.addError(
                        CreateSchoolClassCommand.TEACHER_ID,
                        MESSAGE_KEY_TEACHER_IS_ALREADY_SUPERVISING_TEACHER,
                        ErrorCode.SELECTED_TEACHER_IS_ALREADY_SUPERVISING_TEACHER
                );
            }

        }
    }
}
