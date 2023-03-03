package pl.edziennik.eDziennik.domain.parent.domain.dto;

import lombok.Builder;
import lombok.Getter;
import pl.edziennik.eDziennik.domain.student.dto.StudentSimpleResponseApiDto;

@Getter
@Builder
public class ParentResponseApiDto {

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
    private final StudentSimpleResponseApiDto student;

}
