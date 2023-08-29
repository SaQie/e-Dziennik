package pl.edziennik.application.query.file;

import pl.edziennik.common.enums.DocumentType;
import pl.edziennik.common.valueobject.id.StudentId;

public interface FileQueryDao {

    byte[] getStudentAllSubjectGradesFile(StudentId studentId, DocumentType documentType);

}
