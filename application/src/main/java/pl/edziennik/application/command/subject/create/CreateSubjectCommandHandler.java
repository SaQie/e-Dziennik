package pl.edziennik.application.command.subject.create;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.application.common.dispatcher.CommandHandler;
import pl.edziennik.domain.schoolclass.SchoolClass;
import pl.edziennik.domain.subject.Subject;
import pl.edziennik.domain.teacher.Teacher;
import pl.edziennik.infrastructure.repository.schoolclass.SchoolClassCommandRepository;
import pl.edziennik.infrastructure.repository.subject.SubjectCommandRepository;
import pl.edziennik.infrastructure.repository.teacher.TeacherCommandRepository;

@Component
@AllArgsConstructor
class CreateSubjectCommandHandler implements CommandHandler<CreateSubjectCommand> {

    private final SubjectCommandRepository subjectCommandRepository;
    private final SchoolClassCommandRepository schoolClassCommandRepository;
    private final TeacherCommandRepository teacherCommandRepository;

    @Override
    @Transactional
    @CacheEvict(allEntries = true, value = "subjects")
    public void handle(CreateSubjectCommand command) {
        SchoolClass schoolClass = schoolClassCommandRepository.getReferenceById(command.schoolClassId());
        Teacher teacher = teacherCommandRepository.getReferenceById(command.teacherId());

        Subject subject = Subject.builder()
                .subjectId(command.subjectId())
                .schoolClass(schoolClass)
                .description(command.description())
                .teacher(teacher)
                .subjectName(command.name())
                .build();

        subjectCommandRepository.save(subject);
    }
}
