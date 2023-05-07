package pl.edziennik.domain.schoollevel;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.edziennik.domain.school.School;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@IdClass(SchoolLevelId.class)
@EqualsAndHashCode
public class SchoolLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "school_level_id_seq")
    @SequenceGenerator(name = "school_level_id_seq", sequenceName = "school_level_id_seq", allocationSize = 1)
    @Getter(AccessLevel.NONE)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "schoolLevel", orphanRemoval = true)
    private final List<School> schools = new ArrayList<>();

    public SchoolLevel(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addSchool(School school) {
        schools.add(school);
        school.setSchoolLevel(this);
    }

    public SchoolLevelId getSchoolLevelId() {
        return SchoolLevelId.wrap(id);
    }


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
