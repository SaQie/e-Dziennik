package pl.edziennik.eDziennik.domain.studentsubject;

import pl.edziennik.eDziennik.domain.personinformation.domain.PersonInformation;
import pl.edziennik.eDziennik.domain.schoolclass.domain.SchoolClass;
import pl.edziennik.eDziennik.domain.student.domain.Student;
import pl.edziennik.eDziennik.domain.student.domain.wrapper.StudentId;
import pl.edziennik.eDziennik.domain.studentsubject.dto.request.StudentSubjectRequestDto;
import pl.edziennik.eDziennik.domain.subject.SubjectIntegrationTestUtil;
import pl.edziennik.eDziennik.domain.subject.domain.Subject;
import pl.edziennik.eDziennik.domain.subject.domain.wrapper.SubjectId;
import pl.edziennik.eDziennik.domain.subject.dto.SubjectRequestApiDto;
import pl.edziennik.eDziennik.domain.teacher.domain.wrapper.TeacherId;
import pl.edziennik.eDziennik.domain.user.domain.User;

/**
 * Util test class for {@link StudentSubjectIntegrationTest}
 */
public class StudentSubjectIntegrationTestUtil {

    private final SubjectIntegrationTestUtil subjectUtil;

    public StudentSubjectIntegrationTestUtil() {
        this.subjectUtil = new SubjectIntegrationTestUtil();
    }

    public SubjectRequestApiDto prepareSubject(TeacherId teacherId) {
        return subjectUtil.prepareSubjectRequestDto(teacherId);
    }

    public StudentSubjectRequestDto prepareStudentSubjectRequestDto(SubjectId subjectId, StudentId studentId) {
        return new StudentSubjectRequestDto(subjectId, studentId);
    }


    public Student prepareStudentWithSchoolClass(Long schoolClassId, String className) {
        SchoolClass schoolClass = new SchoolClass();
        User user = new User();
        PersonInformation personInformation = new PersonInformation();
        schoolClass.setClassName(className);
        schoolClass.setId(schoolClassId);
        Student student = new Student();
        student.setUser(user);
        student.setSchoolClass(schoolClass);
        student.setPersonInformation(personInformation);
        return student;
    }

    public Subject prepareSubjectWithSchoolClass(Long id, String className) {
        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setClassName(className);
        schoolClass.setId(id);
        Subject subject = new Subject();
        subject.setSchoolClass(schoolClass);
        return subject;
    }


}
