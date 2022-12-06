package pl.edziennik.eDziennik.server.school.domain.dto.mapper;

import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.server.address.AddressMapper;
import pl.edziennik.eDziennik.server.school.domain.School;
import pl.edziennik.eDziennik.server.school.domain.dto.SchoolRequestApiDto;
import pl.edziennik.eDziennik.server.school.domain.dto.SchoolResponseApiDto;
import pl.edziennik.eDziennik.server.schoollevel.domain.dto.mapper.SchoolLevelMapper;

public class SchoolMapper {

    private SchoolMapper() {
    }

    public static SchoolResponseApiDto toDto(School entity) {
        return new SchoolResponseApiDto(
                entity.getId(),
                entity.getName(),
                entity.getAddress().getPostalCode(),
                entity.getAddress().getCity(),
                entity.getNip(),
                entity.getRegon(),
                entity.getAddress().getAddress(),
                entity.getPhoneNumber(),
                entity.getSchoolLevel().getId()
        );
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

}
