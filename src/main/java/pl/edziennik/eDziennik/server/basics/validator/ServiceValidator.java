package pl.edziennik.eDziennik.server.basics.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.server.basics.service.BaseService;
import pl.edziennik.eDziennik.server.basics.dto.ApiErrorsDto;
import pl.edziennik.eDziennik.server.exceptions.BusinessException;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Base class for validation
 *
 * @param <VALIDATORS> Interface which specific validators implement
 * @param <INPUT>      Object to validate
 */
@Service
@SuppressWarnings("all")
public abstract class ServiceValidator<VALIDATORS extends AbstractValidator<INPUT>, INPUT> extends BaseService {

    @Autowired
    private List<VALIDATORS> validators;
    @Autowired
    protected ResourceCreator resourceCreator;
    @Autowired
    public BasicValidator basicValidator;

    /**
     * This method run chain-of-responsibility pattern of defined validators
     *
     * @param input -> Object to validate
     */
    protected void runValidatorChain(INPUT input) {
        List<ApiErrorsDto> errors = new ArrayList<>();
        validators.forEach(valid -> valid.validate(input).ifPresent(error -> {
            if (error.isThrownImmediately()) {
                throw new BusinessException(error);
            }
            errors.add(error);
        }));
        if (!errors.isEmpty()) {
            throw new BusinessException(errors);
        }
    }

    /**
     * Method which need to be overrided by concrete validator service
     *
     * @param dto
     */
    protected abstract void valid(INPUT dto);

    /**
     * This method run chain-of-responsibility pattern of defined validators sorted by priority (High-Low)
     *
     * @param input -> Object to validate
     */
    protected void validateByPriority(INPUT input) {
        List<ApiErrorsDto> errors = new ArrayList<>();
        validators.stream().sorted(Comparator.comparing(validator -> validator.getValidationPriority().ordinal())).
                forEach(valid -> valid.validate(input).ifPresent(error -> {
                    if (error.isThrownImmediately()) {
                        throw new BusinessException(error);
                    }
                    errors.add(error);
                }));
        if (!errors.isEmpty()) {
            throw new BusinessException(errors);
        }
    }

    /**
     * This method run chain-of-responsibility patter of defined validators sorted by ids ASC
     *
     * @param input  -> Object to validate
     */
    protected void validateByIds(INPUT input) {
        List<ApiErrorsDto> erros = new ArrayList<>();
        validators.stream().sorted(Comparator.comparing(AbstractValidator::getValidationNumber)).
                forEach(valid -> valid.validate(input).ifPresent(error -> {
                    if (error.isThrownImmediately()) {
                        throw new BusinessException(error);
                    }
                    erros.add(error);
                }));
        if (!erros.isEmpty()) {
            throw new BusinessException(erros);
        }
    }

    /**
     * This method run only one selected validator by Id
     *
     * @param input  -> Object to validate
     * @param validatorId -> Validator id which will be run
     */
    protected void runSelectedValidator(INPUT input, Integer validatorId) {
        if (validators != null && !validators.isEmpty()) {
            validators.stream().filter(item -> Objects.equals(item.getValidationNumber(), validatorId))
                    .findFirst().ifPresent(validator -> validator.validate(input).ifPresent(error -> {
                        throw new BusinessException(error);
                    }));
        }
    }

    /**
     * This method run chain-of-responsibility patter for defined validators with only selected priority
     *
     * @param input -> Object to validate
     * @param priority -> Validators priority which will be run
     */
    protected void validateBySelectedPriority(INPUT input, ValidatorPriority priority) {
        List<ApiErrorsDto> erros = new ArrayList<>();
        validators.stream().filter(validator -> validator.getValidationPriority().equals(priority)).
                forEach(valid -> valid.validate(input).ifPresent(error -> {
                    if (error.isThrownImmediately()) {
                        throw new BusinessException(error);
                    }
                    erros.add(error);
                }));
        if (!erros.isEmpty()) {
            throw new BusinessException(erros);
        }
    }


}
