package pl.edziennik.eDziennik.server.basics.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.server.basics.dao.BaseDao;
import pl.edziennik.eDziennik.server.exceptions.EntityNotFoundException;
import pl.edziennik.eDziennik.domain.role.domain.Role;
import pl.edziennik.eDziennik.domain.school.domain.School;
import pl.edziennik.eDziennik.domain.schoolclass.domain.SchoolClass;
import pl.edziennik.eDziennik.domain.studensubject.domain.StudentSubject;
import pl.edziennik.eDziennik.domain.student.domain.Student;
import pl.edziennik.eDziennik.domain.subject.domain.Subject;
import pl.edziennik.eDziennik.domain.teacher.domain.Teacher;
import pl.edziennik.eDziennik.domain.user.domain.User;
import pl.edziennik.eDziennik.server.utils.PersistanceHelper;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import javax.persistence.TypedQuery;
import java.util.Optional;

/**
 * Basic validator (simple most used checks)
 */
@Service
public class BasicValidator extends BaseDao<User> {

    @Autowired
    private ResourceCreator resourceCreator;

    public void checkStudentExist(final Long idStudent){
        get(Student.class, idStudent);
    }

    public void checkSubjectExist(final Long idSubject){
        get(Subject.class, idSubject);
    }

    public void checkTeacherExist(final Long idTeacher){
        get(Teacher.class, idTeacher);
    }

    public void checkSchoolExist(final Long idSchool){
        get(School.class, idSchool);
    }

    public void checkSchoolClassExist(final Long idSchoolClass){
        get(SchoolClass.class, idSchoolClass);
    }

    public StudentSubject checkStudentSubjectExist(final Long idStudent, final Long idSubject){
        TypedQuery<StudentSubject> query = em.createNamedQuery("StudentSubject.findSubjectStudent", StudentSubject.class);
        query.setParameter("idStudent", idStudent);
        query.setParameter("idSubject", idSubject);
        StudentSubject studentSubject = (StudentSubject) PersistanceHelper.getSingleResultOrNull(query);
        if (studentSubject == null){
            Student student = get(Student.class, idStudent);
            String studentName = student.getUser().getPersonInformation().getFirstName() + " " + student.getUser().getPersonInformation().getLastName();
            String subjectName = get(Subject.class, idSubject).getName();
            String exceptionMessage = resourceCreator.of("student.subject.not.exist", studentName, subjectName);
            throw new EntityNotFoundException(exceptionMessage);
        }
        return studentSubject;
    }

    public Role checkRoleExistOrReturnDefault(String roleName){
        TypedQuery<Role> query = em.createNamedQuery("Role.getRoleByName", Role.class);
        query.setParameter("name", roleName);
        Optional<Role> role = PersistanceHelper.getSingleResultAsOptional(query);
        return role.orElse(get(Role.class,Role.RoleConst.ROLE_STUDENT.getId()));
    }



}
