package pl.edziennik.application.command.school.delete;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.ValidationErrorBuilder;
import pl.edziennik.application.common.dispatcher.base.IBaseValidator;
import pl.edziennik.infrastructure.repositories.school.SchoolCommandRepository;
import pl.edziennik.infrastructure.spring.ResourceCreator;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

@Component
@AllArgsConstructor
class DeleteSchoolCommandValidator implements IBaseValidator<DeleteSchoolCommand> {

    private final SchoolCommandRepository schoolCommandRepository;
    private final ResourceCreator res;

    @Override
    public void validate(DeleteSchoolCommand command, ValidationErrorBuilder errorBuilder) {
        schoolCommandRepository.findById(command.schoolId())
                .orElseGet(() -> {
                    errorBuilder.addNotFoundError(DeleteSchoolCommand.SCHOOL_ID);
                    return null;
                });

        errorBuilder.flushErrors();

        if (schoolCommandRepository.isTeacherExistsInSchool(command.schoolId())) {
            errorBuilder.addError(
                    DeleteSchoolCommand.SCHOOL_ID,
                    res.of("cannot.delete.because.of.teacher.exists"),
                    ErrorCode.STILL_EXISTS_RELATED_OBJECTS_TO_SCHOOL
            );
        }

        if (schoolCommandRepository.isStudentExistsInSchool(command.schoolId())) {
            errorBuilder.addError(
                    DeleteSchoolCommand.SCHOOL_ID,
                    res.of("cannot.delete.because.of.student.exists"),
                    ErrorCode.STILL_EXISTS_RELATED_OBJECTS_TO_SCHOOL
            );
        }

        if (schoolCommandRepository.isSchoolClassExistsInSchool(command.schoolId())) {
            errorBuilder.addError(
                    DeleteSchoolCommand.SCHOOL_ID,
                    res.of("cannot.delete.because.of.school.class.exists"),
                    ErrorCode.STILL_EXISTS_RELATED_OBJECTS_TO_SCHOOL
            );
        }
    }
}
