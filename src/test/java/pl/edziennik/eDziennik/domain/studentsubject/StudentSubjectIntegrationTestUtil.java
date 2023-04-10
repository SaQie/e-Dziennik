package pl.edziennik.eDziennik.domain.studentsubject;

import pl.edziennik.eDziennik.domain.personinformation.domain.PersonInformation;
import pl.edziennik.eDziennik.domain.schoolclass.domain.SchoolClass;
import pl.edziennik.eDziennik.domain.student.domain.Student;
import pl.edziennik.eDziennik.domain.studentsubject.dto.request.StudentSubjectRequestDto;
import pl.edziennik.eDziennik.domain.subject.domain.Subject;
import pl.edziennik.eDziennik.domain.subject.dto.SubjectRequestApiDto;
import pl.edziennik.eDziennik.domain.subject.SubjectIntegrationTestUtil;
import pl.edziennik.eDziennik.domain.user.domain.User;

/**
 * Util test class for {@link StudentSubjectIntegrationTest}
 */
public class StudentSubjectIntegrationTestUtil {

    private final SubjectIntegrationTestUtil subjectUtil;

    public StudentSubjectIntegrationTestUtil() {
        this.subjectUtil = new SubjectIntegrationTestUtil();
    }

    public SubjectRequestApiDto prepareSubject(Long idTeacher) {
        return subjectUtil.prepareSubjectRequestDto(idTeacher);
    }

    public StudentSubjectRequestDto prepareStudentSubjectRequestDto(Long idSubject, Long idStudent) {
        return new StudentSubjectRequestDto(idSubject, idStudent);
    }


    public Student prepareStudentWithSchoolClass(String className) {
        SchoolClass schoolClass = new SchoolClass();
        User user = new User();
        PersonInformation personInformation = new PersonInformation();
        schoolClass.setClassName(className);
        Student student = new Student();
        student.setUser(user);
        student.setSchoolClass(schoolClass);
        student.setPersonInformation(personInformation);
        return student;
    }

    public Subject prepareSubjectWithSchoolClass(String className) {
        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setClassName(className);
        Subject subject = new Subject();
        subject.setSchoolClass(schoolClass);
        return subject;
    }


}
