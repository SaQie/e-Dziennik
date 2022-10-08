package pl.edziennik.eDziennik.server.school.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.edziennik.eDziennik.server.basics.AbstractDto;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SchoolRequestApiDto extends AbstractDto {

    private String name;
    private String adress;
    private String postalCode;
    private String city;
    private String nip;
    private String regon;
    private String phoneNumber;
    private Long schoolLevel;

}
