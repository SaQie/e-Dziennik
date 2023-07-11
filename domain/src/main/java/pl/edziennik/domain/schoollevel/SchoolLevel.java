package pl.edziennik.domain.schoollevel;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.edziennik.common.valueobject.Name;
import pl.edziennik.common.valueobject.id.SchoolLevelId;
import pl.edziennik.domain.school.School;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
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

    public static SchoolLevel of(SchoolLevelId schoolLevelId, Name name) {
        SchoolLevel schoolLevel = new SchoolLevel();
        schoolLevel.schoolLevelId = schoolLevelId;
        schoolLevel.name = name;

        return schoolLevel;
    }

    public static SchoolLevel of(Name name) {
        SchoolLevel schoolLevel = new SchoolLevel();
        schoolLevel.name = name;

        return schoolLevel;
    }

}
