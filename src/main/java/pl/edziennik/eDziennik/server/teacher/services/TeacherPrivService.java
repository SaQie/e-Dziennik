package pl.edziennik.eDziennik.server.teacher.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.exceptions.EntityNotFoundException;
import pl.edziennik.eDziennik.server.basics.ServiceValidator;
import pl.edziennik.eDziennik.server.role.dao.RoleDao;
import pl.edziennik.eDziennik.server.role.domain.Role;
import pl.edziennik.eDziennik.server.school.dao.SchoolDao;
import pl.edziennik.eDziennik.server.school.domain.School;
import pl.edziennik.eDziennik.server.teacher.domain.Teacher;
import pl.edziennik.eDziennik.server.teacher.domain.dto.TeacherRequestApiDto;
import pl.edziennik.eDziennik.server.teacher.domain.dto.mapper.TeacherMapper;
import pl.edziennik.eDziennik.server.teacher.services.validator.TeacherValidator;

import java.util.Optional;

@Service
@AllArgsConstructor
class TeacherPrivService extends ServiceValidator<TeacherValidator<TeacherRequestApiDto>, TeacherRequestApiDto> {

    private final RoleDao dao;

    protected Teacher validateDtoAndMapToEntity(TeacherRequestApiDto dto){
        super.validate(dto);
        Teacher teacher = TeacherMapper.toEntity(dto);
        checkSchoolExistAndAssignIfExist(dto.getIdSchool(), teacher);
        checkRoleExistAndAssignIfExist(dto.getRole(), teacher);
        return teacher;
    }

    protected void validateDto(TeacherRequestApiDto dto){
        super.validate(dto);
    }


    private void checkRoleExistAndAssignIfExist(String role, Teacher teacher) {
        if (role != null){
            dao.findByName(role).ifPresentOrElse(teacher::setRole, () -> {
                throw new EntityNotFoundException("Role with name " + role + " not exist");
            });
        }
        teacher.setRole(dao.get(Role.class,Role.RoleConst.ROLE_TEACHER.getId()));
    }

    private void checkSchoolExistAndAssignIfExist(Long idSchool, Teacher teacher) {
        if (idSchool != null) {
            dao.findWithExecute(School.class,idSchool, school -> school.addTeacher(teacher));
        }
    }
}
