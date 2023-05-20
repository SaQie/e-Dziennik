package pl.edziennik.domain.address;

import jakarta.persistence.*;
import lombok.*;
import pl.edziennik.common.valueobject.City;
import pl.edziennik.common.valueobject.PostalCode;
import pl.edziennik.common.valueobject.id.AddressId;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Address {

    @EmbeddedId
    private AddressId addressId = AddressId.create();

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "address", nullable = false))
    })
    private pl.edziennik.common.valueobject.Address address;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "city",nullable = false))
    })
    private City city;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "postal_code", nullable = false))
    })
    private PostalCode postalCode;


    public static Address of(pl.edziennik.common.valueobject.Address address, City city, PostalCode postalCode){
        Address addressEntity = new Address();
        addressEntity.setAddress(address);
        addressEntity.setCity(city);
        addressEntity.setPostalCode(postalCode);
        return addressEntity;
    }
}
