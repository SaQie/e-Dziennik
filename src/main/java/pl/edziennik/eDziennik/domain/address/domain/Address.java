package pl.edziennik.eDziennik.domain.address.domain;

import lombok.*;
import pl.edziennik.eDziennik.domain.address.domain.wrapper.AddressId;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_id_seq")
    @SequenceGenerator(name = "address_id_seq", sequenceName = "address_id_seq", allocationSize = 1)
    @Getter(AccessLevel.NONE)
    private Long id;

    private String address;
    private String city;
    private String postalCode;

    public AddressId getAddressId() {
        return AddressId.wrap(id);
    }


}
