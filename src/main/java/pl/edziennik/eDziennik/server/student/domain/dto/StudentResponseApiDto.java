package pl.edziennik.eDziennik.server.student.domain.dto;

import lombok.Getter;
import pl.edziennik.eDziennik.server.role.domain.Role;
import pl.edziennik.eDziennik.server.school.domain.dto.SchoolResponseApiDto;
import pl.edziennik.eDziennik.server.schoolclass.domain.dto.SchoolClassResponseApiDto;

@Getter
public class StudentResponseApiDto {

    private final Long id;
    private final String username;
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String postalCode;
    private final String city;
    private final String pesel;
    private final String email;
    private final String parentFirstName;
    private final String parentLastName;
    private final String parentPhoneNumber;
    private final Long idSchool;
    private final Long idSchoolClass;
    private final String role;

    public StudentResponseApiDto(Long id, String username, String firstName, String lastName, String address, String postalCode, String city, String pesel, String parentFirstName, String parentLastName, String parentPhoneNumber,String email, Long idSchool, Long idSchoolClass) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.postalCode = postalCode;
        this.city = city;
        this.pesel = pesel;
        this.parentFirstName = parentFirstName;
        this.parentLastName = parentLastName;
        this.parentPhoneNumber = parentPhoneNumber;
        this.idSchool = idSchool;
        this.idSchoolClass = idSchoolClass;
        this.username = username;
        this.email = email;
        this.role = Role.RoleConst.ROLE_STUDENT.name();
    }
}
