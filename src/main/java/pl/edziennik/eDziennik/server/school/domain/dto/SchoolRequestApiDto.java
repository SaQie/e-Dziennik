package pl.edziennik.eDziennik.server.school.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SchoolRequestApiDto{

    public static final String NAME = "name";
    public static final String ADDRESS = "address";
    public static final String POSTAL_CODE = "postalCode";
    public static final String CITY = "city";
    public static final String NIP = "nip";
    public static final String REGON = "regon";
    public static final String PHONE_NUMBER = "phoneNumber";
    public static final String ID_SCHOOL_LEVEL = "idSchoolLevel";

    private String name;
    private String address;
    private String postalCode;
    private String city;
    private String nip;
    private String regon;
    private String phoneNumber;
    private Long idSchoolLevel;

}
