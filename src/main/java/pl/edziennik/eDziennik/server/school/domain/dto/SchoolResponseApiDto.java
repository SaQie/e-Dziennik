package pl.edziennik.eDziennik.server.school.domain.dto;

import lombok.Getter;
import pl.edziennik.eDziennik.server.schoollevel.domain.dto.SchoolLevelResponseApiDto;

@Getter
public class SchoolResponseApiDto{

    private Long id;
    private String name;
    private String postalCode;
    private String city;
    private String nip;
    private String regon;
    private String phoneNumber;
    private SchoolLevelResponseApiDto schoolLevel;

    public SchoolResponseApiDto(Long id, String name, String postalCode, String city, String nip, String regon, String phoneNumber, SchoolLevelResponseApiDto schoolLevelResponseApiDto) {
        this.id = id;
        this.name = name;
        this.postalCode = postalCode;
        this.city = city;
        this.nip = nip;
        this.regon = regon;
        this.phoneNumber = phoneNumber;
        this.schoolLevel = schoolLevelResponseApiDto;
    }
}
