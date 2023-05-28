package pl.edziennik.application.query.file;

import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.query.IQuery;
import pl.edziennik.common.enums.DocumentType;
import pl.edziennik.common.valueobject.id.StudentId;

@HandledBy(handler = GetStudentAllSubjectsGradesSummaryForFileQueryHandler.class)
public record GetStudentAllSubjectsGradesSummaryForFileQuery(
        StudentId studentId,
        DocumentType documentType
) implements IQuery<byte[]> {

    public static final String STUDENT_ID = "studentId";
    public static final String DOCUMENT_TYPE = "documentType";

}
