package pl.edziennik.application.query.file;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.query.IQueryHandler;
import pl.edziennik.common.dto.file.studentallsubjectsgrades.DetailedGradeForFileDto;
import pl.edziennik.common.dto.file.studentallsubjectsgrades.StudentAllSubjectsGradesHeaderForFileDto;
import pl.edziennik.common.dto.file.studentallsubjectsgrades.StudentAllSubjectsSummaryForFileDto;
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
        StudentAllSubjectsGradesHeaderForFileDto header = gradeQueryRepository.getStudentAllSubjectGradesHeaderForFileDto(command.studentId());

        List<StudentAllSubjectsSummaryForFileDto> subjects = gradeQueryRepository.getStudentAllSubjectsSummaryForFileDto(command.studentId());

        List<DetailedGradeForFileDto> grades = gradeQueryRepository.getDetailedGradeForFileDto(command.studentId());

        subjects = connectSubjectListWithTheirGrades(subjects, grades);

        DocumentGeneratorStrategy generatorStrategy = generatorStrategies.stream()
                .filter(strategy -> strategy.forType(command.documentType()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("File generation strategy not found"));

        StudentAllSubjectsGradesHeaderForFileDto dto = new StudentAllSubjectsGradesHeaderForFileDto(header, subjects);

        return generatorStrategy.generateDocument(dto);
    }

    /**
     * Method for connect list of subjects with their grades (every subject has many grades)
     */
    private static List<StudentAllSubjectsSummaryForFileDto> connectSubjectListWithTheirGrades(List<StudentAllSubjectsSummaryForFileDto> subjects, List<DetailedGradeForFileDto> grades) {
        return subjects.stream()
                .map(subject -> new StudentAllSubjectsSummaryForFileDto(subject, grades.stream()
                        .filter(grade -> grade.studentSubjectId().equals(subject.studentSubjectId()))
                        .toList()))
                .toList();
    }
}
