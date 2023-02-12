package pl.edziennik.eDziennik.studentsubject;

import pl.edziennik.eDziennik.domain.studensubject.dto.request.StudentSubjectRequestDto;
import pl.edziennik.eDziennik.domain.subject.dto.SubjectRequestApiDto;
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

    public StudentSubjectRequestDto prepareStudentSubjectRequestDto(Long idSubject, Long idStudent){
        return new StudentSubjectRequestDto(idSubject, idStudent);
    }


}
