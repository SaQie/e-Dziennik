package pl.edziennik.application.command.grademanagment.assigngrade;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.application.common.dispatcher.CommandHandler;
import pl.edziennik.application.events.event.GradeAddedEvent;
import pl.edziennik.domain.grade.Grade;
import pl.edziennik.domain.studentsubject.StudentSubject;
import pl.edziennik.domain.teacher.Teacher;
import pl.edziennik.infrastructure.repository.grade.GradeCommandRepository;
import pl.edziennik.infrastructure.repository.studentsubject.StudentSubjectCommandRepository;
import pl.edziennik.infrastructure.repository.teacher.TeacherCommandRepository;

@Component
@AllArgsConstructor
class AssignGradeToStudentSubjectCommandHandler implements CommandHandler<AssignGradeToStudentSubjectCommand> {

    private final StudentSubjectCommandRepository studentSubjectCommandRepository;
    private final TeacherCommandRepository teacherCommandRepository;
    private final GradeCommandRepository gradeCommandRepository;

    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public void handle(AssignGradeToStudentSubjectCommand command) {
        StudentSubject studentSubject = studentSubjectCommandRepository.getReferenceByStudentStudentIdAndSubjectSubjectId(command.studentId(), command.subjectId());
        Teacher teacher = teacherCommandRepository.getReferenceById(command.teacherId());

        Grade grade = Grade.builder()
                .gradeId(command.gradeId())
                .gradeConst(command.grade())
                .weight(command.weight())
                .description(command.description())
                .studentSubject(studentSubject)
                .teacher(teacher)
                .build();

        gradeCommandRepository.save(grade);

        GradeAddedEvent event = new GradeAddedEvent(studentSubject.studentSubjectId());
        eventPublisher.publishEvent(event);
    }
}
