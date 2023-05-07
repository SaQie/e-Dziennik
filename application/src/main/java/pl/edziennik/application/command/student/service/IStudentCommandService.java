package pl.edziennik.application.command.student.service;

import pl.edziennik.application.command.student.service.create.CreateStudentCommand;
import pl.edziennik.application.common.dispatcher.OperationResult;

public interface IStudentCommandService {

    OperationResult createUser(CreateStudentCommand command);

}
