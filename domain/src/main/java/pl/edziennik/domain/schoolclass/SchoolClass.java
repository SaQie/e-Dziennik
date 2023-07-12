package pl.edziennik.domain.schoolclass;

import jakarta.persistence.*;
import lombok.*;
import pl.edziennik.common.properties.SchoolClassConfigurationProperties;
import pl.edziennik.common.valueobject.Name;
import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.domain.school.School;
import pl.edziennik.domain.student.Student;
import pl.edziennik.domain.subject.Subject;
import pl.edziennik.domain.teacher.Teacher;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode
public class SchoolClass {

    @EmbeddedId
    private SchoolClassId schoolClassId = SchoolClassId.create();

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "class_name", nullable = false))
    })
    private Name className;

    @OneToMany(mappedBy = "schoolClass")
    private final List<Student> students = new ArrayList<>();

    @OneToMany(mappedBy = "schoolClass")
    private final List<Subject> subjects = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private School school;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private SchoolClassConfiguration schoolClassConfiguration;

    @Column(nullable = false)
    private LocalDate createdDate;

    @Version
    private Long version;

    public static SchoolClass of(Name name, School school, Teacher teacher, SchoolClassConfigurationProperties properties) {
        SchoolClass schoolClass = new SchoolClass();
        schoolClass.className = name;
        schoolClass.school = school;
        schoolClass.createdDate = LocalDate.now();
        schoolClass.teacher = teacher;

        schoolClass.schoolClassConfiguration = SchoolClassConfiguration.createConfigFromProperties(properties);

        return schoolClass;
    }


}
