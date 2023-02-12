package pl.edziennik.eDziennik.domain.teacher.dto;

import lombok.Builder;
import lombok.Getter;
import pl.edziennik.eDziennik.domain.school.dto.SchoolSimpleResponseApiDto;

@Getter
@Builder
public class TeacherResponseApiDto {

    private final Long id;

    private final String username;
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String postalCode;
    private final String city;
    private final String pesel;
    private final String phoneNumber;
    private final SchoolSimpleResponseApiDto school;
    private final String role;

}
