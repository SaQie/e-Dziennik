package pl.edziennik.eDziennik.server.subject.services.validator;

import pl.edziennik.eDziennik.server.basics.AbstractValidator;
import pl.edziennik.eDziennik.server.subject.domain.dto.SubjectRequestApiDto;

public interface SubjectValidators extends AbstractValidator<SubjectRequestApiDto> {

    String EXCEPTION_MESSAGE_SUBJECT_ALREADY_EXIST = "subject.already.exist";


}
