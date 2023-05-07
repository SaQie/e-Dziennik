package pl.edziennik.eDziennik.command.student.service.delete;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.domain.student.domain.Student;
import pl.edziennik.eDziennik.infrastructure.OperationResult;
import pl.edziennik.eDziennik.infrastructure.repository.command.student.StudentCommandRepository;
import pl.edziennik.eDziennik.infrastructure.spring.command.ICommandHandler;
import pl.edziennik.eDziennik.infrastructure.spring.exception.BusinessException;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

@Component
@AllArgsConstructor
public class DeleteStudentCommandHandler implements ICommandHandler<DeleteStudentCommand, OperationResult> {

    private final StudentCommandRepository studentCommandRepository;
    private final ResourceCreator res;

    @Override
    public OperationResult handle(DeleteStudentCommand command) {
        Student student = studentCommandRepository.findById(command.studentId())
                .orElseThrow(() -> new BusinessException(
                        res.notFoundError(DeleteStudentCommand.STUDENT_ID, command.studentId())
                ));

        if (student.getParent() != null) {
            student.getParent().clearStudent();
        }

        studentCommandRepository.delete(student);

        return OperationResult.success();
    }
}
