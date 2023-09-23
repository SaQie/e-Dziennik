package pl.edziennik.application.command.school.create;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.Command;
import pl.edziennik.application.common.dispatcher.Handler;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.id.SchoolLevelId;
import pl.edziennik.common.valueobject.vo.*;

/**
 * A command used for creating a new school
 */
@Handler(handler = CreateSchoolCommandHandler.class, validator = CreateSchoolCommandValidator.class)
public record CreateSchoolCommand(

        @JsonIgnore SchoolId schoolId,

        @Schema(example = "University")
        @Valid @NotNull(message = "{name.empty}") Name name,

        @Schema(example = "Ogrodowa 2/2")
        @Valid @NotNull(message = "{address.empty}") Address address,

        @Schema(example = "22-222")
        @Valid @NotNull(message = "{postalCode.empty}") PostalCode postalCode,

        @Schema(example = "Warszawa")
        @Valid @NotNull(message = "{city.empty}") City city,

        @Schema(example = "8263989886")
        @Valid @NotNull(message = "{nip.invalid}") Nip nip,

        @Schema(example = "476280628")
        @Valid @NotNull(message = "{regon.invalid}") Regon regon,

        @Schema(example = "123123123")
        @Valid @NotNull(message = "{phone.invalid}") PhoneNumber phoneNumber,

        @Schema(example = "ff131a86-1f42-11ee-be56-0242ac120002")
        @Valid @NotNull(message = "{schoolLevel.empty}") SchoolLevelId schoolLevelId

) implements Command {

    public static final String NAME = "name";
    public static final String ADDRESS = "address";
    public static final String POSTAL_CODE = "postalCode";
    public static final String CITY = "city";
    public static final String NIP = "nip";
    public static final String REGON = "regon";
    public static final String PHONE_NUMBER = "phoneNumber";
    public static final String SCHOOL_LEVEL_ID = "schoolLevelId";


    @JsonCreator
    public CreateSchoolCommand(Name name, Address address, PostalCode postalCode, City city, Nip nip, Regon regon,
                               PhoneNumber phoneNumber, SchoolLevelId schoolLevelId) {
        this(SchoolId.create(), name, address, postalCode, city, nip, regon, phoneNumber, schoolLevelId);
    }
}