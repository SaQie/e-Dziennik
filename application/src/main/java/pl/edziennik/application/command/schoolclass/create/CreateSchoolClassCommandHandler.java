package pl.edziennik.application.command.schoolclass.create;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.command.ICommandHandler;
import pl.edziennik.common.properties.SchoolClassConfigurationProperties;
import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.domain.school.School;
import pl.edziennik.domain.schoolclass.SchoolClass;
import pl.edziennik.domain.teacher.Teacher;
import pl.edziennik.infrastructure.repository.school.SchoolCommandRepository;
import pl.edziennik.infrastructure.repository.schoolclass.SchoolClassCommandRepository;
import pl.edziennik.infrastructure.repository.teacher.TeacherCommandRepository;

@Component
@AllArgsConstructor
class CreateSchoolClassCommandHandler implements ICommandHandler<CreateSchoolClassCommand, OperationResult> {

    private final SchoolCommandRepository schoolCommandRepository;
    private final TeacherCommandRepository teacherCommandRepository;
    private final SchoolClassCommandRepository schoolClassCommandRepository;
    private final SchoolClassConfigurationProperties configurationProperties;

    @Override
    @CacheEvict(allEntries = true, value = "schoolClasses")
    public OperationResult handle(CreateSchoolClassCommand command) {
        Teacher teacher = teacherCommandRepository.getReferenceById(command.teacherId());
        School school = schoolCommandRepository.getReferenceById(command.schoolId());

        SchoolClass schoolClass = SchoolClass.builder()
                .name(command.className())
                .school(school)
                .teacher(teacher)
                .properties(configurationProperties)
                .build();

        SchoolClassId schoolClassId = schoolClassCommandRepository.save(schoolClass).getSchoolClassId();

        return OperationResult.success(schoolClassId);
    }
}
