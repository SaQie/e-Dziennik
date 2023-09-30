package pl.edziennik.application.command.classroomschedule.delete;

import org.junit.jupiter.api.Test;
import pl.edziennik.application.BaseUnitTest;
import pl.edziennik.application.mock.repositories.RoleCommandMockRepo;
import pl.edziennik.common.valueobject.vo.TimeFrame;
import pl.edziennik.domain.classroom.ClassRoom;
import pl.edziennik.domain.classroom.ClassRoomSchedule;
import pl.edziennik.domain.school.School;
import pl.edziennik.domain.teacher.Teacher;
import pl.edziennik.domain.user.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class DeleteClassRoomScheduleCommandHandlerTest extends BaseUnitTest {

    // 10:00 -> 10:30
    private static final LocalDateTime DATE_2022_01_01_10_00 = LocalDateTime.of(2022, 1, 1, 10, 0);
    private static final LocalDateTime DATE_2022_01_01_10_30 = LocalDateTime.of(2022, 1, 1, 10, 30);

    private final DeleteClassRoomScheduleCommandHandler handler;

    public DeleteClassRoomScheduleCommandHandlerTest() {
        this.handler = new DeleteClassRoomScheduleCommandHandler(classRoomScheduleCommandRepository);
    }


    @Test
    public void shouldDeleteClassRoomSchedule() {
        // given
        User user = createUser("Test", "Test123@o2.pl", RoleCommandMockRepo.TEACHER_ROLE_NAME.value());
        School school = createSchool("Testowa", "123123", "123123", address);
        Teacher teacher = createTeacher(user, school, personInformation, address);
        ClassRoom classRoom = createClassRoom("122A", school);
        TimeFrame timeFrame = TimeFrame.of(DATE_2022_01_01_10_00, DATE_2022_01_01_10_30);
        ClassRoomSchedule classRoomSchedule = createClassRoomSchedule(classRoom, timeFrame);

        assertTrue(classRoomScheduleCommandRepository.findById(classRoomSchedule.classRoomScheduleId()).isPresent());
        DeleteClassRoomScheduleCommand command = new DeleteClassRoomScheduleCommand(classRoomSchedule.classRoomScheduleId());

        // when
        handler.handle(command);

        // then
        assertFalse(classRoomScheduleCommandRepository.findById(classRoomSchedule.classRoomScheduleId()).isPresent());
    }
}
