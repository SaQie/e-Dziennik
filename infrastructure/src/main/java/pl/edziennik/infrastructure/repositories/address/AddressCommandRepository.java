package pl.edziennik.infrastructure.repositories.address;

import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.domain.address.Address;
import pl.edziennik.domain.address.AddressId;

@RepositoryDefinition(domainClass = Address.class, idClass = AddressId.class)
public interface AddressCommandRepository {

}
