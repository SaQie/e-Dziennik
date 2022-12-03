package pl.edziennik.eDziennik.server.basics;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@SuppressWarnings("all")
public abstract class ServiceValidator<T, E> {

    @Autowired(required = false)
    private List<T> validators;
    @Autowired
    private Validator<E> validator;

    /**
     * This method run chain-of-responsibility pattern of defined validators
     * Throws IllegalArgumentException if validators are empty or null
     * @param e
     */
    protected void validate(E e) {
        checkValidators();
        validator.validate(e);
    }

    protected void validateByPriority(E e){
        checkValidators();
        validator.validateByPriority(e);
    }

    protected void validateByIds(E e){
        checkValidators();
        validator.validateByIds(e);
    }

    protected void runSelectedValidator(E e, Integer validatorId){
        checkValidators();
        validator.runSelectedValidator(e,validatorId);
    }

    protected void validateBySelectedPriority(E e, ValidatorPriority priority){
        checkValidators();
        validator.validateBySelectedPriority(e,priority);
    }


    private void checkValidators(){
        if (validators != null && !validators.isEmpty()) {
            if (!validator.isValidatorsDefined()) {
                validator.setValidators((List<AbstractValidator<E>>) validators);
                return;
            }
            return;
        }
        throw new IllegalArgumentException("Define your validators !");
    }


}
