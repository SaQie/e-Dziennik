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
class TeacherValidatorService extends ServiceValidator<TeacherValidators, TeacherRequestApiDto> {

    @Override
    protected void valid(TeacherRequestApiDto dto) {
        runValidatorChain(dto);
    }
}
