package pl.edziennik.domain.schoolclass;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import pl.edziennik.common.properties.SchoolClassConfigurationProperties;
import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.common.valueobject.vo.Name;
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
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class SchoolClass {

    @EmbeddedId
    private SchoolClassId schoolClassId;

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

    @Builder
    public static SchoolClass of(SchoolClassId schoolClassId, Name name, School school, Teacher teacher, SchoolClassConfigurationProperties properties) {
        SchoolClass schoolClass = new SchoolClass();

        schoolClass.schoolClassId = schoolClassId;
        schoolClass.className = name;
        schoolClass.school = school;
        schoolClass.teacher = teacher;
        schoolClass.createdDate = LocalDate.now();

        schoolClass.schoolClassConfiguration = SchoolClassConfiguration.createConfigFromProperties(properties);

        return schoolClass;
    }


}
