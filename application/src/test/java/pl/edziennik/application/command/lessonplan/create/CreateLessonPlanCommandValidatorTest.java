package pl.edziennik.application.command.lessonplan.create;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edziennik.application.BaseUnitTest;
import pl.edziennik.application.mock.repositories.RoleCommandMockRepo;
import pl.edziennik.common.valueobject.id.*;
import pl.edziennik.common.valueobject.vo.TimeFrame;
import pl.edziennik.domain.classroom.ClassRoom;
import pl.edziennik.domain.school.School;
import pl.edziennik.domain.schoolclass.SchoolClass;
import pl.edziennik.domain.subject.Subject;
import pl.edziennik.domain.teacher.Teacher;
import pl.edziennik.domain.user.User;
import pl.edziennik.infrastructure.spring.exception.BusinessException;
import pl.edziennik.infrastructure.validator.ValidationError;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateLessonPlanCommandValidatorTest extends BaseUnitTest {

    // 10:00 -> 10:30
    private static final LocalDateTime DATE_2022_01_01_10_00 = LocalDateTime.of(2022, 1, 1, 10, 0);
    private static final LocalDateTime DATE_2022_01_01_10_30 = LocalDateTime.of(2022, 1, 1, 10, 30);

    // 12:00
    private static final LocalDateTime DATE_2022_01_01_12_00 = LocalDateTime.of(2022, 1, 1, 12, 00);
    private static final LocalDateTime DATE_2022_01_01_12_01 = LocalDateTime.of(2022, 1, 1, 12, 01);


    private final CreateLessonPlanCommandValidator validator;

    public CreateLessonPlanCommandValidatorTest() {
        this.validator = new CreateLessonPlanCommandValidator(teacherCommandRepository, subjectCommandRepository,
                classRoomCommandRepository, schoolClassCommandRepository, teacherScheduleCommandRepository,
                classRoomScheduleCommandRepository);
    }

    private SchoolId schoolId;
    private TeacherId teacherId;
    private ClassRoomId classRoomId;
    private SchoolClassId schoolClassId;
    private SubjectId subjectId;

    @BeforeEach
    public void prepare() {
        School school = createSchool("TEST", "123123123", "132123123", address);
        User user = createUser("BUBU", "test@example.com", RoleCommandMockRepo.TEACHER_ROLE_NAME.value());
        Teacher teacher = createTeacher(user, school, personInformation, address);
        ClassRoom classRoom = createClassRoom("122A", school);
        SchoolClass schoolClass = createSchoolClass("1A", school, teacher);
        Subject subject = createSubject("Przyroda", teacher, schoolClass);

        schoolId = school.schoolId();
        teacherId = teacher.teacherId();
        classRoomId = classRoom.classRoomId();
        schoolClassId = schoolClass.schoolClassId();
        subjectId = subject.subjectId();
    }

    @Test
    public void shouldThrowExceptionIfSubjectNotExists() {
        // given
        CreateLessonPlanCommand command = new CreateLessonPlanCommand(SubjectId.create(), null, classRoomId,
                DATE_2022_01_01_10_00, DATE_2022_01_01_10_30, schoolClassId);

        // when
        // then
        Assertions.assertThatThrownBy(() -> validator.validate(command, validationErrorBuilder))
                .isInstanceOf(BusinessException.class)
                .hasMessage(
                        assertBusinessExceptionMessage(
                                CreateLessonPlanCommand.SUBJECT_ID));

    }

    @Test
    public void shouldThrowExceptionIfTeacherNotExists() {
        // given
        CreateLessonPlanCommand command = new CreateLessonPlanCommand(subjectId, TeacherId.create(), classRoomId,
                DATE_2022_01_01_10_00, DATE_2022_01_01_10_30, schoolClassId);

        // when
        // then
        Assertions.assertThatThrownBy(() -> validator.validate(command, validationErrorBuilder))
                .isInstanceOf(BusinessException.class)
                .hasMessage(
                        assertBusinessExceptionMessage(
                                CreateLessonPlanCommand.TEACHER_ID));
    }

    @Test
    public void shouldThrowExceptionIfClassRoomNotExists() {
        // given
        CreateLessonPlanCommand command = new CreateLessonPlanCommand(subjectId, null, ClassRoomId.create(),
                DATE_2022_01_01_10_00, DATE_2022_01_01_10_30, schoolClassId);

        // when
        // then
        Assertions.assertThatThrownBy(() -> validator.validate(command, validationErrorBuilder))
                .isInstanceOf(BusinessException.class)
                .hasMessage(
                        assertBusinessExceptionMessage(
                                CreateLessonPlanCommand.CLASS_ROOM_ID));
    }

    @Test
    public void shouldThrowExceptionIfSchoolClassNotExists() {
        // given
        CreateLessonPlanCommand command = new CreateLessonPlanCommand(subjectId, null, classRoomId,
                DATE_2022_01_01_10_00, DATE_2022_01_01_10_30, SchoolClassId.create());

        // when
        // then
        Assertions.assertThatThrownBy(() -> validator.validate(command, validationErrorBuilder))
                .isInstanceOf(BusinessException.class)
                .hasMessage(
                        assertBusinessExceptionMessage(
                                CreateLessonPlanCommand.SCHOOL_CLASS_ID));
    }

    @Test
    public void shouldAddErrorIfEndDateIsBeforeStartDate() {
        // given
        CreateLessonPlanCommand command = new CreateLessonPlanCommand(subjectId, null, classRoomId,
                DATE_2022_01_01_10_30, DATE_2022_01_01_10_00, schoolClassId);

        // when
        validator.validate(command, validationErrorBuilder);

        // then
        List<ValidationError> errors = validationErrorBuilder.getErrors();
        assertEquals(2, errors.size());
        assertEquals(CreateLessonPlanCommandValidator.END_DATE_CANNOT_BE_BEFORE_START_DATE, errors.get(0).message());
        assertEquals(CreateLessonPlanCommand.END_DATE, errors.get(0).field());

    }

    @Test
    public void shouldAddErrorIfTimeFrameIsGreaterThanMaxDurationFromConfiguration() {
        // given
        CreateLessonPlanCommand command = new CreateLessonPlanCommand(subjectId, null, classRoomId,
                DATE_2022_01_01_10_30, DATE_2022_01_01_12_00, schoolClassId);

        // when
        validator.validate(command, validationErrorBuilder);

        // then
        List<ValidationError> errors = validationErrorBuilder.getErrors();
        assertEquals(1, errors.size());
        assertEquals(CreateLessonPlanCommandValidator.LESSON_TIME_GREATER_THAN_CONFIGURATION_LIMIT, errors.get(0).message());
        assertEquals(CreateLessonPlanCommand.END_DATE, errors.get(0).field());

    }

    @Test
    public void shouldAddErrorIfLessonTimeDurationIsLessThanConfiguration() {
        // given
        CreateLessonPlanCommand command = new CreateLessonPlanCommand(subjectId, null, classRoomId,
                DATE_2022_01_01_12_00, DATE_2022_01_01_12_01, schoolClassId);

        // when
        validator.validate(command, validationErrorBuilder);

        // then
        List<ValidationError> errors = validationErrorBuilder.getErrors();
        assertEquals(1, errors.size());
        assertEquals(CreateLessonPlanCommandValidator.SCHEDULE_TIME_TOO_SHORT_MESSAGE_KEY, errors.get(0).message());
        assertEquals(CreateLessonPlanCommand.START_DATE, errors.get(0).field());

    }

    @Test
    public void shouldAddErrorIfTeacherScheduleConflicts() {
        // given
        Teacher teacher = teacherCommandRepository.getByTeacherId(teacherId);
        createTeacherSchedule(teacher, TimeFrame.of(DATE_2022_01_01_10_00, DATE_2022_01_01_10_30));
        CreateLessonPlanCommand command = new CreateLessonPlanCommand(subjectId, null, classRoomId,
                DATE_2022_01_01_10_00, DATE_2022_01_01_10_30, schoolClassId);

        // when
        validator.validate(command, validationErrorBuilder);

        // then
        List<ValidationError> errors = validationErrorBuilder.getErrors();
        assertEquals(1, errors.size());
        assertEquals(CreateLessonPlanCommandValidator.BUSY_TEACHER_SCHEDULE_MESSAGE_KEY, errors.get(0).message());
        assertEquals(CreateLessonPlanCommand.TEACHER_ID, errors.get(0).field());

    }

    @Test
    public void shouldAddErrorIfClassRoomScheduleConflicts() {
        // given
        ClassRoom classRoom = classRoomCommandRepository.getById(classRoomId);
        createClassRoomSchedule(classRoom, TimeFrame.of(DATE_2022_01_01_10_00, DATE_2022_01_01_10_30));
        CreateLessonPlanCommand command = new CreateLessonPlanCommand(subjectId, null, classRoomId,
                DATE_2022_01_01_10_00, DATE_2022_01_01_10_30, schoolClassId);

        // when
        validator.validate(command, validationErrorBuilder);

        // then
        List<ValidationError> errors = validationErrorBuilder.getErrors();
        assertEquals(1, errors.size());
        assertEquals(CreateLessonPlanCommandValidator.BUSY_CLASS_ROOM_SCHEDULE_MESSAGE_KEY, errors.get(0).message());
        assertEquals(CreateLessonPlanCommand.CLASS_ROOM_ID, errors.get(0).field());

    }
}
