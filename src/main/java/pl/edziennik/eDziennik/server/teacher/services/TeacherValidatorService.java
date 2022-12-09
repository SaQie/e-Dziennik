package pl.edziennik.eDziennik.server.teacher.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.server.basics.ServiceValidator;
import pl.edziennik.eDziennik.server.role.dao.RoleDao;
import pl.edziennik.eDziennik.server.role.domain.Role;
import pl.edziennik.eDziennik.server.school.domain.School;
import pl.edziennik.eDziennik.server.teacher.domain.Teacher;
import pl.edziennik.eDziennik.server.teacher.domain.dto.TeacherRequestApiDto;
import pl.edziennik.eDziennik.server.teacher.domain.dto.mapper.TeacherMapper;
import pl.edziennik.eDziennik.server.teacher.services.validator.TeacherValidators;

@Service
@AllArgsConstructor
public class TeacherValidatorService extends ServiceValidator<TeacherValidators, TeacherRequestApiDto> {

    private final RoleDao dao;

    protected Teacher validateDtoAndMapToEntity(TeacherRequestApiDto dto){
        super.validate(dto);
        Teacher teacher = TeacherMapper.toEntity(dto);
        School school = null;
        if (dto.getIdSchool() != null){
            school = dao.get(School.class, dto.getIdSchool());
        }
        Role role = checkRoleExist(dto.getRole());
        teacher.setRole(role);
        teacher.setSchool(school);
        return teacher;
    }

    protected void validateDto(TeacherRequestApiDto dto){
        super.validate(dto);
    }


    private Role checkRoleExist(String role) {
        return dao.findByName(role)
                .orElse(dao.get(Role.class,Role.RoleConst.ROLE_TEACHER.getId()));
    }
}
