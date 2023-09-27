package pl.edziennik.application.command.teacherschedule.delete;

import org.junit.jupiter.api.Test;
import pl.edziennik.application.BaseUnitTest;
import pl.edziennik.application.mock.repositories.RoleCommandMockRepo;
import pl.edziennik.common.valueobject.vo.TimeFrame;
import pl.edziennik.domain.school.School;
import pl.edziennik.domain.teacher.Teacher;
import pl.edziennik.domain.teacher.TeacherSchedule;
import pl.edziennik.domain.user.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeleteTeacherScheduleCommandHandlerTest extends BaseUnitTest {

    // 10:00 -> 10:30
    private static final LocalDateTime DATE_2022_01_01_10_00 = LocalDateTime.of(2022, 1, 1, 10, 0);
    private static final LocalDateTime DATE_2022_01_01_10_30 = LocalDateTime.of(2022, 1, 1, 10, 30);

    private final DeleteTeacherScheduleCommandHandler handler;

    public DeleteTeacherScheduleCommandHandlerTest() {
        this.handler = new DeleteTeacherScheduleCommandHandler(teacherScheduleCommandRepository);
    }

    @Test
    public void shouldDeleteTeacherSchedule(){
        // given
        User user = createUser("Test", "Test123@o2.pl", RoleCommandMockRepo.TEACHER_ROLE_NAME.value());
        School school = createSchool("Testowa", "123123", "123123", address);
        Teacher teacher = createTeacher(user, school, personInformation, address);
        TimeFrame timeFrame = TimeFrame.of(DATE_2022_01_01_10_00, DATE_2022_01_01_10_30);
        TeacherSchedule teacherSchedule = createTeacherSchedule(teacher, timeFrame);

        assertTrue(teacherScheduleCommandRepository.findById(teacherSchedule.teacherScheduleId()).isPresent());
        DeleteTeacherScheduleCommand command = new DeleteTeacherScheduleCommand(teacherSchedule.teacherScheduleId());

        // when
        handler.handle(command);

        // then
        assertFalse(teacherScheduleCommandRepository.findById(teacherSchedule.teacherScheduleId()).isPresent());
    }
}
