package pl.edziennik.application.mock.repositories;

import pl.edziennik.common.valueobject.id.ClassRoomId;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.vo.ClassRoomName;
import pl.edziennik.domain.classroom.ClassRoom;
import pl.edziennik.infrastructure.repository.classroom.ClassRoomCommandRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ClassRoomCommandMockRepo implements ClassRoomCommandRepository {

    private final Map<ClassRoomId, ClassRoom> database;

    public ClassRoomCommandMockRepo() {
        this.database = new HashMap<>();
    }

    @Override
    public boolean isClassRoomAlreadyExists(ClassRoomName classRoomName, SchoolId schoolId) {
        List<ClassRoom> classRooms = database.values().stream()
                .filter(classRoom -> classRoom.school().schoolId().equals(schoolId))
                .filter(classRoom -> classRoom.classRoomName().equals(classRoomName))
                .toList();
        return classRooms.size() > 0;
    }

    @Override
    public ClassRoom save(ClassRoom classRoom) {
        database.put(classRoom.classRoomId(), classRoom);
        return database.get(classRoom.classRoomId());
    }

    @Override
    public Optional<ClassRoom> findById(ClassRoomId classRoomId) {
        return Optional.ofNullable(database.get(classRoomId));
    }

    @Override
    public ClassRoom getById(ClassRoomId classRoomId) {
        return database.get(classRoomId);
    }
}
