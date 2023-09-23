package pl.edziennik.application.command.address.changeaddress;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import pl.edziennik.application.common.dispatcher.Command;
import pl.edziennik.application.common.dispatcher.Handler;
import pl.edziennik.common.valueobject.vo.Address;
import pl.edziennik.common.valueobject.vo.City;
import pl.edziennik.common.valueobject.vo.PostalCode;

import java.util.UUID;

/**
 * A command used for update address
 * <br>
 * <b>Note that, this command is common for student,teacher,parent and school entities</b>
 */
@Handler(handler = ChangeAddressCommandHandler.class)
public record ChangeAddressCommand(
        UUID id,
        @Schema(example = "Ogrodowa 2/2")
        @Valid Address address,

        @Schema(example = "Warszawa")
        @Valid City city,

        @Schema(example = "22-222")
        @Valid PostalCode postalCode,
        @JsonIgnore CommandFor commandFor
) implements Command {

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
