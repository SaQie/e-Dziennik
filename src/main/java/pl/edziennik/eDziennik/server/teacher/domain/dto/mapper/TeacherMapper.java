package pl.edziennik.eDziennik.server.teacher.domain.dto.mapper;

import pl.edziennik.eDziennik.server.address.AddressMapper;
import pl.edziennik.eDziennik.server.personinformation.PersonInformationMapper;
import pl.edziennik.eDziennik.server.school.domain.dto.mapper.SchoolMapper;
import pl.edziennik.eDziennik.server.teacher.domain.Teacher;
import pl.edziennik.eDziennik.server.teacher.domain.dto.TeacherRequestApiDto;
import pl.edziennik.eDziennik.server.teacher.domain.dto.TeacherResponseApiDto;
import pl.edziennik.eDziennik.server.teacher.domain.dto.TeacherSimpleResponseApiDto;


public class TeacherMapper {

    private TeacherMapper() {
    }

    public static TeacherResponseApiDto toDto(Teacher entity) {
        if (entity.getSchool() != null) {
            return TeacherResponseApiDto.builder()
                    .id(entity.getId())
                    .username(entity.getUser().getUsername())
                    .firstName(entity.getUser().getPersonInformation().getFirstName())
                    .lastName(entity.getUser().getPersonInformation().getLastName())
                    .address(entity.getUser().getAddress().getAddress())
                    .postalCode(entity.getUser().getAddress().getPostalCode())
                    .city(entity.getUser().getAddress().getCity())
                    .pesel(entity.getUser().getPersonInformation().getPesel())
                    .phoneNumber(entity.getPhoneNumber())
                    .school(SchoolMapper.toSimpleDto(entity.getSchool()))
                    .role(entity.getUser().getRole().getName())
                    .build();
        }
        return TeacherResponseApiDto.builder()
                .id(entity.getId())
                .username(entity.getUser().getUsername())
                .firstName(entity.getUser().getPersonInformation().getFirstName())
                .lastName(entity.getUser().getPersonInformation().getLastName())
                .address(entity.getUser().getAddress().getAddress())
                .postalCode(entity.getUser().getAddress().getPostalCode())
                .city(entity.getUser().getAddress().getCity())
                .pesel(entity.getUser().getPersonInformation().getPesel())
                .phoneNumber(entity.getPhoneNumber())
                .role(entity.getUser().getRole().getName())
                .build();
    }

    public static Teacher toEntity(TeacherRequestApiDto dto) {
        return new Teacher(
                dto.getPhoneNumber()
        );
    }

    public static TeacherSimpleResponseApiDto toSimpleDto(Teacher teacher) {
        String fullName = teacher.getUser().getPersonInformation().getFirstName() + " " + teacher.getUser().getPersonInformation().getLastName();
        return new TeacherSimpleResponseApiDto(teacher.getId(), fullName);
    }
}
