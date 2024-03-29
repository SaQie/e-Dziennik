package pl.edziennik.application.command.school.changeconfig;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.edziennik.application.BaseUnitTest;
import pl.edziennik.common.enums.AverageType;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.vo.TimeFrameDuration;
import pl.edziennik.domain.school.School;
import pl.edziennik.infrastructure.spring.exception.BusinessException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChangeSchoolConfigurationValuesHandlerTest extends BaseUnitTest {

    private final ChangeSchoolConfigurationValuesCommandHandler handler;

    public ChangeSchoolConfigurationValuesHandlerTest() {
        this.handler = new ChangeSchoolConfigurationValuesCommandHandler(schoolCommandRepository, schoolConfigurationCommandRepository, resourceCreator);
    }

    @Test
    public void shouldThrowExceptionWhenSchoolNotExists() {
        // given
        ChangeSchoolConfigurationValuesCommand command = new ChangeSchoolConfigurationValuesCommand(SchoolId.create(),
                AverageType.WEIGHTED, TimeFrameDuration.of(1), TimeFrameDuration.of(1));

        // when
        // then
        Assertions.assertThatThrownBy(() -> handler.handle(command))
                .isInstanceOf(BusinessException.class)
                .hasMessage(assertNotFoundMessage());

    }

    @Test
    public void shouldChangeSchoolConfigurationValues() {
        // given
        School school = createSchool("Test", "123123123", "123123", address);
        schoolCommandRepository.save(school);
        assertEquals(school.schoolConfiguration().averageType(), AverageType.ARITHMETIC);

        ChangeSchoolConfigurationValuesCommand command = new ChangeSchoolConfigurationValuesCommand(school.schoolId(), AverageType.WEIGHTED,
                TimeFrameDuration.of(1), TimeFrameDuration.of(1));

        // when
        handler.handle(command);

        // then
        school = schoolCommandRepository.getBySchoolId(school.schoolId());
        assertEquals(school.schoolConfiguration().averageType(), AverageType.WEIGHTED);
        assertEquals(school.schoolConfiguration().minScheduleTime(), TimeFrameDuration.of(1));
        assertEquals(school.schoolConfiguration().maxLessonTime(), TimeFrameDuration.of(1));
    }
}
