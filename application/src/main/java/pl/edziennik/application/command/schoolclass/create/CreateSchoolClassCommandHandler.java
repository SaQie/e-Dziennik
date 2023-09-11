package pl.edziennik.application.command.schoolclass.create;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.CommandHandler;
import pl.edziennik.common.properties.SchoolClassConfigurationProperties;
import pl.edziennik.domain.school.School;
import pl.edziennik.domain.schoolclass.SchoolClass;
import pl.edziennik.domain.teacher.Teacher;
import pl.edziennik.infrastructure.repository.school.SchoolCommandRepository;
import pl.edziennik.infrastructure.repository.schoolclass.SchoolClassCommandRepository;
import pl.edziennik.infrastructure.repository.teacher.TeacherCommandRepository;

@Component
@AllArgsConstructor
class CreateSchoolClassCommandHandler implements CommandHandler<CreateSchoolClassCommand> {

    private final SchoolCommandRepository schoolCommandRepository;
    private final TeacherCommandRepository teacherCommandRepository;
    private final SchoolClassCommandRepository schoolClassCommandRepository;
    private final SchoolClassConfigurationProperties configurationProperties;

    @Override
    @CacheEvict(allEntries = true, value = "schoolClasses")
    public void handle(CreateSchoolClassCommand command) {
        Teacher teacher = teacherCommandRepository.getReferenceById(command.teacherId());
        School school = schoolCommandRepository.getReferenceById(command.schoolId());

        SchoolClass schoolClass = SchoolClass.builder()
                .schoolClassId(command.schoolClassId())
                .name(command.className())
                .school(school)
                .teacher(teacher)
                .properties(configurationProperties)
                .build();

        schoolClassCommandRepository.save(schoolClass);
    }
}
