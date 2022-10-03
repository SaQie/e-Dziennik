package pl.edziennik.eDziennik.server.subject;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import pl.edziennik.eDziennik.server.rating.RatingSubjectStudentLink;
import pl.edziennik.eDziennik.server.teacher.Teacher;
import pl.edziennik.eDziennik.server.basics.BasicEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Subject implements BasicEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @OneToMany(mappedBy = "subject")
    private Collection<RatingSubjectStudentLink> ratingSubjectStudentLinks = new ArrayList<>();

    @OneToMany(mappedBy = "subject")
    private Collection<SubjectClassLink> subjectClassLinks = new ArrayList<>();

    public void addSubjectClassLinks(SubjectClassLink subjectClassLink){
        subjectClassLinks.add(subjectClassLink);
        subjectClassLink.setSubject(this);
    }

    public void addRatingSubjectStudentLink(RatingSubjectStudentLink ratingSubjectStudentLink){
        ratingSubjectStudentLinks.add(ratingSubjectStudentLink);
        ratingSubjectStudentLink.setSubject(this);
    }
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}
