package pl.edziennik.eDziennik.domain.school.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import pl.edziennik.eDziennik.domain.schoollevel.domain.wrapper.SchoolLevelId;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public class SchoolRequestApiDto {
    @NotEmpty(message = "{name.empty}")
    private final String name;

    @NotEmpty(message = "{address.empty}")
    private final String address;

    @NotEmpty(message = "{postalCode.empty}")
    @Size(min = 6, max = 6, message = "{postalCode.size}")
    private final String postalCode;

    @NotEmpty(message = "{city.empty}")
    private final String city;

    @org.hibernate.validator.constraints.pl.NIP(message = "{nip.invalid}")
    private final String nip;

    @org.hibernate.validator.constraints.pl.REGON(message = "{regon.invalid}")
    private final String regon;

    @Pattern(regexp = "[\\d]{9}", message = "{phone.invalid}")
    private final String phoneNumber;

    @NotNull(message = "{schoolLevel.empty}")
    private final SchoolLevelId schoolLevelId;

    public static final String NAME = "name";
    public static final String ADDRESS = "address";
    public static final String POSTAL_CODE = "postalCode";
    public static final String CITY = "city";
    public static final String NIP = "nip";
    public static final String REGON = "regon";
    public static final String PHONE_NUMBER = "phoneNumber";
    public static final String ID_SCHOOL_LEVEL = "idSchoolLevel";


}
