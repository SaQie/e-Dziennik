package pl.edziennik.infrastructure.strategy.grades;

import pl.edziennik.common.enums.AverageType;

public interface CalculateAverageOfGradesStrategy {

    /**
     * Calculate the student's subject grade's average
     */
    CalculateAverageOfGradesStrategyOutput calculateAverage(CalculateAverageOfGradesStrategyInput input);

    boolean isEnabled();

    boolean forAverageType(AverageType averageType);

}
