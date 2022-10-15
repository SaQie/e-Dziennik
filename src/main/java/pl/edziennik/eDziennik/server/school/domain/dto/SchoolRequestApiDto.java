package pl.edziennik.eDziennik.server.school.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SchoolRequestApiDto{

    private String name;
    private String adress;
    private String postalCode;
    private String city;
    private String nip;
    private String regon;
    private String phoneNumber;
    private Long schoolLevel;

}
