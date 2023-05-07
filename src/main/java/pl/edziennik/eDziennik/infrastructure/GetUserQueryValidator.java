package pl.edziennik.eDziennik.infrastructure;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.domain.user.repository.UserRepository;
import pl.edziennik.eDziennik.infrastructure.spring.base.IBaseValidator;
import pl.edziennik.eDziennik.infrastructure.spring.dispatcher.ValidationErrorBuilder;

@Service
@AllArgsConstructor
public class GetUserQueryValidator implements IBaseValidator<GetUserQuery> {

    private final UserRepository repository;

    @Override
    public void validate(GetUserQuery query, ValidationErrorBuilder resultBuilder) {
        System.out.println("Gowno");
    }
}
