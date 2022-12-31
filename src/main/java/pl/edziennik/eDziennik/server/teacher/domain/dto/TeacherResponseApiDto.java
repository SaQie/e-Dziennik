package pl.edziennik.eDziennik.server.teacher.domain.dto;

import lombok.Getter;
import pl.edziennik.eDziennik.server.role.domain.dto.RoleResponseApiDto;
import pl.edziennik.eDziennik.server.school.domain.dto.SchoolResponseApiDto;

@Getter
public class TeacherResponseApiDto {

    private Long id;

    private final String username;
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String postalCode;
    private final String city;
    private final String pesel;
    private final String phoneNumber;
    private final Long idRole;
    private final Long idSchool;

    public TeacherResponseApiDto(Long id,String username, String firstName, String lastName, String address, String postalCode, String city, String pesel, String phoneNumber, Long idRole, Long idSchool) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.postalCode = postalCode;
        this.city = city;
        this.pesel = pesel;
        this.phoneNumber = phoneNumber;
        this.idRole = idRole;
        this.idSchool = idSchool;
        this.username = username;
    }

    public TeacherResponseApiDto(Long id,String username, String firstName, String lastName, String address, String postalCode, String city, String pesel, String phoneNumber, Long idRole) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.postalCode = postalCode;
        this.city = city;
        this.pesel = pesel;
        this.phoneNumber = phoneNumber;
        this.idRole = idRole;
        this.idSchool = null;
        this.username = username;
    }
}
