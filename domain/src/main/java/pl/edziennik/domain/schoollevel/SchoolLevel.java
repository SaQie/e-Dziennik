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
            @AttributeOverride(name = "value", column = @Column(name = "name"))
    })
    private Name name;

    public static SchoolLevel of(SchoolLevelId schoolLevelId, Name name) {
        SchoolLevel schoolLevel = new SchoolLevel();
        schoolLevel.schoolLevelId = schoolLevelId;
        schoolLevel.name = name;

        return schoolLevel;
    }

    @OneToMany(mappedBy = "schoolLevel", orphanRemoval = true)
    private final List<School> schools = new ArrayList<>();

    public enum SchoolLevelConst {

        PRIMARY_SCHOOL("Szkoła podstawowa", 1L),
        HIGH_SCHOOL("Szkoła średnia", 2L),
        UNIVERSITY("Studia", 3L);

        private final Long id;
        private final String name;

        SchoolLevelConst(String name, Long id) {
            this.id = id;
            this.name = name;
        }
    }

}
