package pl.edziennik.application.command.lessonplan.create;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.edziennik.application.BaseUnitTest;
import pl.edziennik.application.mock.repositories.RoleCommandMockRepo;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.domain.classroom.ClassRoom;
import pl.edziennik.domain.lessonplan.LessonPlan;
import pl.edziennik.domain.school.School;
import pl.edziennik.domain.schoolclass.SchoolClass;
import pl.edziennik.domain.subject.Subject;
import pl.edziennik.domain.teacher.Teacher;
import pl.edziennik.domain.user.User;
import pl.edziennik.infrastructure.spring.exception.BusinessException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CreateLessonPlanCommandHandlerTest extends BaseUnitTest {

    // 10:00 -> 10:30
    private static final LocalDateTime DATE_2022_01_01_10_00 = LocalDateTime.of(2022, 1, 1, 10, 0);
    private static final LocalDateTime DATE_2022_01_01_10_30 = LocalDateTime.of(2022, 1, 1, 10, 30);

    private final CreateLessonPlanCommandHandler handler;

    public CreateLessonPlanCommandHandlerTest() {
        this.handler = new CreateLessonPlanCommandHandler(lessonPlanCommandRepository, classRoomCommandRepository,
                subjectCommandRepository, schoolClassCommandRepository,
                teacherCommandRepository, publisher, resourceCreator);
    }

    @Test
    public void shouldCreateLessonPlan() {
        // given
        School school = createSchool("TEST", "123123123", "132123123", address);
        User user = createUser("BUBU", "test@example.com", RoleCommandMockRepo.TEACHER_ROLE_NAME.value());
        Teacher teacher = createTeacher(user, school, personInformation, address);
        ClassRoom classRoom = createClassRoom("122A", school);
        SchoolClass schoolClass = createSchoolClass("1A", school, teacher);
        Subject subject = createSubject("Przyroda", teacher, schoolClass);

        CreateLessonPlanCommand command = new CreateLessonPlanCommand(subject.subjectId(), null, classRoom.classRoomId(),
                DATE_2022_01_01_10_00, DATE_2022_01_01_10_30, schoolClass.schoolClassId());

        // when
        handler.handle(command);

        // then
        LessonPlan lessonPlan = lessonPlanCommandRepository.getById(command.lessonPlanId());
        assertNotNull(lessonPlan);
    }

    @Test
    public void shouldThrowsExceptionIfSubstituteTeacherIsGivenAndTeacherNotFound() {
        // given
        School school = createSchool("TEST", "123123123", "132123123", address);
        User user = createUser("BUBU", "test@example.com", RoleCommandMockRepo.TEACHER_ROLE_NAME.value());
        Teacher teacher = createTeacher(user, school, personInformation, address);
        ClassRoom classRoom = createClassRoom("122A", school);
        SchoolClass schoolClass = createSchoolClass("1A", school, teacher);
        Subject subject = createSubject("Przyroda", teacher, schoolClass);

        CreateLessonPlanCommand command = new CreateLessonPlanCommand(subject.subjectId(), TeacherId.create(), classRoom.classRoomId(),
                DATE_2022_01_01_10_00, DATE_2022_01_01_10_30, schoolClass.schoolClassId());

        // when
        // then
        Assertions.assertThatThrownBy(() -> handler.handle(command))
                .isInstanceOf(BusinessException.class)
                .hasMessage(assertNotFoundMessage());
    }
}
