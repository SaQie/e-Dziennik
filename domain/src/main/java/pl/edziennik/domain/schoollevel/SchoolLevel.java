package pl.edziennik.domain.schoollevel;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import pl.edziennik.common.valueobject.vo.Name;
import pl.edziennik.common.valueobject.id.SchoolLevelId;
import pl.edziennik.domain.school.School;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class SchoolLevel {

    @EmbeddedId
    private SchoolLevelId schoolLevelId = SchoolLevelId.create();

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "name", nullable = false))
    })
    private Name name;

    @OneToMany(mappedBy = "schoolLevel", orphanRemoval = true)
    private final List<School> schools = new ArrayList<>();

    @Version
    private Long version;

    @Builder
    public static SchoolLevel of(SchoolLevelId schoolLevelId, Name name) {
        SchoolLevel schoolLevel = new SchoolLevel();

        schoolLevel.schoolLevelId = schoolLevelId;
        schoolLevel.name = name;

        return schoolLevel;
    }

}
