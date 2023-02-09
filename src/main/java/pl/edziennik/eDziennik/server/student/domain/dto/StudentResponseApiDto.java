package pl.edziennik.eDziennik.server.student.domain.dto;

import lombok.Builder;
import lombok.Getter;
import pl.edziennik.eDziennik.server.role.domain.Role;
import pl.edziennik.eDziennik.server.school.domain.dto.SchoolSimpleResponseApiDto;
import pl.edziennik.eDziennik.server.schoolclass.domain.dto.SchoolClassSimpleResponseApiDto;

@Getter
@Builder
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
    private final SchoolSimpleResponseApiDto school;
    private final SchoolClassSimpleResponseApiDto schoolClass;
    private final String role;
}
