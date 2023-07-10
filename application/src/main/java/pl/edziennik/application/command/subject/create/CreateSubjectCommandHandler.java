package pl.edziennik.application.command.subject.create;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.command.ICommandHandler;
import pl.edziennik.common.valueobject.id.SubjectId;
import pl.edziennik.domain.schoolclass.SchoolClass;
import pl.edziennik.domain.subject.Subject;
import pl.edziennik.domain.teacher.Teacher;
import pl.edziennik.infrastructure.repository.schoolclass.SchoolClassCommandRepository;
import pl.edziennik.infrastructure.repository.subject.SubjectCommandRepository;
import pl.edziennik.infrastructure.repository.teacher.TeacherCommandRepository;

@Component
@AllArgsConstructor
class CreateSubjectCommandHandler implements ICommandHandler<CreateSubjectCommand, OperationResult> {

    private final SubjectCommandRepository subjectCommandRepository;
    private final SchoolClassCommandRepository schoolClassCommandRepository;
    private final TeacherCommandRepository teacherCommandRepository;

    @Override
    @CacheEvict(allEntries = true, value = "subjects")
    public OperationResult handle(CreateSubjectCommand command) {
        SchoolClass schoolClass = schoolClassCommandRepository.getReferenceById(command.schoolClassId());
        Teacher teacher = teacherCommandRepository.getReferenceById(command.teacherId());

        Subject subject = Subject.of(command.name(), command.description(), schoolClass, teacher);

        SubjectId subjectId = subjectCommandRepository.save(subject).getSubjectId();

        return OperationResult.success(subjectId);
    }
}
