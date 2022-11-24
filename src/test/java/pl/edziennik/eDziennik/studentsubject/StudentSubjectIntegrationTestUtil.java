package pl.edziennik.eDziennik.studentsubject;

import pl.edziennik.eDziennik.server.studensubject.domain.dto.request.StudentSubjectRequestDto;
import pl.edziennik.eDziennik.server.subject.domain.dto.SubjectRequestApiDto;
import pl.edziennik.eDziennik.subject.SubjectIntegrationTestUtil;

/**
 * Util test class for {@link StudentSubjectIntegrationTest}
 */
public class StudentSubjectIntegrationTestUtil {

    private SubjectIntegrationTestUtil subjectUtil;

    public StudentSubjectIntegrationTestUtil() {
        this.subjectUtil = new SubjectIntegrationTestUtil();
    }

    public SubjectRequestApiDto prepareSubject(Long idTeacher){
        return subjectUtil.prepareSubjectRequestDto(idTeacher);
    }

    public StudentSubjectRequestDto prepareStudentSubjectRequestDto(Long idSubject){
        return new StudentSubjectRequestDto(idSubject);
    }


}
