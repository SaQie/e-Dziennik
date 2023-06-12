package pl.edziennik.infrastructure.strategy.grades;

import java.math.BigDecimal;

/**
 * Class used for strategy {@link CalculateAverageOfGradesStrategy} as a result of the calculated average
 */
public record CalculateAverageOfGradesStrategyOutput(

        BigDecimal average

) {

    public static final String AVERAGE = "average";

}
