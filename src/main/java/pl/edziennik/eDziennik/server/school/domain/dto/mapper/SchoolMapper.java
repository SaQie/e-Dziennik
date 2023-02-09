package pl.edziennik.eDziennik.server.school.domain.dto.mapper;

import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.server.address.AddressMapper;
import pl.edziennik.eDziennik.server.school.domain.School;
import pl.edziennik.eDziennik.server.school.domain.dto.SchoolRequestApiDto;
import pl.edziennik.eDziennik.server.school.domain.dto.SchoolResponseApiDto;
import pl.edziennik.eDziennik.server.school.domain.dto.SchoolSimpleResponseApiDto;
import pl.edziennik.eDziennik.server.schoollevel.domain.dto.mapper.SchoolLevelMapper;

import java.util.List;

public class SchoolMapper {

    private SchoolMapper() {
    }

    public static SchoolResponseApiDto toDto(School entity) {
        return SchoolResponseApiDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .postalCode(entity.getAddress().getPostalCode())
                .city(entity.getAddress().getCity())
                .nip(entity.getNip())
                .regon(entity.getRegon())
                .address(entity.getAddress().getAddress())
                .phoneNumber(entity.getPhoneNumber())
                .schoolLevel(SchoolLevelMapper.toDto(entity.getSchoolLevel()))
                .build();
    }

    public static List<SchoolResponseApiDto> toDto(List<School> entities){
        return entities.stream().map(SchoolMapper::toDto).toList();
    }

    public static School toEntity(SchoolRequestApiDto dto) {
        return new School(
                dto.getName(),
                dto.getNip(),
                dto.getRegon(),
                dto.getPhoneNumber(),
                AddressMapper.mapToAddress(dto.getAddress(),dto.getCity(),dto.getPostalCode())
        );
    }

    public static SchoolSimpleResponseApiDto toSimpleDto(School entity) {
        return new SchoolSimpleResponseApiDto(entity.getId(), entity.getName());
    }
}
