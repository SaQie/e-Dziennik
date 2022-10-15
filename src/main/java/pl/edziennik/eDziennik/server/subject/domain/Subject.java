package pl.edziennik.eDziennik.server.subject.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.edziennik.eDziennik.server.ratingsubjectstudent.domain.RatingSubjectStudentLink;
import pl.edziennik.eDziennik.server.subjectclass.domain.SubjectClassLink;
import pl.edziennik.eDziennik.server.teacher.domain.Teacher;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@NoArgsConstructor
@Getter
public class Subject{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_teacher")
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    private RatingSubjectStudentLink ratingSubjectStudentLink;

    @OneToMany(mappedBy = "subject")
    private Collection<SubjectClassLink> subjectClassLinks = new ArrayList<>();

    public Subject(String name, String description) {
        this.description = description;
        this.name = name;
    }

    public void addSubjectClassLinks(SubjectClassLink subjectClassLink){
        subjectClassLinks.add(subjectClassLink);
        subjectClassLink.setSubject(this);
    }
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}
