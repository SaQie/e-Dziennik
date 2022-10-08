package pl.edziennik.eDziennik.server.rating.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import pl.edziennik.eDziennik.server.basics.BasicEntity;
import pl.edziennik.eDziennik.server.ratingsubjectstudent.domain.RatingSubjectStudentLink;

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

    public enum RatingConst {

        ZERO(0),
        ONE(1),
        TWO(2),
        THREE(3),
        FOUR(4),
        FIVE(5),
        SIX(6);

        private int rating;

        RatingConst(int rating) {
            this.rating = rating;
        }

        public int getRating(){
            return rating;
        }
    }


}
