package pl.edziennik.eDziennik.server.rating;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import pl.edziennik.eDziennik.server.basics.BasicEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Rating implements BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rating;
    private int weight;
    private String description;

    @OneToMany(mappedBy = "rating")
    private Collection<RatingSubjectStudentLink> ratingSubjectStudentLinks = new ArrayList<>();



    public void addRatingSubjectStudentLinks(RatingSubjectStudentLink ratingSubjectStudentLink){
        ratingSubjectStudentLinks.add(ratingSubjectStudentLink);
        ratingSubjectStudentLink.setRating(this);
    }

}
