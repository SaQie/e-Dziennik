package pl.edziennik.eDziennik.server.school.domain.dto;

import lombok.Builder;
import lombok.Getter;
import pl.edziennik.eDziennik.server.schoollevel.domain.dto.SchoolLevelResponseApiDto;

@Getter
@Builder
public class SchoolResponseApiDto{

    private final Long id;
    private final String name;
    private final String postalCode;
    private final String city;
    private final String nip;
    private final String regon;
    private final String address;
    private final String phoneNumber;
    private final SchoolLevelResponseApiDto schoolLevel;
}
