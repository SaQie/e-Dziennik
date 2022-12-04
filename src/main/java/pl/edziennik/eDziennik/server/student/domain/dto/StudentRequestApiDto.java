package pl.edziennik.eDziennik.server.student.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class StudentRequestApiDto{

    public static final String USERNAME = "username";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String ADDRESS = "username";
    public static final String POSTAL_CODE = "postalCode";
    public static final String CITY = "city";
    public static final String PESEL = "pesel";
    public static final String ROLE = "role";
    public static final String PARENT_PHONE_NUMBER = "parentPhoneNumber";
    public static final String ID_SCHOOL = "idSchool";
    public static final String PARENT_FIRST_NAME = "parentFirstName";
    public static final String PARENT_LAST_NAME = "parentLastName";

    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String firstName;
    private String lastName;
    private String address;
    private String postalCode;
    private String city;
    private String pesel;
    private String parentFirstName;
    private String parentLastName;
    private String parentPhoneNumber;
    private Long idSchool;
    private Long idSchoolClass;


}
