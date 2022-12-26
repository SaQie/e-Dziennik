package pl.edziennik.eDziennik.server.admin.services.validator;

import pl.edziennik.eDziennik.server.admin.domain.dto.AdminRequestApiDto;
import pl.edziennik.eDziennik.server.basics.AbstractValidator;

public interface AdminValidators extends AbstractValidator<AdminRequestApiDto> {

    String EXCEPTION_MESSAGE_ADMIN_ALREADY_EXIST = "admin.already.exist";

}
