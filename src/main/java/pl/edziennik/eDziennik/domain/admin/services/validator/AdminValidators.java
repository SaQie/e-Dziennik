package pl.edziennik.eDziennik.domain.admin.services.validator;

import pl.edziennik.eDziennik.domain.admin.dto.AdminRequestApiDto;
import pl.edziennik.eDziennik.server.basic.validator.AbstractValidator;

public interface AdminValidators extends AbstractValidator<AdminRequestApiDto> {

    String EXCEPTION_MESSAGE_ADMIN_ALREADY_EXIST = "admin.already.exist";

    String ADMIN_ALREADY_EXISTS_VALIDATOR_NAME = AdminAlreadyExistValidator.class.getSimpleName();

}
