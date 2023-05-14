package pl.edziennik.application.query.teacher.detailedsubject;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.ValidationErrorBuilder;
import pl.edziennik.application.common.dispatcher.base.IBaseValidator;
import pl.edziennik.infrastructure.repositories.subject.SubjectCommandRepository;
import pl.edziennik.infrastructure.repositories.teacher.TeacherCommandRepository;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

@Component
@AllArgsConstructor
class GetTeacherDetailedSubjectQueryValidator implements IBaseValidator<GetTeacherDetailedSubjectQuery> {

    public static final String MESSAGE_KEY_TEACHER_NOT_FROM_PROVIDED_SUBJECT = "teacher.not.from.provided.subject";

    private final SubjectCommandRepository subjectCommandRepository;
    private final TeacherCommandRepository teacherCommandRepository;

    @Override
    public void validate(GetTeacherDetailedSubjectQuery command, ValidationErrorBuilder errorBuilder) {
        teacherCommandRepository.findById(command.teacherId())
                .orElseGet(() -> {
                    errorBuilder.addNotFoundError(GetTeacherDetailedSubjectQuery.TEACHER_ID);
                    return null;
                });

        subjectCommandRepository.findById(command.subjectId())
                .orElseGet(() -> {
                    errorBuilder.addNotFoundError(GetTeacherDetailedSubjectQuery.SUBJECT_ID);
                    return null;
                });

        errorBuilder.flush();

        if (!subjectCommandRepository.isTeacherFromProvidedSubject(command.teacherId(), command.subjectId())) {
            errorBuilder.addError(
                    GetTeacherDetailedSubjectQuery.TEACHER_ID,
                    MESSAGE_KEY_TEACHER_NOT_FROM_PROVIDED_SUBJECT,
                    ErrorCode.NOT_ASSIGNED_TO_SUBJECT
            );
        }
    }
}
