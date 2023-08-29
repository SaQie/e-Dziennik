package pl.edziennik.web.query.file;

import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edziennik.application.query.file.FileQueryDao;
import pl.edziennik.common.enums.DocumentType;
import pl.edziennik.common.valueobject.id.StudentId;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/api/v1/files")
@AllArgsConstructor
public class FileQueryController {

    private final FileQueryDao dao;

    @GetMapping("/grades/{studentId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<InputStreamResource> getStudentAllSubjectsGradesSummaryFile(@PathVariable StudentId studentId, @RequestParam String type) {
        DocumentType documentType = DocumentType.get(type);

        byte[] bytes = dao.getStudentAllSubjectGradesFile(studentId, documentType);

        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(bytes));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=student_subject_grades.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(bytes.length)
                .body(resource);
    }

}
