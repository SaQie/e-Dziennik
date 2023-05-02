package pl.edziennik.eDziennik.domain.subject.services.validator;

import pl.edziennik.eDziennik.server.basic.validator.AbstractValidator;
import pl.edziennik.eDziennik.domain.subject.dto.SubjectRequestApiDto;

public interface SubjectValidators extends AbstractValidator<SubjectRequestApiDto> {

    String SUBJECT_ALREADY_EXIST_VALIDATOR_NAME = SubjectAlreadyExistValidator.class.getSimpleName();

    String EXCEPTION_MESSAGE_SUBJECT_ALREADY_EXIST = "subject.already.exist";


}
