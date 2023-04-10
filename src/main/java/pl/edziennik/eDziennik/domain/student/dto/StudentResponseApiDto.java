package pl.edziennik.eDziennik.domain.student.dto;

import lombok.Builder;
import pl.edziennik.eDziennik.domain.parent.domain.dto.ParentSimpleResponseApiDto;
import pl.edziennik.eDziennik.domain.school.dto.SchoolSimpleResponseApiDto;
import pl.edziennik.eDziennik.domain.schoolclass.dto.SchoolClassSimpleResponseApiDto;

@Builder
public record StudentResponseApiDto(
        Long id,
        Long userId,
        String username,
        String firstName,
        String lastName,
        String fullName,
        String address,
        String postalCode,
        String city,
        String pesel,
        String email,
        String phoneNumber,
        ParentSimpleResponseApiDto parent,
        SchoolSimpleResponseApiDto school,
        SchoolClassSimpleResponseApiDto schoolClass,
        String role
) {
}
