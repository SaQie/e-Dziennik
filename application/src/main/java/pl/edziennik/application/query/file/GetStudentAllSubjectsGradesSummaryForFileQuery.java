package pl.edziennik.application.query.file;

import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.query.IQuery;
import pl.edziennik.common.enums.DocumentType;
import pl.edziennik.common.valueobject.id.StudentId;

/**
 * A query used for getting all subjects with grades student's data prepared for file data type
 * <br>
 * Currently used for the PDF File
 */
@HandledBy(handler = GetStudentAllSubjectsGradesSummaryForFileQueryHandler.class)
public record GetStudentAllSubjectsGradesSummaryForFileQuery(
        StudentId studentId,
        DocumentType documentType
) implements IQuery<byte[]> {

    public static final String STUDENT_ID = "studentId";
    public static final String DOCUMENT_TYPE = "documentType";

}
