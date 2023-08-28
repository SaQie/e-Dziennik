package pl.edziennik.common.view.classroom.classroomsforschool;

import pl.edziennik.common.valueobject.id.ClassRoomId;
import pl.edziennik.common.valueobject.vo.ClassRoomName;

public record ClassRoomForSchoolView(
        ClassRoomId classRoomId,
        ClassRoomName classRoomName
) {
}
