package pl.edziennik.eDziennik.server.teacher.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.exceptions.EntityNotFoundException;
import pl.edziennik.eDziennik.server.role.dao.RoleDao;
import pl.edziennik.eDziennik.server.role.domain.Role;
import pl.edziennik.eDziennik.server.school.dao.SchoolDao;
import pl.edziennik.eDziennik.server.school.domain.School;
import pl.edziennik.eDziennik.server.teacher.domain.Teacher;

import java.util.Optional;

@Service
@AllArgsConstructor
class TeacherPrivService {

    private final RoleDao dao;

    protected void checkRoleExist(String role, Teacher teacher) {
        if (role != null){
            dao.findByName(role).ifPresentOrElse(teacher::setRole, () -> {
                throw new EntityNotFoundException("Role with name " + role + " not exist");
            });
        }
        teacher.setRole(dao.get(Role.class,Role.RoleConst.ROLE_TEACHER.getId()));
    }

    protected void checkSchoolExist(Long idSchool, Teacher teacher) {
        if (idSchool != null) {
            dao.findWithExecute(School.class,idSchool, school -> school.addTeacher(teacher));
        }
    }

}
