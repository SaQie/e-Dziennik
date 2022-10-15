package pl.edziennik.eDziennik.server.student.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class StudentRequestApiDto{

    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String firstName;
    private String lastName;
    private String adress;
    private String postalCode;
    private String city;
    private String pesel;
    private String parentFirstName;
    private String parentLastName;
    private String parentPhoneNumber;
    private Long school;
    private Long schoolClass;


}
