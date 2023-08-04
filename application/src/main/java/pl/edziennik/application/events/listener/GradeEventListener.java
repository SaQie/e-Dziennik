package pl.edziennik.application.events.listener;

import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import pl.edziennik.application.events.event.GradeAddedEvent;
import pl.edziennik.common.enums.AverageType;
import pl.edziennik.domain.grade.Grade;
import pl.edziennik.domain.studentsubject.StudentSubject;
import pl.edziennik.infrastructure.repository.studentsubject.StudentSubjectCommandRepository;
import pl.edziennik.infrastructure.spring.ResourceCreator;
import pl.edziennik.infrastructure.spring.exception.BusinessException;
import pl.edziennik.infrastructure.strategy.grades.CalculateAverageOfGradesStrategy;
import pl.edziennik.infrastructure.strategy.grades.CalculateAverageOfGradesStrategyInput;
import pl.edziennik.infrastructure.strategy.grades.CalculateAverageOfGradesStrategyOutput;

import java.math.BigDecimal;
import java.util.List;

@Component
@AllArgsConstructor
public class GradeEventListener {

    private final StudentSubjectCommandRepository studentSubjectCommandRepository;
    private final List<CalculateAverageOfGradesStrategy> averageOfGradesStrategies;
    private final ResourceCreator res;


    @Async
    @EventListener(classes = GradeAddedEvent.class)
    public void onGradeAddedEvent(GradeAddedEvent event) {
        StudentSubject studentSubject = studentSubjectCommandRepository.findById(event.studentSubjectId())
                .orElseThrow(() -> new BusinessException(
                        res.notFoundError(GradeAddedEvent.STUDENT_SUBJECT_ID, event.studentSubjectId())
                ));

        AverageType averageType = studentSubject.student().school().schoolConfiguration().averageType();

        List<Grade> grades = studentSubjectCommandRepository.getStudentSubjectGrades(event.studentSubjectId());

        BigDecimal average = calculateAverage(averageType, grades);

        studentSubject.changeAverage(average);
    }

    private BigDecimal calculateAverage(AverageType averageType, List<Grade> grades) {
        CalculateAverageOfGradesStrategy strategy = findStrategy(averageType);

        CalculateAverageOfGradesStrategyInput strategyInput = new CalculateAverageOfGradesStrategyInput(grades);
        CalculateAverageOfGradesStrategyOutput strategyOutput = strategy.calculateAverage(strategyInput);
        return strategyOutput.average();
    }

    private CalculateAverageOfGradesStrategy findStrategy(AverageType averageType) {
        return averageOfGradesStrategies.stream()
                .filter(CalculateAverageOfGradesStrategy::isEnabled)
                .filter(strategy -> strategy.forAverageType(averageType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Average of grades strategy not found"));
    }

}
