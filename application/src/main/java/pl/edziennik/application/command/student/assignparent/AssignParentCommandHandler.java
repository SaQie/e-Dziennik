package pl.edziennik.application.command.student.assignparent;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.command.ICommandHandler;
import pl.edziennik.domain.parent.Parent;
import pl.edziennik.domain.student.Student;
import pl.edziennik.infrastructure.repository.parent.ParentCommandRepository;
import pl.edziennik.infrastructure.repository.student.StudentCommandRepository;

@Component
@AllArgsConstructor
class AssignParentCommandHandler implements ICommandHandler<AssignParentCommand, OperationResult> {

    private final StudentCommandRepository studentCommandRepository;
    private final ParentCommandRepository parentCommandRepository;

    @Override
    @Transactional
    public OperationResult handle(AssignParentCommand command) {
        Parent parent = parentCommandRepository.getReferenceById(command.parentId());
        Student student = studentCommandRepository.getReferenceById(command.studentId());

        student.assignParent(parent);

        // FIXME : CQRS -> Zmienic model Parenta (idStudent nie wymagane podczas dodawania Parenta)

        return OperationResult.success();
    }
}
