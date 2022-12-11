package pl.edziennik.eDziennik.server.personinformation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PersonInformation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_information_id_seq")
    @SequenceGenerator(name = "person_information_id_seq", sequenceName = "person_information_id_seq", allocationSize = 1)
    private Long id;

    private String firstName;
    private String lastName;
    private String pesel;

}
