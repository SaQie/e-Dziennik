package pl.edziennik.web.query.file;

import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edziennik.application.common.dispatcher.Dispatcher;
import pl.edziennik.application.query.file.GetStudentAllSubjectsGradesSummaryForFileQuery;
import pl.edziennik.common.enums.DocumentType;
import pl.edziennik.common.valueobject.id.StudentId;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/api/v1/files")
@AllArgsConstructor
public class FileQueryController {

    private final Dispatcher dispatcher;

    @GetMapping("/grades/{studentId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<InputStreamResource> getStudentAllSubjectsGradesSummaryFile(@PathVariable StudentId studentId, @RequestParam String type) {
        DocumentType documentType = DocumentType.get(type);

        GetStudentAllSubjectsGradesSummaryForFileQuery query = new GetStudentAllSubjectsGradesSummaryForFileQuery(studentId, documentType);

        byte[] documentBytes = dispatcher.dispatch(query);

        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(documentBytes));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=student_subject_grades.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(documentBytes.length)
                .body(resource);
    }

}
