package pl.edziennik.application.command.schoolclass.delete;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.CommandHandler;
import pl.edziennik.infrastructure.repository.schoolclass.SchoolClassCommandRepository;

@Component
@AllArgsConstructor
class DeleteSchoolClassCommandHandler implements CommandHandler<DeleteSchoolClassCommand> {

    private final SchoolClassCommandRepository schoolClassCommandRepository;

    @Override
    public void handle(DeleteSchoolClassCommand command) {
        schoolClassCommandRepository.deleteById(command.schoolClassId());
    }
}
