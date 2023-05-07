package pl.edziennik.domain.subject;

import jakarta.persistence.*;
import lombok.*;
import pl.edziennik.domain.schoolclass.SchoolClass;
import pl.edziennik.domain.teacher.Teacher;

@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@IdClass(SubjectId.class)
public class Subject{


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subject_id_seq")
    @SequenceGenerator(name = "subject_id_seq", sequenceName = "subject_id_seq", allocationSize = 1)
    @Getter(AccessLevel.NONE)
    private Long id;

    private String description;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    private SchoolClass schoolClass;


    public Subject(String name, String description) {
        this.description = description;
        this.name = name;
    }

    public SubjectId getSubjectId(){
        return SubjectId.wrap(id);
    }


}
