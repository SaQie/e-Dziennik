package pl.edziennik.eDziennik.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rating;
    private int weight;
    private String description;

    @OneToMany(mappedBy = "rating")
    private Set<RatingSubjectStudentLink> ratingSubjectStudentLinks = new HashSet<>();



    public void addRatingSubjectStudentLinks(RatingSubjectStudentLink ratingSubjectStudentLink){
        ratingSubjectStudentLinks.add(ratingSubjectStudentLink);
        ratingSubjectStudentLink.setRating(this);
    }

}
