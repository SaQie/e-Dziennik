package pl.edziennik.application.command.student.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.application.command.student.service.create.CreateStudentCommand;
import pl.edziennik.application.common.dispatcher.DispatcherResolver;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.domain.student.StudentId;

@Service
@AllArgsConstructor
class StudentCommandService extends DispatcherResolver implements IStudentCommandService{


    @Override
    public OperationResult createUser(CreateStudentCommand command) {
        StudentId studentId = callHandler(command);
        return OperationResult.success();
    }
}
