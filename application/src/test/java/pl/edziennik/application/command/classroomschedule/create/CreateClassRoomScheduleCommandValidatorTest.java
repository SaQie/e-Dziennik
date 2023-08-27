package pl.edziennik.application.command.classroomschedule.create;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.edziennik.application.BaseUnitTest;
import pl.edziennik.common.valueobject.id.ClassRoomId;
import pl.edziennik.common.valueobject.vo.Description;
import pl.edziennik.common.valueobject.vo.TimeFrame;
import pl.edziennik.domain.classroom.ClassRoom;
import pl.edziennik.domain.school.School;
import pl.edziennik.infrastructure.spring.exception.BusinessException;
import pl.edziennik.infrastructure.validator.ValidationError;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateClassRoomScheduleCommandValidatorTest extends BaseUnitTest {

    // 10:00 -> 10:30
    private static final LocalDateTime DATE_2022_01_01_10_00 = LocalDateTime.of(2022, 1, 1, 10, 0);
    private static final LocalDateTime DATE_2022_01_01_10_30 = LocalDateTime.of(2022, 1, 1, 10, 30);

    private final CreateClassRoomScheduleCommandValidator validator;

    public CreateClassRoomScheduleCommandValidatorTest() {
        this.validator = new CreateClassRoomScheduleCommandValidator(classRoomCommandRepository, classRoomScheduleCommandRepository);
    }

    @Test
    public void shouldThrowExceptionIfClassRoomNotExists() {
        // given
        CreateClassRoomScheduleCommand command = new CreateClassRoomScheduleCommand(ClassRoomId.create(), Description.of("TEST"),
                DATE_2022_01_01_10_00, DATE_2022_01_01_10_30);

        // when
        // then
        Assertions.assertThatThrownBy(() -> validator.validate(command, validationErrorBuilder))
                .isInstanceOf(BusinessException.class)
                .hasMessage(
                        assertBusinessExceptionMessage(CreateClassRoomScheduleCommand.CLASS_ROOM_ID));
    }

    @Test
    public void shouldAddErrorWhenThereAreAnyClassRoomScheduleConflicts() {
        // given
        School school = createSchool("TEST", "123123123", "231232", address);
        school = schoolCommandRepository.save(school);

        ClassRoom classRoom = createClassRoom("122A", school);
        TimeFrame timeFrame = TimeFrame.of(DATE_2022_01_01_10_00, DATE_2022_01_01_10_30);
        createClassRoomSchedule(classRoom, timeFrame);

        CreateClassRoomScheduleCommand command = new CreateClassRoomScheduleCommand(classRoom.classRoomId(), Description.of("TEST"), DATE_2022_01_01_10_00, DATE_2022_01_01_10_30);

        // when
        validator.validate(command, validationErrorBuilder);

        // then
        List<ValidationError> errors = validationErrorBuilder.getErrors();
        assertEquals(1, errors.size());
        assertEquals(errors.get(0).field(), CreateClassRoomScheduleCommand.START_DATE);
        assertEquals(errors.get(0).message(), CreateClassRoomScheduleCommandValidator.BUSY_CLASS_ROOM_SCHEDULE_MESSAGE_KEY);
    }

    @Test
    public void shouldAddErrorWhenStartDateIsAfterEndDate() {
        // given
        School school = createSchool("TEST", "123123123", "231232", address);
        school = schoolCommandRepository.save(school);

        ClassRoom classRoom = createClassRoom("122A", school);

        CreateClassRoomScheduleCommand command = new CreateClassRoomScheduleCommand(classRoom.classRoomId(),
                Description.of("TEST"), DATE_2022_01_01_10_30, DATE_2022_01_01_10_00);

        // when
        validator.validate(command, validationErrorBuilder);

        // then
        List<ValidationError> errors = validationErrorBuilder.getErrors();
        assertEquals(1, errors.size());
        assertEquals(errors.get(0).field(), CreateClassRoomScheduleCommand.END_DATE);
        assertEquals(errors.get(0).message(), CreateClassRoomScheduleCommandValidator.END_DATE_CANNOT_BE_BEFORE_START_DATE);
    }
}
