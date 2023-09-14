package pl.edziennik.application.integration;

import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import pl.edziennik.application.BaseIntegrationTest;
import pl.edziennik.application.command.lessonplan.create.CreateLessonPlanCommand;
import pl.edziennik.common.valueobject.id.*;
import pl.edziennik.domain.classroom.ClassRoomSchedule;
import pl.edziennik.domain.lessonplan.LessonPlan;
import pl.edziennik.domain.teacher.TeacherSchedule;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DirtiesContext
public class LessonPlanIntegrationTest extends BaseIntegrationTest {

    private static final LocalDateTime DATE_2022_01_01_10_00 = LocalDateTime.of(2022, 1, 1, 10, 0);
    private static final LocalDateTime DATE_2022_01_01_10_30 = LocalDateTime.of(2022, 1, 1, 10, 30);

    @Test
    public void shouldCreateLessonPlan() {
        // given
        SchoolId schoolId = createSchool("asdads", "123123", "123123");
        TeacherId teacherId = createTeacher("Test", "test@example.com", "123123", schoolId);
        SchoolClassId schoolClassId = createSchoolClass(schoolId, teacherId, "1");
        ClassRoomId classRoomId = createClassRoom(schoolId, "122A");
        SubjectId subjectId = transactionTemplate.execute((x) -> createSubject("Przyroda", schoolClassId, teacherId));

        CreateLessonPlanCommand command = new CreateLessonPlanCommand(subjectId, null, classRoomId,
                DATE_2022_01_01_10_00, DATE_2022_01_01_10_30, schoolClassId);

        // when
        transactionTemplate.executeWithoutResult(x -> dispatcher.run(command));

        // then
        LessonPlan lessonPlan = lessonPlanCommandRepository.getById(command.lessonPlanId());
        assertNotNull(lessonPlan);

        List<TeacherSchedule> teacherSchedulesInTimeFrame = teacherScheduleCommandRepository.getTeacherSchedulesInTimeFrame(DATE_2022_01_01_10_00, DATE_2022_01_01_10_30, teacherId);
        assertEquals(1, teacherSchedulesInTimeFrame.size());

        List<ClassRoomSchedule> classRoomSchedulesInTimeFrame = classRoomScheduleCommandRepository.getClassRoomSchedulesInTimeFrame(DATE_2022_01_01_10_00, DATE_2022_01_01_10_30, classRoomId);
        assertEquals(1, classRoomSchedulesInTimeFrame.size());

    }

    @Test
    public void shouldSetLessonPlanAsSubstituteWhenTeacherIsProvided() {
        // given
        SchoolId schoolId = createSchool("asdads", "123123", "123123");
        TeacherId teacherId = createTeacher("Test", "test@example.com", "123123", schoolId);
        SchoolClassId schoolClassId = createSchoolClass(schoolId, teacherId, "1");
        ClassRoomId classRoomId = createClassRoom(schoolId, "122A");
        SubjectId subjectId = transactionTemplate.execute((x) -> createSubject("Przyroda", schoolClassId, teacherId));
        TeacherId teacherIdSecond = createTeacher("Test2", "test@exampleee.com", "123123222", schoolId);

        CreateLessonPlanCommand command = new CreateLessonPlanCommand(subjectId, teacherIdSecond, classRoomId,
                DATE_2022_01_01_10_00, DATE_2022_01_01_10_30, schoolClassId);

        // when
        transactionTemplate.executeWithoutResult(x -> dispatcher.run(command));

        // then
        transactionTemplate.executeWithoutResult((x) -> {
            LessonPlan lessonPlan = lessonPlanCommandRepository.getById(command.lessonPlanId());
            assertNotNull(lessonPlan);
            assertTrue(lessonPlan.isSubstitute());
        });

        List<TeacherSchedule> teacherSchedulesInTimeFrame = teacherScheduleCommandRepository.getTeacherSchedulesInTimeFrame(DATE_2022_01_01_10_00, DATE_2022_01_01_10_30, teacherIdSecond);
        assertEquals(1, teacherSchedulesInTimeFrame.size());

        List<ClassRoomSchedule> classRoomSchedulesInTimeFrame = classRoomScheduleCommandRepository.getClassRoomSchedulesInTimeFrame(DATE_2022_01_01_10_00, DATE_2022_01_01_10_30, classRoomId);
        assertEquals(1, classRoomSchedulesInTimeFrame.size());

    }

}
