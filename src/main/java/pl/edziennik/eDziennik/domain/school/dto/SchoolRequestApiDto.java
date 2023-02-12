package pl.edziennik.eDziennik.domain.school.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.pl.NIP;
import org.hibernate.validator.constraints.pl.REGON;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

    @NotEmpty(message = "{name.empty}")
    private String name;

    @NotEmpty(message = "{address.empty}")
    private String address;

    @NotEmpty(message = "{postalCode.empty}")
    @Size(min = 6, max = 6, message = "{postalCode.size}")
    private String postalCode;

    @NotEmpty(message = "{city.empty}")
    private String city;

    @org.hibernate.validator.constraints.pl.NIP(message = "{nip.invalid}")
    private String nip;

    @org.hibernate.validator.constraints.pl.REGON(message = "{regon.invalid}")
    private String regon;

    @NotEmpty(message = "{phoneNumber.empty}")
    private String phoneNumber;

    @NotNull(message = "{schoolLevel.empty}")
    private Long idSchoolLevel;

}
