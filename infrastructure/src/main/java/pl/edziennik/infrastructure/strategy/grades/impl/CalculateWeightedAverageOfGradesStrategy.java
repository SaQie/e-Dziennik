package pl.edziennik.infrastructure.strategy.grades.impl;

import org.springframework.stereotype.Component;
import pl.edziennik.common.enums.AverageType;
import pl.edziennik.infrastructure.strategy.grades.CalculateAverageOfGradesStrategy;
import pl.edziennik.infrastructure.strategy.grades.CalculateAverageOfGradesStrategyInput;
import pl.edziennik.infrastructure.strategy.grades.CalculateAverageOfGradesStrategyOutput;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Strategy for calculating weighted average of grades
 */
@Component
public class CalculateWeightedAverageOfGradesStrategy implements CalculateAverageOfGradesStrategy {

    @Override
    public CalculateAverageOfGradesStrategyOutput calculateAverage(CalculateAverageOfGradesStrategyInput input) {
        double gradesSum = input.grades().stream()
                .mapToDouble(grade -> {
                    if (grade.getWeight().value() == 0) {
                        return grade.getGrade().decimalGrade;
                    }
                    return (grade.getGrade().decimalGrade * grade.getWeight().value());
                })
                .sum();

        int gradesCount = input.grades().size();

        double average = gradesSum / gradesCount;

        BigDecimal bigDecimal = new BigDecimal(average);
        bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);

        return new CalculateAverageOfGradesStrategyOutput(bigDecimal);
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean forAverageType(AverageType averageType) {
        return AverageType.WEIGHTED == averageType;
    }
}
