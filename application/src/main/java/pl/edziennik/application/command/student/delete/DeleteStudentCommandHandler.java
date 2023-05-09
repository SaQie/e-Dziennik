package pl.edziennik.application.command.student.delete;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.command.ICommandHandler;
import pl.edziennik.application.common.dispatcher.exception.BusinessException;
import pl.edziennik.domain.student.Student;
import pl.edziennik.infrastructure.repositories.student.StudentCommandRepository;
import pl.edziennik.infrastructure.spring.ResourceCreator;

@Component
@AllArgsConstructor
class DeleteStudentCommandHandler implements ICommandHandler<DeleteStudentCommand, OperationResult> {

    private final StudentCommandRepository studentCommandRepository;
    private final ResourceCreator res;

    @Override
    @Transactional
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
