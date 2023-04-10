package pl.edziennik.eDziennik.domain.school.dto;

import lombok.Builder;
import pl.edziennik.eDziennik.domain.schoollevel.dto.SchoolLevelResponseApiDto;


@Builder
public record SchoolResponseApiDto(
        Long id,
        String name,
        String postalCode,
        String city,
        String nip,
        String regon,
        String address,
        String phoneNumber,
        SchoolLevelResponseApiDto schoolLevel
){


}
