package pl.edziennik.eDziennik.domain.parent.domain.dto;

import lombok.Builder;
import lombok.Getter;
import pl.edziennik.eDziennik.domain.student.dto.StudentSimpleResponseApiDto;

@Builder
public record ParentResponseApiDto(
        Long id,
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
        String role,
        StudentSimpleResponseApiDto student
) {



}
