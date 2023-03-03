package pl.edziennik.eDziennik.domain.school.dto.mapper;

import pl.edziennik.eDziennik.domain.address.dto.mapper.AddressMapper;
import pl.edziennik.eDziennik.domain.school.domain.School;
import pl.edziennik.eDziennik.domain.school.dto.SchoolSimpleResponseApiDto;
import pl.edziennik.eDziennik.domain.school.dto.SchoolRequestApiDto;
import pl.edziennik.eDziennik.domain.school.dto.SchoolResponseApiDto;
import pl.edziennik.eDziennik.domain.schoollevel.dto.mapper.SchoolLevelMapper;

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
                AddressMapper.mapToAddress(dto)
        );
    }

    public static SchoolSimpleResponseApiDto toSimpleDto(School entity) {
        return new SchoolSimpleResponseApiDto(entity.getId(), entity.getName());
    }
}
