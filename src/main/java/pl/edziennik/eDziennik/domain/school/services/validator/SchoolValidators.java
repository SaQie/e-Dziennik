package pl.edziennik.eDziennik.domain.school.services.validator;

import pl.edziennik.eDziennik.domain.school.dto.SchoolRequestApiDto;
import pl.edziennik.eDziennik.server.basics.validator.AbstractValidator;

public interface SchoolValidators extends AbstractValidator<SchoolRequestApiDto> {

    String SCHOOL_ALREADY_EXISTS_VALIDATOR_NAME = SchoolAlreadyExistsValidator.class.getSimpleName();
    String SCHOOL_NIP_ALREADY_EXISTS_VALIDATOR_NAME = SchoolNipAlreadyExistsValidator.class.getSimpleName();
    String SCHOOL_REGON_ALREADY_EXISTS_VALIDATOR_NAME = SchoolRegonAlreadyExistValidator.class.getSimpleName();

    String EXCEPTION_MESSAGE_SCHOOL_ALREADY_EXIST = "school.already.exist";
    String EXCEPTION_MESSAGE_SCHOOL_WITH_NIP_ALREADY_EXIST = "school.with.nip.already.exist";
    String EXCEPTION_MESSAGE_SCHOOL_WITH_REGON_ALREADY_EXIST = "school.with.regon.already.exist";

}
