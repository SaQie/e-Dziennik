package pl.edziennik.eDziennik.server.user.services.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.server.basics.ApiErrorsDto;
import pl.edziennik.eDziennik.server.basics.ExceptionType;
import pl.edziennik.eDziennik.server.basics.ValidatorPriority;
import pl.edziennik.eDziennik.server.user.dao.UserDao;
import pl.edziennik.eDziennik.server.user.domain.User;
import pl.edziennik.eDziennik.server.user.domain.dto.UserRequestDto;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
class UserWithEmailAlreadyExistsValidator implements UserValidators{

    private final UserDao userDao;
    private final ResourceCreator resourceCreator;

    @Override
    public String getValidatorName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public Integer getValidationNumber() {
        return 2;
    }

    @Override
    public ValidatorPriority getValidationPriority() {
        return ValidatorPriority.HIGH;
    }

    @Override
    public Optional<ApiErrorsDto> validate(UserRequestDto dto) {
        if (userDao.isUserExistByEmail(dto.getEmail())){

            String message = resourceCreator.of(EXCEPTION_MESSAGE_USER_ALREADY_EXISTS_BY_EMAIL, dto.getEmail());

            ApiErrorsDto apiErrorsDto = ApiErrorsDto.builder()
                    .fields(List.of(UserRequestDto.EMAIL))
                    .cause(message)
                    .thrownImmediately(false)
                    .errorThrownedBy(getValidatorName())
                    .exceptionType(ExceptionType.BUSINESS)
                    .build();

            return Optional.of(apiErrorsDto);

        }
        return Optional.empty();
    }
}
