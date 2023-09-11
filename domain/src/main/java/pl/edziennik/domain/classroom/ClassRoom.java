package pl.edziennik.domain.classroom;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import pl.edziennik.common.valueobject.id.ClassRoomId;
import pl.edziennik.common.valueobject.vo.ClassRoomName;
import pl.edziennik.domain.school.School;

@Entity
@Getter
@Accessors(fluent = true)
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ClassRoom {

    @EmbeddedId
    private ClassRoomId classRoomId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private School school;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "class_room_name", nullable = false))
    })
    private ClassRoomName classRoomName;

    @Version
    private Long version;


    @Builder
    public static ClassRoom of(ClassRoomId classRoomId, School school, ClassRoomName classRoomName) {
        ClassRoom classRoom = new ClassRoom();

        classRoom.school = school;
        classRoom.classRoomName = classRoomName;
        classRoom.classRoomId = classRoomId;

        return classRoom;
    }

    public void changeName(ClassRoomName classRoomName) {
        this.classRoomName = classRoomName;
    }

}
