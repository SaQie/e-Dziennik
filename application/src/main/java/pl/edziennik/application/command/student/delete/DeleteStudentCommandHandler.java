package pl.edziennik.application.command.student.delete;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.application.common.dispatcher.CommandHandler;
import pl.edziennik.domain.student.Student;
import pl.edziennik.infrastructure.repository.student.StudentCommandRepository;
import pl.edziennik.infrastructure.spring.ResourceCreator;
import pl.edziennik.infrastructure.spring.exception.BusinessException;

@Component
@AllArgsConstructor
class DeleteStudentCommandHandler implements CommandHandler<DeleteStudentCommand> {

    private final StudentCommandRepository studentCommandRepository;
    private final ResourceCreator res;

    @Override
    @Transactional
    public void handle(DeleteStudentCommand command) {
        Student student = studentCommandRepository.findById(command.studentId())
                .orElseThrow(() -> new BusinessException(
                        res.notFoundError(DeleteStudentCommand.STUDENT_ID, command.studentId())
                ));

        studentCommandRepository.delete(student);
    }
}
