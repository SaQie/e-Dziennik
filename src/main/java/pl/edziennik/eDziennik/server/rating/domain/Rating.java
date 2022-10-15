package pl.edziennik.eDziennik.server.rating.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.edziennik.eDziennik.server.ratingsubjectstudent.domain.RatingSubjectStudentLink;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@NoArgsConstructor
@Getter
public class Rating{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated
    private RatingConst rating;
    private int weight;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private RatingSubjectStudentLink ratingSubjectStudentLink;

    public Rating(RatingConst rating, int weight, String description) {
        this.rating = rating;
        this.weight = weight;
        this.description = description;
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

        public static RatingConst getByRating(int rating){
            for (RatingConst ratingConst : RatingConst.values()){
                if (ratingConst.rating == rating){
                    return ratingConst;
                }
            }
            throw new EntityNotFoundException("Rating " + rating + " not found");
        }
    }


}
