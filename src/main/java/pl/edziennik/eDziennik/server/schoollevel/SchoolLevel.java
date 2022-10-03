package pl.edziennik.eDziennik.server.schoollevel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.edziennik.eDziennik.server.school.School;
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

}
