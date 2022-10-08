package pl.edziennik.eDziennik.server.schoollevel.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.edziennik.eDziennik.server.school.domain.School;
import pl.edziennik.eDziennik.server.basics.BasicEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@NoArgsConstructor
@Getter
public class SchoolLevel implements BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "schoolLevel")
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
