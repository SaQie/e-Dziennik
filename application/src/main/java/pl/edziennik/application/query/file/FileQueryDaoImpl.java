package pl.edziennik.application.query.file;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.edziennik.common.enums.DocumentType;
import pl.edziennik.common.valueobject.id.StudentId;
import pl.edziennik.common.view.file.studentallsubjectsgrades.DetailedGradeForFileView;
import pl.edziennik.common.view.file.studentallsubjectsgrades.StudentAllSubjectsGradesHeaderForFileView;
import pl.edziennik.common.view.file.studentallsubjectsgrades.StudentAllSubjectsSummaryForFileView;
import pl.edziennik.infrastructure.repository.grade.GradeQueryRepository;
import pl.edziennik.infrastructure.strategy.file.DocumentGeneratorStrategy;

import java.util.List;

@Repository
@AllArgsConstructor
class FileQueryDaoImpl implements FileQueryDao {

    private final List<DocumentGeneratorStrategy> generatorStrategies;
    private final GradeQueryRepository gradeQueryRepository;


    @Override
    public byte[] getStudentAllSubjectGradesFile(StudentId studentId, DocumentType documentType) {
        StudentAllSubjectsGradesHeaderForFileView header = gradeQueryRepository.getStudentAllSubjectGradesHeaderForFileView(studentId);

        List<StudentAllSubjectsSummaryForFileView> subjects = gradeQueryRepository.getStudentAllSubjectsSummaryForFileView(studentId);

        List<DetailedGradeForFileView> grades = gradeQueryRepository.getDetailedGradeForFileView(studentId);

        subjects = connectSubjectListWithTheirGrades(subjects, grades);

        DocumentGeneratorStrategy generatorStrategy = generatorStrategies.stream()
                .filter(strategy -> strategy.forType(documentType))
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
