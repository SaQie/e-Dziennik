package pl.edziennik.eDziennik.server.teacher;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.dto.teacher.TeacherRequestDto;
import pl.edziennik.eDziennik.server.repositories.RoleRepository;
import pl.edziennik.eDziennik.server.repositories.SchoolRepository;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
class TeacherPrivService {

    private final RoleRepository roleRepository;
    private final SchoolRepository schoolRepository;

    protected void checkRoleExist(TeacherRequestDto dto, Teacher teacher) {
        if (dto.getRole() != null){
            roleRepository.findByName(dto.getRole()).ifPresentOrElse(teacher::setRole, () -> {
                throw new EntityNotFoundException("Role with name " + dto.getRole() + " not exist");
            });
        }
    }

    protected void checkSchoolExist(TeacherRequestDto dto, Teacher teacher) {
        if (dto.getSchool() != null) {
            schoolRepository.findById(dto.getSchool()).ifPresentOrElse(school -> school.addTeacher(teacher), () -> {
                throw new EntityNotFoundException("school with given id " + dto.getSchool() + " not exist");
            });
        }
    }

}
