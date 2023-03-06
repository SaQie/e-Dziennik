package pl.edziennik.eDziennik.domain.student.dto;

import lombok.Builder;
import lombok.Getter;
import pl.edziennik.eDziennik.domain.parent.domain.dto.ParentSimpleResponseApiDto;
import pl.edziennik.eDziennik.domain.school.dto.SchoolSimpleResponseApiDto;
import pl.edziennik.eDziennik.domain.schoolclass.dto.SchoolClassSimpleResponseApiDto;

@Getter
@Builder
public class StudentResponseApiDto {

    private final Long id;
    private final String username;
    private final String firstName;
    private final String lastName;
    private final String fullName;
    private final String address;
    private final String postalCode;
    private final String city;
    private final String pesel;
    private final String email;
    private final String phoneNumber;
    private final ParentSimpleResponseApiDto parent;
    private final SchoolSimpleResponseApiDto school;
    private final SchoolClassSimpleResponseApiDto schoolClass;
    private final String role;
}
