package pl.edziennik.eDziennik.server.student.domain.dto;

import lombok.Getter;
import pl.edziennik.eDziennik.server.school.domain.dto.SchoolResponseApiDto;
import pl.edziennik.eDziennik.server.schoolclass.domain.dto.SchoolClassResponseApiDto;

@Getter
public class StudentResponseApiDto{

    private Long id;
    private String firstName;
    private String lastName;
    private String adress;
    private String postalCode;
    private String city;
    private String pesel;
    private String parentFirstName;
    private String parentLastName;
    private String parentPhoneNumber;
    private SchoolResponseApiDto school;
    private SchoolClassResponseApiDto schoolClass;

    public StudentResponseApiDto(Long id, String firstName, String lastName, String adress, String postalCode, String city, String pesel, String parentFirstName, String parentLastName, String parentPhoneNumber, SchoolResponseApiDto school, SchoolClassResponseApiDto schoolClass) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.adress = adress;
        this.postalCode = postalCode;
        this.city = city;
        this.pesel = pesel;
        this.parentFirstName = parentFirstName;
        this.parentLastName = parentLastName;
        this.parentPhoneNumber = parentPhoneNumber;
        this.school = school;
        this.schoolClass = schoolClass;
    }
}
