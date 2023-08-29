package pl.edziennik.application.command.address.changeaddress;

import jakarta.validation.Valid;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.HandledBy;
import pl.edziennik.application.common.dispatcher.ICommand;
import pl.edziennik.common.valueobject.vo.Address;
import pl.edziennik.common.valueobject.vo.City;
import pl.edziennik.common.valueobject.vo.PostalCode;

import java.util.UUID;

/**
 * A command used for update address
 * <br>
 * <b>Note that, this command is common for student,teacher,parent and school entities</b>
 */
@HandledBy(handler = ChangeAddressCommandHandler.class)
public record ChangeAddressCommand(
        UUID id,
        @Valid Address address,
        @Valid City city,
        @Valid PostalCode postalCode,
        CommandFor commandFor
) implements ICommand<OperationResult> {

    public static final String ID = "id";
    public static final String ADDRESS = "address";
    public static final String CITY = "city";
    public static final String POSTAL_CODE = "postalCode";

    public enum CommandFor {
        SCHOOL,
        STUDENT,
        TEACHER,
        PARENT,
        DIRECTOR
    }

}
