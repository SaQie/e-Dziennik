package pl.edziennik.eDziennik.server.teacher.domain.dto;

import lombok.Getter;
import pl.edziennik.eDziennik.server.role.domain.dto.RoleResponseApiDto;
import pl.edziennik.eDziennik.server.school.domain.dto.SchoolResponseApiDto;
import pl.edziennik.eDziennik.server.basics.AbstractDto;

@Getter
public class TeacherResponseApiDto extends AbstractDto {

    private Long id;

    private String firstName;
    private String lastName;
    private String adress;
    private String postalCode;
    private String city;
    private String pesel;
    private String phoneNumber;
    private RoleResponseApiDto role;
    private SchoolResponseApiDto school;

    public TeacherResponseApiDto(Long id, String firstName, String lastName, String adress, String postalCode, String city, String pesel, String phoneNumber, RoleResponseApiDto role, SchoolResponseApiDto school) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.adress = adress;
        this.postalCode = postalCode;
        this.city = city;
        this.pesel = pesel;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.school = school;
    }

    public TeacherResponseApiDto(Long id, String firstName, String lastName, String adress, String postalCode, String city, String pesel, String phoneNumber, RoleResponseApiDto role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.adress = adress;
        this.postalCode = postalCode;
        this.city = city;
        this.pesel = pesel;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }
}
