package pl.edziennik.eDziennik.server.basic.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.server.basic.dto.ApiValidationResult;
import pl.edziennik.eDziennik.server.basic.service.BaseService;
import pl.edziennik.eDziennik.server.exception.BusinessException;

import java.util.*;

/**
 * Base class for validation
 *
 * @param <VALIDATORS> Interface which specific validators implement
 * @param <INPUT>      Object to validate
 */
@Service
@SuppressWarnings("all")
public abstract class ServiceValidator<INPUT> extends BaseService {

    @Autowired
    private List<AbstractValidator<INPUT>> validators;
    @Autowired
    private ApplicationContext context;

    /**
     * This method run chain-of-responsibility pattern of defined validators (all validators)
     *
     * @param input -> Object to validate
     */
    protected void validate(final INPUT input) {
        List<ApiValidationResult> errors = new ArrayList<>();
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
     * This method run chain-of-responsibility patter of defined by validate purpose validators
     *
     * @param input           -> Object to validate
     * @param validatePurpose -> Type of validation
     */
    protected void validate(final INPUT input, final ValidatePurpose validatePurpose) {
        List<ApiValidationResult> errors = new ArrayList<>();
        validators.stream()
                .filter(validator -> validator.getValidatorPurposes().contains(validatePurpose))
                .forEach(specificValidator -> specificValidator.validate(input)
                        .ifPresent(error -> {
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
     * This method run chain-of-responsibility pattern of defined by validate purpose set validators
     *
     * @param input              -> Object to validate
     * @param validatePurposeSet -> Types of validation
     */
    protected void validate(final INPUT input, final Set<ValidatePurpose> validatePurposeSet) {
        List<ApiValidationResult> errors = new ArrayList<>();
        validators.stream()
                .filter(validator -> validator.getValidatorPurposes().containsAll(validatePurposeSet))
                .forEach(specificValidator -> specificValidator.validate(input)
                        .ifPresent(error -> {
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
     * This method run only one selected validator by Id
     *
     * @param input       -> Object to validate
     * @param validatorId -> Validator id which will be run
     */
    protected void runValidator(final INPUT input, final String validatorId) {
        if (validators != null && !validators.isEmpty()) {
            validators.stream()
                    .filter(item -> Objects.equals(item.getValidatorId(), validatorId))
                    .forEach(validator -> validator.validate(input).ifPresent(error -> {
                        throw new BusinessException(error);
                    }));
        }
    }

    /**
     * This method run selected validator by validator name with any input
     *
     * @param input
     * @param validatorId -> validator name defined in xxxValidators interface
     * @param <T>
     */
    protected <T> void runSelectedValidator(final T input, String validatorId) {
        validatorId = Character.toLowerCase(validatorId.charAt(0)) + validatorId.substring(1);
        AbstractValidator<T> bean = context.getBean(validatorId, AbstractValidator.class);
        Optional<ApiValidationResult> validateResult = bean.validate(input);
        if (validateResult.isPresent()) {
            throw new BusinessException(validateResult.get());
        }
    }

    /**
     * This method run selected validator by validator name with any inputs
     *
     * @param validatorId
     * @param inputs
     * @param <T>
     */
    protected <T> void runSelectedValidator(String validatorId, final T... inputs) {
        validatorId = Character.toLowerCase(validatorId.charAt(0)) + validatorId.substring(1);
        AbstractValidator<T> bean = context.getBean(validatorId, AbstractValidator.class);
        Optional<ApiValidationResult> validationResult = bean.validate(inputs);
        if (validationResult.isPresent()) {
            throw new BusinessException(validationResult.get());
        }
    }


}
