package pl.edziennik.application.command.school.delete;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.CommandHandler;
import pl.edziennik.infrastructure.repository.school.SchoolCommandRepository;

@Component
@AllArgsConstructor
class DeleteSchoolCommandHandler implements CommandHandler<DeleteSchoolCommand> {

    private final SchoolCommandRepository schoolCommandRepository;

    @Override
    @CacheEvict(allEntries = true, value = "schools")
    public void handle(DeleteSchoolCommand command) {
        schoolCommandRepository.deleteById(command.schoolId());
    }
}
