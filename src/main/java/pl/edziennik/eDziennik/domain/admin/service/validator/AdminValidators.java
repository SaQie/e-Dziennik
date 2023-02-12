package pl.edziennik.eDziennik.domain.admin.service.validator;

import pl.edziennik.eDziennik.domain.admin.dto.AdminRequestApiDto;
import pl.edziennik.eDziennik.server.basics.validator.AbstractValidator;

public interface AdminValidators extends AbstractValidator<AdminRequestApiDto> {

    String EXCEPTION_MESSAGE_ADMIN_ALREADY_EXIST = "admin.already.exist";

}
