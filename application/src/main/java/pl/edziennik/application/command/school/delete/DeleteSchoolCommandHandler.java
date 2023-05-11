package pl.edziennik.application.command.school.delete;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.command.ICommandHandler;
import pl.edziennik.infrastructure.repositories.school.SchoolCommandRepository;

@Component
@AllArgsConstructor
class DeleteSchoolCommandHandler implements ICommandHandler<DeleteSchoolCommand, OperationResult> {

    private final SchoolCommandRepository schoolCommandRepository;

    @Override
    public OperationResult handle(DeleteSchoolCommand command) {
        schoolCommandRepository.deleteById(command.schoolId());

        return OperationResult.success();
    }
}
