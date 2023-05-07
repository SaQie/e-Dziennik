package pl.edziennik.domain.address;

import jakarta.persistence.*;
import lombok.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@IdClass(AddressId.class)
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

    public Address(String address, String city, String postalCode) {
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
    }
}
