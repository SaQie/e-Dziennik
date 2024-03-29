package pl.edziennik.application.command.schoolclass.changeconfig;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.edziennik.application.BaseUnitTest;
import pl.edziennik.application.mock.repositories.RoleCommandMockRepo;
import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.domain.school.School;
import pl.edziennik.domain.schoolclass.SchoolClass;
import pl.edziennik.domain.schoolclass.SchoolClassConfiguration;
import pl.edziennik.domain.teacher.Teacher;
import pl.edziennik.domain.user.User;
import pl.edziennik.infrastructure.spring.exception.BusinessException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChangeSchoolClassConfigurationValuesHandlerTest extends BaseUnitTest {

    private final ChangeSchoolClassConfigurationValuesCommandHandler handler;

    public ChangeSchoolClassConfigurationValuesHandlerTest() {
        this.handler = new ChangeSchoolClassConfigurationValuesCommandHandler(resourceCreator, schoolClassCommandRepository, schoolClassConfigurationCommandRepository);
    }

    @Test
    public void shouldThrowExceptionWhenSchoolClassNotExists(){
        // given
        ChangeSchoolClassConfigurationValuesCommand command =
                new ChangeSchoolClassConfigurationValuesCommand(
                        SchoolClassId.create(),
                        50,
                        Boolean.FALSE
                );

        // when
        // then
        Assertions.assertThatThrownBy(() -> handler.handle(command))
                .isInstanceOf(BusinessException.class)
                .hasMessage(assertNotFoundMessage());

    }

    @Test
    public void shouldChangeSchoolClassConfigurationValue() {
        // given
        School school = createSchool("Testowa", "!23123", "123123", address);
        User user = createUser("Test", "test@example.com", RoleCommandMockRepo.TEACHER_ROLE_NAME.value());
        Teacher teacher = createTeacher(user, school, personInformation, address);
        SchoolClass schoolClass = createSchoolClass("1A", school, teacher);
        schoolClass = schoolClassCommandRepository.save(schoolClass);

        ChangeSchoolClassConfigurationValuesCommand command =
                new ChangeSchoolClassConfigurationValuesCommand(
                        schoolClass.schoolClassId(),
                        50,
                        Boolean.FALSE
                );
        // when
        handler.handle(command);

        // then
        SchoolClassConfiguration config = schoolClassConfigurationCommandRepository.getSchoolClassConfigurationBySchoolClassConfigurationId(schoolClass.schoolClassConfiguration().schoolClassConfigurationId());
        assertEquals(config.maxStudentsSize(), 50);
        assertEquals(config.autoAssignSubjects(), Boolean.FALSE);
    }
}
