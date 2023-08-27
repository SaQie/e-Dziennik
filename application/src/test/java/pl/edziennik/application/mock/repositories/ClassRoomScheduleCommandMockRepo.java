package pl.edziennik.application.mock.repositories;

import pl.edziennik.common.valueobject.id.ClassRoomId;
import pl.edziennik.common.valueobject.id.ClassRoomScheduleId;
import pl.edziennik.domain.classroom.ClassRoomSchedule;
import pl.edziennik.infrastructure.repository.classroomschedule.ClassRoomScheduleCommandRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassRoomScheduleCommandMockRepo implements ClassRoomScheduleCommandRepository {

    private final Map<ClassRoomScheduleId, ClassRoomSchedule> database;

    public ClassRoomScheduleCommandMockRepo() {
        this.database = new HashMap<>();
    }

    @Override
    public ClassRoomSchedule getReferenceById(ClassRoomScheduleId classRoomScheduleId) {
        return database.get(classRoomScheduleId);
    }

    @Override
    public ClassRoomSchedule save(ClassRoomSchedule classRoomSchedule) {
        database.put(classRoomSchedule.classRoomScheduleId(), classRoomSchedule);
        return database.get(classRoomSchedule.classRoomScheduleId());
    }

    @Override
    public List<ClassRoomSchedule> getClassRoomSchedulesInTimeFrame(LocalDateTime startDate, LocalDateTime endDate, ClassRoomId classRoomId) {
        return database.values().stream()
                .filter(schedule -> schedule.timeFrame().startDate().isEqual(startDate))
                .filter(schedule -> schedule.timeFrame().endDate().isEqual(endDate))
                .filter(schedule -> schedule.classRoom().classRoomId().equals(classRoomId))
                .toList();
    }
}
