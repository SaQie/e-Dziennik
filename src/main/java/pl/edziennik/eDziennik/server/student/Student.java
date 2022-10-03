package pl.edziennik.eDziennik.server.student;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.edziennik.eDziennik.server.schoolclass.SchoolClass;
import pl.edziennik.eDziennik.server.basics.BasicEntity;
import pl.edziennik.eDziennik.server.basics.BasicUser;
import pl.edziennik.eDziennik.server.rating.RatingSubjectStudentLink;
import pl.edziennik.eDziennik.server.school.School;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Student extends BasicUser implements BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String parentFirstName;
    private String parentLastName;
    private String parentPhoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_class_id")
    private SchoolClass schoolClass;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private School school;

    @OneToMany(mappedBy = "student")
    private Collection<RatingSubjectStudentLink> ratingSubjectStudentLinks = new ArrayList<>();

    public void setSchoolClass(SchoolClass schoolClass) {
        this.schoolClass = schoolClass;
    }

    public void addRatingSubjectStudentLink(RatingSubjectStudentLink ratingSubjectStudentLink){
        ratingSubjectStudentLinks.add(ratingSubjectStudentLink);
        ratingSubjectStudentLink.setStudent(this);
    }

    public void setSchool(School school) {
        this.school = school;
    }
}
