package pl.edziennik.eDziennik.server.school.domain.dto;

import lombok.Getter;
import pl.edziennik.eDziennik.server.schoollevel.domain.dto.SchoolLevelResponseApiDto;

@Getter
public class SchoolResponseApiDto{

    private final Long id;
    private final String name;
    private final String postalCode;
    private final String city;
    private final String nip;
    private final String regon;
    private final String phoneNumber;
    private final Long idSchoolLevel;

    public SchoolResponseApiDto(Long id, String name, String postalCode, String city, String nip, String regon, String phoneNumber, Long idSchoolLevel) {
        this.id = id;
        this.name = name;
        this.postalCode = postalCode;
        this.city = city;
        this.nip = nip;
        this.regon = regon;
        this.phoneNumber = phoneNumber;
        this.idSchoolLevel = idSchoolLevel;
    }
}
