package pl.edziennik.application.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import pl.edziennik.application.BaseIntegrationTest;
import pl.edziennik.application.command.classroomschedule.create.CreateClassRoomScheduleCommand;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.common.valueobject.id.ClassRoomId;
import pl.edziennik.common.valueobject.id.ClassRoomScheduleId;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.vo.Description;
import pl.edziennik.common.valueobject.vo.TimeFrame;
import pl.edziennik.domain.classroom.ClassRoomSchedule;
import pl.edziennik.infrastructure.spring.exception.BusinessException;
import pl.edziennik.infrastructure.validator.ValidationError;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DirtiesContext
public class ClassRoomScheduleIntegrationTest extends BaseIntegrationTest {

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
    public void shouldCreateNewClassRoomSchedule() {
        // given
        SchoolId schoolId = createSchool("TEST", "123123", "123123");
        ClassRoomId classRoomId = createClassRoom(schoolId, "122A");

        CreateClassRoomScheduleCommand command = new CreateClassRoomScheduleCommand(classRoomId, Description.of("TEST"),
                DATE_2022_01_01_10_00, DATE_2022_01_01_10_30);

        // when
        OperationResult operationResult = dispatcher.dispatch(command);

        // then
        ClassRoomSchedule classRoomSchedule = classRoomScheduleCommandRepository.getReferenceById(ClassRoomScheduleId.of(operationResult.identifier().id()));
        assertNotNull(classRoomSchedule);
    }

    @Test
    public void shouldThrowExceptionIfThereAreAnyClassRoomScheduleConflicts() {
        // given
        SchoolId schoolId = createSchool("TEST", "123123", "123123");
        ClassRoomId classRoomId = createClassRoom(schoolId, "122A");
        createClassRoomSchedule(classRoomId, TimeFrame.of(DATE_2022_01_01_10_00, DATE_2022_01_01_10_30));

        CreateClassRoomScheduleCommand command = new CreateClassRoomScheduleCommand(classRoomId, Description.of("TEST"),
                DATE_2022_01_01_10_00, DATE_2022_01_01_10_30);

        try {
            // when
            dispatcher.dispatch(command);
            Assertions.fail("Should throw exception if there are any class room schedule conflicts");
            // then
        } catch (BusinessException e) {
            List<ValidationError> errors = e.getErrors();
            assertEquals(1, errors.size());
            assertThat(errors)
                    .extracting(ValidationError::field)
                    .containsExactlyInAnyOrder(CreateClassRoomScheduleCommand.START_DATE);
        }
    }
}
