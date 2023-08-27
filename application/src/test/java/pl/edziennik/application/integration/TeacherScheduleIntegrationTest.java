package pl.edziennik.application.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import pl.edziennik.application.BaseIntegrationTest;
import pl.edziennik.application.command.teacherschedule.create.CreateTeacherScheduleCommand;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.common.valueobject.id.TeacherScheduleId;
import pl.edziennik.common.valueobject.vo.Description;
import pl.edziennik.common.valueobject.vo.TimeFrame;
import pl.edziennik.domain.teacher.TeacherSchedule;
import pl.edziennik.infrastructure.spring.exception.BusinessException;
import pl.edziennik.infrastructure.validator.ValidationError;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DirtiesContext
public class TeacherScheduleIntegrationTest extends BaseIntegrationTest {

    // 10:00 -> 10:30
    private static final LocalDateTime DATE_2022_01_01_10_00 = LocalDateTime.of(2022, 1, 1, 10, 0);
    private static final LocalDateTime DATE_2022_01_01_10_30 = LocalDateTime.of(2022, 1, 1, 10, 30);

    // 11:00 -> 11:30
    private static final LocalDateTime DATE_2022_01_01_11_00 = LocalDateTime.of(2022, 1, 1, 11, 0);
    private static final LocalDateTime DATE_2022_01_01_11_30 = LocalDateTime.of(2022, 1, 1, 11, 30);

    // 12:00 -> 12:30
    private static final LocalDateTime DATE_2022_01_01_12_00 = LocalDateTime.of(2022, 1, 1, 12, 0);
    private static final LocalDateTime DATE_2022_01_01_12_30 = LocalDateTime.of(2022, 1, 1, 12, 30);

    @Test
    public void shouldCreateNewTeacherSchedule() {
        // given
        SchoolId schoolId = createSchool("TEST", "123123123", "123123123");
        TeacherId teacherId = createTeacher("TEST", "test@o2.pl", "123123123", schoolId);

        CreateTeacherScheduleCommand command = new CreateTeacherScheduleCommand(teacherId,
                Description.of("TEST"), DATE_2022_01_01_10_00, DATE_2022_01_01_10_30);

        // when
        OperationResult operationResult = dispatcher.dispatch(command);

        // then
        TeacherSchedule teacherSchedule = teacherScheduleCommandRepository.getReferenceById(TeacherScheduleId.of(operationResult.identifier().id()));
        assertNotNull(teacherSchedule);

    }

    @Test
    public void shouldThrowExceptionIfThereAreAnyTeacherScheduleConflicts() {
        // given
        SchoolId schoolId = createSchool("TEST", "123123123", "123123123");
        TeacherId teacherId = createTeacher("TEST", "test@o2.pl", "123123123", schoolId);

        createTeacherSchedule(teacherId, TimeFrame.of(DATE_2022_01_01_10_00, DATE_2022_01_01_10_30));

        CreateTeacherScheduleCommand command = new CreateTeacherScheduleCommand(teacherId,
                Description.of("TEST"), DATE_2022_01_01_10_00, DATE_2022_01_01_10_30);

        try {
            // when
            dispatcher.dispatch(command);
            Assertions.fail("Should throw exception if there is any schedule conflicts");
            // then
        } catch (BusinessException e) {
            List<ValidationError> errors = e.getErrors();
            assertEquals(1, errors.size());
            assertThat(errors)
                    .extracting(ValidationError::field)
                    .containsExactlyInAnyOrder(CreateTeacherScheduleCommand.START_DATE);
        }
    }

}
