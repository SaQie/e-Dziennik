package pl.edziennik.eDziennik.server.teacher.domain.dto.mapper;

import pl.edziennik.eDziennik.server.address.AddressMapper;
import pl.edziennik.eDziennik.server.personinformation.PersonInformationMapper;
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
                    entity.getPersonInformation().getFirstName(),
                    entity.getPersonInformation().getLastName(),
                    entity.getAddress().getAddress(),
                    entity.getAddress().getPostalCode(),
                    entity.getAddress().getCity(),
                    entity.getPersonInformation().getPesel(),
                    entity.getPhoneNumber(),
                    entity.getRole().getId(),
                    entity.getSchool().getId()
            );
        }
        return new TeacherResponseApiDto(
                entity.getId(),
                entity.getPersonInformation().getFirstName(),
                entity.getPersonInformation().getLastName(),
                entity.getAddress().getAddress(),
                entity.getAddress().getPostalCode(),
                entity.getAddress().getCity(),
                entity.getPersonInformation().getPesel(),
                entity.getPhoneNumber(),
                entity.getRole().getId()
        );
    }

    public static Teacher toEntity(TeacherRequestApiDto dto) {
        return new Teacher(
                dto.getUsername(),
                dto.getPassword(),
                dto.getPhoneNumber(),
                PersonInformationMapper.mapToPersonInformation(dto.getFirstName(), dto.getLastName(), dto.getPesel()),
                AddressMapper.mapToAddress(dto.getAddress(), dto.getCity(), dto.getPostalCode())
        );
    }
}
