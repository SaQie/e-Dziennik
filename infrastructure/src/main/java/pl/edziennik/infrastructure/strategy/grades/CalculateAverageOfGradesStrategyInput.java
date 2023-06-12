package pl.edziennik.infrastructure.strategy.grades;

import pl.edziennik.domain.grade.Grade;

import java.util.List;

/**
 * Class used for strategy {@link CalculateAverageOfGradesStrategy} as input to calculate the student's subject grade's average
 */
public record CalculateAverageOfGradesStrategyInput(

        List<Grade> grades

) {

    public static final String GRADES = "grades";

}
