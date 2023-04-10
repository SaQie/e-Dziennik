package pl.edziennik.eDziennik.domain.school.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.pl.NIP;
import org.hibernate.validator.constraints.pl.REGON;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public record SchoolRequestApiDto(
        @NotEmpty(message = "{name.empty}")
        String name,

        @NotEmpty(message = "{address.empty}")
        String address,

        @NotEmpty(message = "{postalCode.empty}")
        @Size(min = 6, max = 6, message = "{postalCode.size}")
        String postalCode,

        @NotEmpty(message = "{city.empty}")
        String city,

        @org.hibernate.validator.constraints.pl.NIP(message = "{nip.invalid}")
        String nip,

        @org.hibernate.validator.constraints.pl.REGON(message = "{regon.invalid}")
        String regon,

        @Pattern(regexp = "[\\d]{9}", message = "{phone.invalid}")
        String phoneNumber,

        @NotNull(message = "{schoolLevel.empty}")
        Long idSchoolLevel
) {

    public static final String NAME = "name";
    public static final String ADDRESS = "address";
    public static final String POSTAL_CODE = "postalCode";
    public static final String CITY = "city";
    public static final String NIP = "nip";
    public static final String REGON = "regon";
    public static final String PHONE_NUMBER = "phoneNumber";
    public static final String ID_SCHOOL_LEVEL = "idSchoolLevel";


}
