package pl.edziennik.eDziennik.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.edziennik.eDziennik.entities.School;
import pl.edziennik.eDziennik.utils.AbstractDto;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SchoolDto extends AbstractDto {

    private Long id;

    private String name;
    private String adress;
    private String postalCode;
    private String city;
    private String nip;
    private String regon;
    private String phoneNumber;
    private SchoolLevelDto schoolLevel;

    public static SchoolDto dtoFrom(School school){
        SchoolDto dto = new SchoolDto();
        dto.name = school.getName();
        dto.adress = school.getAdress();
        dto.postalCode = school.getPostalCode();
        dto.city = school.getCity();
        dto.nip = school.getNip();
        dto.regon = school.getRegon();
        dto.phoneNumber = school.getPhoneNumber();
        dto.schoolLevel = SchoolLevelDto.dtoFrom(school.getSchoolLevel());
        dto.id = school.getId();
        return dto;
    }

    public static School dtoTo(SchoolDto dto){
        return School.builder()
                .name(dto.name)
                .adress(dto.adress)
                .city(dto.city)
                .postalCode(dto.postalCode)
                .phoneNumber(dto.phoneNumber)
                .nip(dto.nip)
                .regon(dto.regon)
                .build();

    }

}
