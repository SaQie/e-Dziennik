package pl.edziennik.infrastructure;

import org.junit.jupiter.api.Test;
import pl.edziennik.common.valueobject.vo.Weight;
import pl.edziennik.domain.grade.Grade;
import pl.edziennik.infrastructure.strategy.grades.CalculateAverageOfGradesStrategyInput;
import pl.edziennik.infrastructure.strategy.grades.CalculateAverageOfGradesStrategyOutput;
import pl.edziennik.infrastructure.strategy.grades.impl.CalculateArithmeticAverageOfGradesStrategy;
import pl.edziennik.infrastructure.strategy.grades.impl.CalculateWeightedAverageOfGradesStrategy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculateAverageOfGradesStrategyUnitTest {

    @Test
    public void shouldCalculateAverageOfGradesForArithmetic() {
        // given
        List<Grade> grades = generateGradesForArithmetic();

        CalculateArithmeticAverageOfGradesStrategy strategy = new CalculateArithmeticAverageOfGradesStrategy();

        // when
        CalculateAverageOfGradesStrategyOutput output = strategy.calculateAverage(
                new CalculateAverageOfGradesStrategyInput(grades));

        // then
        BigDecimal average = output.average();
        assertEquals(average, new BigDecimal("3.38"));

    }

    @Test
    public void shouldCalculateAverageOfGradesForWeighted() {
        // given
        List<Grade> grades = generateGradesForWeighted();

        CalculateWeightedAverageOfGradesStrategy strategy = new CalculateWeightedAverageOfGradesStrategy();

        // when
        CalculateAverageOfGradesStrategyOutput output = strategy.calculateAverage(
                new CalculateAverageOfGradesStrategyInput(grades));

        // then
        BigDecimal average = output.average();
        assertEquals(average, new BigDecimal("12.34"));
    }

    private List<Grade> generateGradesForArithmetic() {
        List<Grade> grades = new ArrayList<>();
        grades.add(getGrade(pl.edziennik.common.enums.Grade.ONE, 0));
        grades.add(getGrade(pl.edziennik.common.enums.Grade.TWO, 0));
        grades.add(getGrade(pl.edziennik.common.enums.Grade.THREE, 0));
        grades.add(getGrade(pl.edziennik.common.enums.Grade.THREE, 0));
        grades.add(getGrade(pl.edziennik.common.enums.Grade.FIVE_PLUS, 0));
        grades.add(getGrade(pl.edziennik.common.enums.Grade.FOUR, 0));
        grades.add(getGrade(pl.edziennik.common.enums.Grade.FIVE_MINUS, 0));
        grades.add(getGrade(pl.edziennik.common.enums.Grade.FOUR, 0));

        return grades;
    }

    private List<Grade> generateGradesForWeighted() {
        List<Grade> grades = new ArrayList<>();
        grades.add(getGrade(pl.edziennik.common.enums.Grade.ONE, 0));
        grades.add(getGrade(pl.edziennik.common.enums.Grade.TWO, 2));
        grades.add(getGrade(pl.edziennik.common.enums.Grade.THREE, 2));
        grades.add(getGrade(pl.edziennik.common.enums.Grade.THREE, 2));
        grades.add(getGrade(pl.edziennik.common.enums.Grade.FIVE_PLUS, 5));
        grades.add(getGrade(pl.edziennik.common.enums.Grade.FOUR, 1));
        grades.add(getGrade(pl.edziennik.common.enums.Grade.FIVE_MINUS, 10));
        grades.add(getGrade(pl.edziennik.common.enums.Grade.FOUR, 1));

        return grades;
    }

    private Grade getGrade(pl.edziennik.common.enums.Grade grade, int weight) {
        return Grade.of(
                grade, Weight.of(weight), null, null, null
        );
    }


}
