package pl.edziennik.application.command.teacherschedule.create;

import org.junit.jupiter.api.Test;
import pl.edziennik.application.BaseUnitTest;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.common.valueobject.id.TeacherScheduleId;
import pl.edziennik.common.valueobject.vo.Description;
import pl.edziennik.domain.school.School;
import pl.edziennik.domain.teacher.Teacher;
import pl.edziennik.domain.teacher.TeacherSchedule;
import pl.edziennik.domain.user.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class CreateTeacherScheduleCommandHandlerTest extends BaseUnitTest {

    // 10:00 -> 10:30
    private static final LocalDateTime DATE_2022_01_01_10_00 = LocalDateTime.of(2022, 1, 1, 10, 0);
    private static final LocalDateTime DATE_2022_01_01_10_30 = LocalDateTime.of(2022, 1, 1, 10, 30);

    private final CreateTeacherScheduleCommandHandler handler;

    public CreateTeacherScheduleCommandHandlerTest() {
        this.handler = new CreateTeacherScheduleCommandHandler(teacherCommandRepository, teacherScheduleCommandRepository);
    }


    @Test
    public void shouldCreateTeacherSchedule() {
        // given
        User user = createUser("TEST", "TEST@EXAMPLE.COM", "TEACHER");
        School school = createSchool("TEST", "123123", "123123", address);
        Teacher teacher = createTeacher(user, school, personInformation, address);
        teacher = teacherCommandRepository.save(teacher);

        CreateTeacherScheduleCommand command =
                new CreateTeacherScheduleCommand(teacher.teacherId(), Description.of("TEST"), DATE_2022_01_01_10_00, DATE_2022_01_01_10_30);

        // when
        OperationResult operationResult = handler.handle(command);

        // then
        TeacherSchedule teacherSchedule = teacherScheduleCommandRepository.getReferenceById(TeacherScheduleId.of(operationResult.identifier().id()));
        assertNotNull(teacherSchedule);
    }
}
