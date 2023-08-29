package pl.edziennik.application.query.file;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.IQueryHandler;
import pl.edziennik.common.view.file.studentallsubjectsgrades.DetailedGradeForFileView;
import pl.edziennik.common.view.file.studentallsubjectsgrades.StudentAllSubjectsGradesHeaderForFileView;
import pl.edziennik.common.view.file.studentallsubjectsgrades.StudentAllSubjectsSummaryForFileView;
import pl.edziennik.infrastructure.repository.grade.GradeQueryRepository;
import pl.edziennik.infrastructure.strategy.file.DocumentGeneratorStrategy;

import java.util.List;

@Component
@AllArgsConstructor
class GetStudentAllSubjectsGradesSummaryForFileQueryHandler
        implements IQueryHandler<GetStudentAllSubjectsGradesSummaryForFileQuery, byte[]> {

    private final List<DocumentGeneratorStrategy> generatorStrategies;

    private final GradeQueryRepository gradeQueryRepository;

    @Override
    public byte[] handle(GetStudentAllSubjectsGradesSummaryForFileQuery command) {
        StudentAllSubjectsGradesHeaderForFileView header = gradeQueryRepository.getStudentAllSubjectGradesHeaderForFileView(command.studentId());

        List<StudentAllSubjectsSummaryForFileView> subjects = gradeQueryRepository.getStudentAllSubjectsSummaryForFileView(command.studentId());

        List<DetailedGradeForFileView> grades = gradeQueryRepository.getDetailedGradeForFileView(command.studentId());

        subjects = connectSubjectListWithTheirGrades(subjects, grades);

        DocumentGeneratorStrategy generatorStrategy = generatorStrategies.stream()
                .filter(strategy -> strategy.forType(command.documentType()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("File generation strategy not found"));

        StudentAllSubjectsGradesHeaderForFileView dto = new StudentAllSubjectsGradesHeaderForFileView(header, subjects);

        return generatorStrategy.generateDocument(dto);
    }

    /**
     * Method for connect list of subjects with their grades (every subject has many grades)
     */
    private static List<StudentAllSubjectsSummaryForFileView> connectSubjectListWithTheirGrades(List<StudentAllSubjectsSummaryForFileView> subjects, List<DetailedGradeForFileView> grades) {
        return subjects.stream()
                .map(subject -> new StudentAllSubjectsSummaryForFileView(subject, grades.stream()
                        .filter(grade -> grade.studentSubjectId().equals(subject.studentSubjectId()))
                        .toList()))
                .toList();
    }
}
