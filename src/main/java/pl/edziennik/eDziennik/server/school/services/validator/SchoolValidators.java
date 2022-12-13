package pl.edziennik.eDziennik.server.school.services.validator;

import pl.edziennik.eDziennik.server.basics.AbstractValidator;
import pl.edziennik.eDziennik.server.school.domain.dto.SchoolRequestApiDto;

public interface SchoolValidators extends AbstractValidator<SchoolRequestApiDto> {

    String EXCEPTION_MESSAGE_SCHOOL_ALREADY_EXIST = "school.already.exist";

}
