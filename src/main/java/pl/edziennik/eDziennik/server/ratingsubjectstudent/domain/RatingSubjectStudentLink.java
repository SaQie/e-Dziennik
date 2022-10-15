package pl.edziennik.eDziennik.server.ratingsubjectstudent.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.edziennik.eDziennik.server.rating.domain.Rating;
import pl.edziennik.eDziennik.server.student.domain.Student;
import pl.edziennik.eDziennik.server.subject.domain.Subject;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Setter
@Getter
@Table(name = "rating_subject_student")
public class RatingSubjectStudentLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_student")
    private Student student;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rating")
    private List<Rating> ratings = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_subject")
    private List<Subject> subjects = new ArrayList<>();



}
