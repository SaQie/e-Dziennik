package pl.edziennik.eDziennik.server.teacher;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.server.role.RoleRepository;
import pl.edziennik.eDziennik.server.school.SchoolDao;
import pl.edziennik.eDziennik.server.teacher.domain.Teacher;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
class TeacherPrivService {

    private final RoleRepository roleRepository;
    private final SchoolDao dao;

    protected void checkRoleExist(String role, Teacher teacher) {
        if (role != null){
            roleRepository.findByName(role).ifPresentOrElse(teacher::setRole, () -> {
                throw new EntityNotFoundException("Role with name " + role + " not exist");
            });
        }
    }

    protected void checkSchoolExist(Long idSchool, Teacher teacher) {
        if (idSchool != null) {
            dao.find(idSchool).ifPresentOrElse(school -> school.addTeacher(teacher), () -> {
                throw new EntityNotFoundException("school with given id " + idSchool + " not exist");
            });
        }
    }

}
