package pl.edziennik.eDziennik.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BasicUser {

    private String adress;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String postalCode;
    private String PESEL;
    private String city;

}
