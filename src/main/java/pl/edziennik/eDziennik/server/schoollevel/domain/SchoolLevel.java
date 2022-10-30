package pl.edziennik.eDziennik.server.schoollevel.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.edziennik.eDziennik.server.school.domain.School;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@NoArgsConstructor
@Getter
public class SchoolLevel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "school_level_id_seq")
    @SequenceGenerator(name = "school_level_id_seq", sequenceName = "school_level_id_seq", allocationSize = 1)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "schoolLevel", orphanRemoval = true)
    private Collection<School> schools = new ArrayList<>();

    public SchoolLevel(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addSchool(School school){
        schools.add(school);
        school.setSchoolLevel(this);
    }


    public enum SchoolLevelConst {

        PRIMARY_SCHOOL("Szkoła podstawowa", 1L),
        HIGH_SCHOOL("Szkoła średnia", 2L),
        UNIVERSITY("Studia", 3L);

        private Long id;
        private String name;

        SchoolLevelConst(String name, Long id) {
            this.id = id;
            this.name = name;
        }
    }

}
