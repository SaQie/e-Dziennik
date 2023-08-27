package pl.edziennik.application.mock.repositories;

import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.common.valueobject.id.TeacherScheduleId;
import pl.edziennik.domain.teacher.TeacherSchedule;
import pl.edziennik.infrastructure.repository.teacherschedule.TeacherScheduleCommandRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeacherScheduleCommandMockRepo implements TeacherScheduleCommandRepository {

    private final Map<TeacherScheduleId, TeacherSchedule> database;

    public TeacherScheduleCommandMockRepo() {
        this.database = new HashMap<>();
    }

    @Override
    public TeacherSchedule getReferenceById(TeacherScheduleId teacherScheduleId) {
        return database.get(teacherScheduleId);
    }

    @Override
    public TeacherSchedule save(TeacherSchedule teacherSchedule) {
        database.put(teacherSchedule.teacherScheduleId(), teacherSchedule);
        return database.get(teacherSchedule.teacherScheduleId());
    }

    @Override
    public List<TeacherSchedule> getTeacherSchedulesInTimeFrame(LocalDateTime startDate, LocalDateTime endDate, TeacherId teacherId) {
        return database.values().stream()
                .filter(schedule -> schedule.timeFrame().startDate().isEqual(startDate))
                .filter(schedule -> schedule.timeFrame().endDate().isEqual(endDate))
                .filter(schedule -> schedule.teacher().teacherId().equals(teacherId))
                .toList();
    }
}
