package pl.edziennik.eDziennik.query.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.domain.address.domain.Address;
import pl.edziennik.eDziennik.domain.address.domain.wrapper.AddressId;

@Repository
public interface AddressQueryRepository extends JpaRepository<Address, AddressId> {

}
