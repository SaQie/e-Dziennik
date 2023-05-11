package pl.edziennik.application.command.school.create;


import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.base.ValidatedBy;
import pl.edziennik.application.common.dispatcher.command.ICommand;
import pl.edziennik.common.valueobject.*;
import pl.edziennik.common.valueobject.id.SchoolLevelId;

@HandledBy(handler = CreateSchoolCommandHandler.class)
@ValidatedBy(validator = CreateSchoolCommandValidator.class)
public record CreateSchoolCommand(
        Name name,
        Address address,
        PostalCode postalCode,
        City city,
        Nip nip,
        Regon regon,
        PhoneNumber phoneNumber,
        SchoolLevelId schoolLevelId
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
