package pl.edziennik.application.command.subject.delete;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.application.common.dispatcher.CommandHandler;
import pl.edziennik.infrastructure.repository.subject.SubjectCommandRepository;

@Component
@AllArgsConstructor
class DeleteSubjectCommandHandler implements CommandHandler<DeleteSubjectCommand> {

    private final SubjectCommandRepository subjectCommandRepository;

    @Override
    @Transactional
    public void handle(DeleteSubjectCommand command) {
        subjectCommandRepository.deleteBySubjectId(command.subjectId());
    }
}
