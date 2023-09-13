package pl.edziennik.application.command.teacherschedule.create;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.edziennik.application.BaseUnitTest;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.common.valueobject.id.TeacherScheduleId;
import pl.edziennik.common.valueobject.vo.Description;
import pl.edziennik.common.valueobject.vo.TimeFrame;
import pl.edziennik.domain.school.School;
import pl.edziennik.domain.teacher.Teacher;
import pl.edziennik.domain.user.User;
import pl.edziennik.infrastructure.spring.exception.BusinessException;
import pl.edziennik.infrastructure.validator.ValidationError;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateTeacherScheduleCommandValidatorTest extends BaseUnitTest {

    // 10:00 -> 10:30
    private static final LocalDateTime DATE_2022_01_01_10_00 = LocalDateTime.of(2022, 1, 1, 10, 0);
    private static final LocalDateTime DATE_2022_01_01_10_30 = LocalDateTime.of(2022, 1, 1, 10, 30);

    // 10:05
    private static final LocalDateTime DATE_2022_01_01_10_05 = LocalDateTime.of(2022, 1, 1, 10, 5);

    private final CreateTeacherScheduleCommandValidator validator;

    public CreateTeacherScheduleCommandValidatorTest() {
        this.validator = new CreateTeacherScheduleCommandValidator(teacherCommandRepository, teacherScheduleCommandRepository);
    }

    @Test
    public void shouldThrowExceptionIfTeacherNotExists() {
        // given
        CreateTeacherScheduleCommand command =
                new CreateTeacherScheduleCommand(TeacherScheduleId.create(), TeacherId.create(), Description.of("TEST"),
                        DATE_2022_01_01_10_00, DATE_2022_01_01_10_30);

        // when
        // then
        Assertions.assertThatThrownBy(() -> validator.validate(command, validationErrorBuilder))
                .isInstanceOf(BusinessException.class)
                .hasMessage(
                        assertBusinessExceptionMessage(CreateTeacherScheduleCommand.TEACHER_ID));
    }

    @Test
    public void shouldAddErrorIfThereAreAnyTeacherScheduleConflicts() {
        // given
        User user = createUser("TEST", "TEST@EXAMPLE.COM", "TEACHER");
        School school = createSchool("TEST", "123123", "123123", address);
        Teacher teacher = createTeacher(user, school, personInformation, address);
        teacher = teacherCommandRepository.save(teacher);
        createTeacherSchedule(teacher, TimeFrame.of(DATE_2022_01_01_10_00, DATE_2022_01_01_10_30));

        CreateTeacherScheduleCommand command =
                new CreateTeacherScheduleCommand(TeacherScheduleId.create(),
                        teacher.teacherId(), Description.of("TEST"), DATE_2022_01_01_10_00, DATE_2022_01_01_10_30);

        // when
        validator.validate(command, validationErrorBuilder);

        // then
        List<ValidationError> errors = validationErrorBuilder.getErrors();
        assertEquals(errors.size(), 1);
        assertEquals(errors.get(0).field(), CreateTeacherScheduleCommand.START_DATE);
        assertEquals(errors.get(0).message(), CreateTeacherScheduleCommandValidator.BUSY_TEACHER_SCHEDULE_MESSAGE_KEY);
    }

    @Test
    public void shouldAddErrorIfStartDateIsAfterEndDate() {
        // given
        User user = createUser("TEST", "TEST@EXAMPLE.COM", "TEACHER");
        School school = createSchool("TEST", "123123", "123123", address);
        Teacher teacher = createTeacher(user, school, personInformation, address);
        teacher = teacherCommandRepository.save(teacher);

        CreateTeacherScheduleCommand command =
                new CreateTeacherScheduleCommand(TeacherScheduleId.create(), teacher.teacherId(),
                        Description.of("TEST"), DATE_2022_01_01_10_30, DATE_2022_01_01_10_00);

        // when
        validator.validate(command, validationErrorBuilder);

        // then
        List<ValidationError> errors = validationErrorBuilder.getErrors();
        assertEquals(2, errors.size());
        assertEquals(errors.get(0).field(), CreateTeacherScheduleCommand.END_DATE);
        assertEquals(errors.get(0).message(), CreateTeacherScheduleCommandValidator.END_DATE_CANNOT_BE_BEFORE_START_DATE);
    }

    @Test
    public void shouldAddErrorWhenTimeFrameIsTooShort() {
        // given
        User user = createUser("TEST", "TEST@EXAMPLE.COM", "TEACHER");
        School school = createSchool("TEST", "123123", "123123", address);
        Teacher teacher = createTeacher(user, school, personInformation, address);
        teacher = teacherCommandRepository.save(teacher);

        CreateTeacherScheduleCommand command =
                new CreateTeacherScheduleCommand(teacher.teacherId(),
                        Description.of("TEST"), DATE_2022_01_01_10_00, DATE_2022_01_01_10_05);

        // when
        validator.validate(command, validationErrorBuilder);

        // then
        List<ValidationError> errors = validationErrorBuilder.getErrors();
        assertEquals(errors.size(), 1);
        assertEquals(errors.get(0).field(), CreateTeacherScheduleCommand.START_DATE);
        assertEquals(errors.get(0).message(), CreateTeacherScheduleCommandValidator.SCHEDULE_TIME_TOO_SHORT);
    }
}
