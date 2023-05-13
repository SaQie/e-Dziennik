package pl.edziennik.application.command.school.create;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.base.ValidatedBy;
import pl.edziennik.application.common.dispatcher.command.ICommand;
import pl.edziennik.common.valueobject.*;
import pl.edziennik.common.valueobject.id.SchoolLevelId;

@HandledBy(handler = CreateSchoolCommandHandler.class)
@ValidatedBy(validator = CreateSchoolCommandValidator.class)
public record CreateSchoolCommand(

        @Valid @NotNull(message = "{name.empty}") Name name,
        @Valid @NotNull(message = "{address.empty}") Address address,
        @Valid @NotNull(message = "{postalCode.empty}") PostalCode postalCode,
        @Valid @NotNull(message = "{city.empty}") City city,
        @Valid @NotNull(message = "{nip.invalid}") Nip nip,
        @Valid @NotNull(message = "{regon.invalid}") Regon regon,
        @Valid @NotNull(message = "{phone.invalid}") PhoneNumber phoneNumber,
        @Valid @NotNull(message = "{schoolLevel.empty}") SchoolLevelId schoolLevelId

) implements ICommand<OperationResult> {

    public static final String NAME = "name";
    public static final String ADDRESS = "address";
    public static final String POSTAL_CODE = "postalCode";
    public static final String CITY = "city";
    public static final String NIP = "nip";
    public static final String REGON = "regon";
    public static final String PHONE_NUMBER = "phoneNumber";
    public static final String SCHOOL_LEVEL_ID = "schoolLevelId";

}
