package pl.edziennik.application.command.student.assignparent;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.application.common.dispatcher.CommandHandler;
import pl.edziennik.domain.parent.Parent;
import pl.edziennik.domain.student.Student;
import pl.edziennik.infrastructure.repository.parent.ParentCommandRepository;
import pl.edziennik.infrastructure.repository.student.StudentCommandRepository;

@Component
@AllArgsConstructor
class AssignParentCommandHandler implements CommandHandler<AssignParentCommand> {

    private final StudentCommandRepository studentCommandRepository;
    private final ParentCommandRepository parentCommandRepository;

    @Override
    @Transactional
    public void handle(AssignParentCommand command) {
        Parent parent = parentCommandRepository.getReferenceById(command.parentId());
        Student student = studentCommandRepository.getReferenceById(command.studentId());

        student.assignParent(parent);

        // FIXME : CQRS -> Zmienic model Parenta (idStudent nie wymagane podczas dodawania Parenta)
    }
}
