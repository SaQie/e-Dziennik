package pl.edziennik.eDziennik.domain.parent.domain.dto.mapper;

import pl.edziennik.eDziennik.domain.address.dto.mapper.AddressMapper;
import pl.edziennik.eDziennik.domain.parent.domain.Parent;
import pl.edziennik.eDziennik.domain.parent.domain.dto.ParentRequestApiDto;
import pl.edziennik.eDziennik.domain.parent.domain.dto.ParentResponseApiDto;
import pl.edziennik.eDziennik.domain.parent.domain.dto.ParentSimpleResponseApiDto;
import pl.edziennik.eDziennik.domain.personinformation.dto.mapper.PersonInformationMapper;
import pl.edziennik.eDziennik.domain.role.domain.Role;
import pl.edziennik.eDziennik.domain.student.dto.mapper.StudentMapper;

import java.util.List;

public class ParentMapper {

    private ParentMapper() {

    }

    public static Parent toEntity(ParentRequestApiDto dto) {
        return new Parent(
                PersonInformationMapper.mapToPersonInformation(dto),
                AddressMapper.mapToAddress(dto)
        );
    }

    public static ParentResponseApiDto toDto(Parent entity) {
        return ParentResponseApiDto.builder()
                .parentId(entity.getParentId())
                .pesel(entity.getPersonInformation().pesel().value())
                .email(entity.getUser().getEmail())
                .address(entity.getAddress().getAddress())
                .city(entity.getAddress().getCity())
                .username(entity.getUser().getUsername())
                .firstName(entity.getPersonInformation().firstName())
                .lastName(entity.getPersonInformation().lastName())
                .phoneNumber(entity.getPersonInformation().phoneNumber().value())
                .pesel(entity.getPersonInformation().pesel().value())
                .postalCode(entity.getAddress().getPostalCode())
                .fullName(entity.getPersonInformation().fullName())
                .student(StudentMapper.toSimpleDto(entity.getStudent()))
                .role(Role.RoleConst.ROLE_PARENT.name())
                .build();
    }

    public static List<ParentResponseApiDto> toDto(List<Parent> entities) {
        return entities.stream().map(ParentMapper::toDto).toList();
    }

    public static ParentSimpleResponseApiDto toSimpleDto(Parent parent) {
        if (parent != null) {
            return new ParentSimpleResponseApiDto(parent.getParentId(), parent.getPersonInformation().fullName());
        }
        return null;
    }

}
