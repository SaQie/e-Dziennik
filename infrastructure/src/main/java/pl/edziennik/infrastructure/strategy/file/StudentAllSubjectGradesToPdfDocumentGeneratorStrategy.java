package pl.edziennik.infrastructure.strategy.file;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.common.dto.file.studentallsubjectsgrades.StudentAllSubjectsGradesHeaderForFileDto;
import pl.edziennik.common.dto.file.studentallsubjectsgrades.StudentAllSubjectsSummaryForFileDto;
import pl.edziennik.common.enums.DocumentType;
import pl.edziennik.infrastructure.spring.ResourceCreator;
import pl.edziennik.infrastructure.spring.file.PdfTableGenerator;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class StudentAllSubjectGradesToPdfDocumentGeneratorStrategy implements DocumentGeneratorStrategy<StudentAllSubjectsGradesHeaderForFileDto> {

    private final ResourceCreator res;

    @Override
    public byte[] generateDocument(StudentAllSubjectsGradesHeaderForFileDto dto) {
        PdfTableGenerator generator = new PdfTableGenerator();

        generator.setDocumentTitle(res.of("grades.summary", dto.studentFullName().toString()));
        generator.setColumns(res.of("column.subjects"), res.of("column.teacher"), res.of("column.grades"));

        generator.addHeader(res.of("school"), dto.schoolName().toString());
        generator.addHeader(res.of("school.class"), dto.schoolClassName().toString());
        generator.addHeader(res.of("classroom.teacher"), dto.teacherFullName().toString());

        for (StudentAllSubjectsSummaryForFileDto subject : dto.subjects()) {
            String grades = subject.grades().stream()
                    .map(grade -> grade.grade().grade)
                    .collect(Collectors.joining(", "));
            generator.setRows(subject.subjectName().toString(), subject.teacherFullName().toString(), grades);
        }

        return generator.generate();
    }

    @Override
    public boolean forType(DocumentType documentType) {
        return documentType.equals(DocumentType.PDF);
    }
}
