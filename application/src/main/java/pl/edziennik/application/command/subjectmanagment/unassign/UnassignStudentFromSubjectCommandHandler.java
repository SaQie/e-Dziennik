package pl.edziennik.application.command.subjectmanagment.unassign;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.application.common.dispatcher.CommandHandler;
import pl.edziennik.infrastructure.repository.studentsubject.StudentSubjectCommandRepository;

@Component
@AllArgsConstructor
class UnassignStudentFromSubjectCommandHandler implements CommandHandler<UnassignStudentFromSubjectCommand> {

    private final StudentSubjectCommandRepository subjectCommandRepository;

    @Override
    @Transactional
    public void handle(UnassignStudentFromSubjectCommand command) {
        subjectCommandRepository.deleteByStudentIdAndSubjectId(command.studentId(),command.subjectId());
    }
}
