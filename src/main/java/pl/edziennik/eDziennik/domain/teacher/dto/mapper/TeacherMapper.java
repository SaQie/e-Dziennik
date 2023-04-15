package pl.edziennik.eDziennik.domain.teacher.dto.mapper;

import pl.edziennik.eDziennik.domain.address.dto.mapper.AddressMapper;
import pl.edziennik.eDziennik.domain.personinformation.dto.mapper.PersonInformationMapper;
import pl.edziennik.eDziennik.domain.school.dto.mapper.SchoolMapper;
import pl.edziennik.eDziennik.domain.teacher.domain.Teacher;
import pl.edziennik.eDziennik.domain.teacher.dto.TeacherRequestApiDto;
import pl.edziennik.eDziennik.domain.teacher.dto.TeacherResponseApiDto;
import pl.edziennik.eDziennik.domain.teacher.dto.TeacherSimpleResponseApiDto;

import java.util.List;


public class TeacherMapper {

    private TeacherMapper() {
    }

    public static TeacherResponseApiDto toDto(Teacher entity) {
        if (entity.getSchool() != null) {
            return TeacherResponseApiDto.builder()
                    .teacherId(entity.getTeacherId())
                    .userId(entity.getUser().getUserId())
                    .username(entity.getUser().getUsername())
                    .firstName(entity.getPersonInformation().firstName())
                    .lastName(entity.getPersonInformation().lastName())
                    .address(entity.getAddress().getAddress())
                    .postalCode(entity.getAddress().getPostalCode())
                    .city(entity.getAddress().getCity())
                    .fullName(entity.getPersonInformation().fullName())
                    .pesel(entity.getPersonInformation().pesel().value())
                    .phoneNumber(entity.getPersonInformation().phoneNumber().value())
                    .school(SchoolMapper.toSimpleDto(entity.getSchool()))
                    .email(entity.getUser().getEmail())
                    .role(entity.getUser().getRole().getName())
                    .build();
        }
        return TeacherResponseApiDto.builder()
                .teacherId(entity.getTeacherId())
                .userId(entity.getUser().getUserId())
                .username(entity.getUser().getUsername())
                .firstName(entity.getPersonInformation().firstName())
                .lastName(entity.getPersonInformation().lastName())
                .fullName(entity.getPersonInformation().fullName())
                .address(entity.getAddress().getAddress())
                .postalCode(entity.getAddress().getPostalCode())
                .email(entity.getUser().getEmail())
                .city(entity.getAddress().getCity())
                .pesel(entity.getPersonInformation().pesel().value())
                .phoneNumber(entity.getPersonInformation().phoneNumber().value())
                .role(entity.getUser().getRole().getName())
                .build();
    }

    public static List<TeacherResponseApiDto> toDto(List<Teacher> entities) {
        return entities.stream().map(TeacherMapper::toDto).toList();
    }

    public static Teacher toEntity(TeacherRequestApiDto dto) {
        return new Teacher(
                PersonInformationMapper.mapToPersonInformation(dto),
                AddressMapper.mapToAddress(dto)
        );
    }

    public static TeacherSimpleResponseApiDto toSimpleDto(Teacher teacher) {
        String fullName = teacher.getPersonInformation().firstName() + " " + teacher.getPersonInformation().lastName();
        return new TeacherSimpleResponseApiDto(teacher.getTeacherId(), fullName);
    }
}
