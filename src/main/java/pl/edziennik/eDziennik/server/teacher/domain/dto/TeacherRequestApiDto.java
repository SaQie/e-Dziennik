package pl.edziennik.eDziennik.server.teacher.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TeacherRequestApiDto{

    private String username;
    private String firstName;
    private String lastName;
    private String adress;
    private String postalCode;
    private String city;
    private String pesel;
    private String role;
    private String phoneNumber;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private Long school;

}
