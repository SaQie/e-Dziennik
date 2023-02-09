package pl.edziennik.eDziennik.server.teacher.domain.dto;

import lombok.Builder;
import lombok.Getter;
import pl.edziennik.eDziennik.server.role.domain.Role;
import pl.edziennik.eDziennik.server.role.domain.dto.RoleResponseApiDto;
import pl.edziennik.eDziennik.server.school.domain.dto.SchoolResponseApiDto;
import pl.edziennik.eDziennik.server.school.domain.dto.SchoolSimpleResponseApiDto;

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
