package pl.edziennik.eDziennik.server.student.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.edziennik.eDziennik.server.schoolclass.domain.SchoolClass;
import pl.edziennik.eDziennik.server.basics.BasicEntity;
import pl.edziennik.eDziennik.server.basics.BasicUser;
import pl.edziennik.eDziennik.server.ratingsubjectstudent.domain.RatingSubjectStudentLink;
import pl.edziennik.eDziennik.server.school.domain.School;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@NoArgsConstructor
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

    public Student(String adress, String username, String password, String firstName, String lastName, String postalCode, String PESEL, String city, String parentFirstName, String parentLastName, String parentPhoneNumber) {
        super(adress, username, password, firstName, lastName, postalCode, PESEL, city);
        this.parentFirstName = parentFirstName;
        this.parentLastName = parentLastName;
        this.parentPhoneNumber = parentPhoneNumber;
    }

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
