package pl.edziennik.eDziennik.server.teacher.domain.dto.mapper;

import pl.edziennik.eDziennik.server.role.domain.dto.mapper.RoleMapper;
import pl.edziennik.eDziennik.server.school.domain.dto.mapper.SchoolMapper;
import pl.edziennik.eDziennik.server.teacher.domain.Teacher;
import pl.edziennik.eDziennik.server.teacher.domain.dto.TeacherRequestApiDto;
import pl.edziennik.eDziennik.server.teacher.domain.dto.TeacherResponseApiDto;


public class TeacherMapper {

    private TeacherMapper() {
    }

    public static TeacherResponseApiDto toDto(Teacher entity) {
        if (entity.getSchool() != null) {
            return new TeacherResponseApiDto(
                    entity.getId(),
                    entity.getFirstName(),
                    entity.getLastName(),
                    entity.getAddress(),
                    entity.getPostalCode(),
                    entity.getCity(),
                    entity.getPESEL(),
                    entity.getPhoneNumber(),
                    entity.getRole().getId(),
                    entity.getSchool().getId()
            );
        }
        return new TeacherResponseApiDto(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getAddress(),
                entity.getPostalCode(),
                entity.getCity(),
                entity.getPESEL(),
                entity.getPhoneNumber(),
                entity.getRole().getId()
        );
    }

    public static Teacher toEntity(TeacherRequestApiDto dto) {
        return new Teacher(
                dto.getAddress(),
                dto.getUsername(),
                dto.getPassword(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getPostalCode(),
                dto.getPesel(),
                dto.getCity(),
                dto.getPhoneNumber()
        );
    }
}
