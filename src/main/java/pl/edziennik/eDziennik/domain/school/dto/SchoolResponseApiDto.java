package pl.edziennik.eDziennik.domain.school.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Builder;
import pl.edziennik.eDziennik.domain.school.domain.wrapper.SchoolId;
import pl.edziennik.eDziennik.domain.schoollevel.dto.SchoolLevelResponseApiDto;


@Builder
public record SchoolResponseApiDto(
        @JsonUnwrapped
        SchoolId schoolId,
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
