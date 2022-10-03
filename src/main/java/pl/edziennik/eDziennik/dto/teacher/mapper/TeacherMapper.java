package pl.edziennik.eDziennik.dto.teacher.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.dto.role.RoleMapper;
import pl.edziennik.eDziennik.dto.school.mapper.SchoolMapper;
import pl.edziennik.eDziennik.dto.teacher.TeacherRequestDto;
import pl.edziennik.eDziennik.dto.teacher.TeacherResponseApiDto;
import pl.edziennik.eDziennik.server.teacher.Teacher;
import pl.edziennik.eDziennik.server.basics.AbstractMapper;

@AllArgsConstructor
@Component
public class TeacherMapper implements AbstractMapper<TeacherResponseApiDto, Teacher> {

    private final SchoolMapper schoolMapper;
    private final RoleMapper roleMapper;

    @Override
    public TeacherResponseApiDto toDto(Teacher entity) {
        if (entity.getSchool() != null) {
            return new TeacherResponseApiDto(
                    entity.getId(),
                    entity.getFirstName(),
                    entity.getLastName(),
                    entity.getAdress(),
                    entity.getPostalCode(),
                    entity.getCity(),
                    entity.getPESEL(),
                    entity.getPhoneNumber(),
                    roleMapper.toDto(entity.getRole()),
                    schoolMapper.toDto(entity.getSchool())
            );
        }
        return new TeacherResponseApiDto(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getAdress(),
                entity.getPostalCode(),
                entity.getCity(),
                entity.getPESEL(),
                entity.getPhoneNumber(),
                roleMapper.toDto(entity.getRole())
        );
    }

    @Override
    public Teacher toEntity(TeacherResponseApiDto dto) {
        return null;
    }

    public Teacher toEntity(TeacherRequestDto dto){
        return new Teacher(
            dto.getAdress(),
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
