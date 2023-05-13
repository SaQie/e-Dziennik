package pl.edziennik.application.command.school.create;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.ValidationErrorBuilder;
import pl.edziennik.application.common.dispatcher.base.IBaseValidator;
import pl.edziennik.infrastructure.repositories.school.SchoolCommandRepository;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

@Component
@AllArgsConstructor
class CreateSchoolCommandValidator implements IBaseValidator<CreateSchoolCommand> {

    public static final String MESSAGE_KEY_SCHOOL_ALREADY_EXISTS = "school.already.exists";
    public static final String MESSAGE_KEY_SCHOOL_WITH_NIP_ALREADY_EXISTS = "school.with.nip.already.exists";
    public static final String MESSAGE_KEY_SCHOOL_WITH_REGON_ALREADY_EXISTS = "school.with.regon.already.exists";

    private final SchoolCommandRepository schoolCommandRepository;

    @Override
    public void validate(CreateSchoolCommand command, ValidationErrorBuilder errorBuilder) {
        if (schoolCommandRepository.isExistsByName(command.name())) {
            errorBuilder.addError(
                    CreateSchoolCommand.NAME,
                    MESSAGE_KEY_SCHOOL_ALREADY_EXISTS,
                    ErrorCode.OBJECT_ALREADY_EXISTS,
                    command.name()
            );
        }

        if (schoolCommandRepository.isExistsByNip(command.nip())) {
            errorBuilder.addError(
                    CreateSchoolCommand.NIP,
                    MESSAGE_KEY_SCHOOL_WITH_NIP_ALREADY_EXISTS,
                    ErrorCode.OBJECT_ALREADY_EXISTS,
                    command.nip()
            );
        }

        if (schoolCommandRepository.isExistsByRegon(command.regon())) {
            errorBuilder.addError(
                    CreateSchoolCommand.REGON,
                    MESSAGE_KEY_SCHOOL_WITH_REGON_ALREADY_EXISTS,
                    ErrorCode.OBJECT_ALREADY_EXISTS,
                    command.regon()
            );
        }
    }
}