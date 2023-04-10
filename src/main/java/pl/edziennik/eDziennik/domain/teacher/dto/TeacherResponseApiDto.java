package pl.edziennik.eDziennik.domain.teacher.dto;

import lombok.Builder;
import lombok.Getter;
import pl.edziennik.eDziennik.domain.school.dto.SchoolSimpleResponseApiDto;

@Builder
public record TeacherResponseApiDto(
        Long id,
        Long userId,
        String username,
        String firstName,
        String lastName,
        String fullName,
        String address,
        String postalCode,
        String city,
        String email,
        String pesel,
        String phoneNumber,
        SchoolSimpleResponseApiDto school,
        String role

) {


}
