package pl.edziennik.domain.subject;

import jakarta.persistence.*;
import lombok.*;
import pl.edziennik.common.valueobject.Description;
import pl.edziennik.common.valueobject.Name;
import pl.edziennik.common.valueobject.id.SubjectId;
import pl.edziennik.domain.schoolclass.SchoolClass;
import pl.edziennik.domain.teacher.Teacher;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Subject {


    @EmbeddedId
    private SubjectId subjectId = SubjectId.create();

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "description"))
    })
    private Description description;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "subject_name"))
    })
    private Name name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    private SchoolClass schoolClass;


    public static Subject of(Name subjectName, Description description, SchoolClass schoolClass) {
        Subject subject = new Subject();
        subject.description = description;
        subject.name = subjectName;
        subject.schoolClass = schoolClass;

        return subject;
    }

    public static Subject of(Name subjectName, Description description, SchoolClass schoolClass, Teacher teacher) {
        Subject subject = of(subjectName, description, schoolClass);
        subject.teacher = teacher;

        return subject;
    }


}
