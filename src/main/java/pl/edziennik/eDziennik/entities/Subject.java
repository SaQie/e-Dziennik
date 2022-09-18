package pl.edziennik.eDziennik.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @OneToMany(mappedBy = "subject")
    private Set<RatingSubjectStudentLink> ratingSubjectStudentLinks;

    @OneToMany(mappedBy = "subject")
    private Set<SubjectClassLink> subjectClassLinks = new HashSet<>();

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
