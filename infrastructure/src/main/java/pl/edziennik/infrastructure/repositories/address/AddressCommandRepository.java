package pl.edziennik.infrastructure.repositories.address;

import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.id.AddressId;
import pl.edziennik.domain.address.Address;

@RepositoryDefinition(domainClass = Address.class, idClass = AddressId.class)
public interface AddressCommandRepository {

}
