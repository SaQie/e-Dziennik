package pl.edziennik.domain.subject;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import pl.edziennik.common.valueobject.id.SubjectId;
import pl.edziennik.common.valueobject.vo.Description;
import pl.edziennik.common.valueobject.vo.Name;
import pl.edziennik.domain.schoolclass.SchoolClass;
import pl.edziennik.domain.teacher.Teacher;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Subject {


    @EmbeddedId
    private SubjectId subjectId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "description"))
    })
    private Description description;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "name", nullable = false))
    })
    private Name name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private SchoolClass schoolClass;

    @Version
    private Long version;


    @Builder
    public static Subject of(SubjectId subjectId, Name subjectName, Description description, SchoolClass schoolClass, Teacher teacher) {
        Subject subject = new Subject();

        subject.description = description;
        subject.name = subjectName;
        subject.schoolClass = schoolClass;
        subject.teacher = teacher;
        subject.subjectId = subjectId;

        schoolClass.subjects().add(subject);

        return subject;
    }


}
