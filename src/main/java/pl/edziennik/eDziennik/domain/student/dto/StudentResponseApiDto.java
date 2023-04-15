package pl.edziennik.eDziennik.domain.student.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Builder;
import pl.edziennik.eDziennik.domain.parent.domain.dto.ParentSimpleResponseApiDto;
import pl.edziennik.eDziennik.domain.school.dto.SchoolSimpleResponseApiDto;
import pl.edziennik.eDziennik.domain.schoolclass.dto.SchoolClassSimpleResponseApiDto;
import pl.edziennik.eDziennik.domain.student.domain.wrapper.StudentId;
import pl.edziennik.eDziennik.domain.user.domain.wrapper.UserId;

@Builder
public record StudentResponseApiDto(
        @JsonUnwrapped
        StudentId studentId,
        @JsonUnwrapped
        UserId userId,
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
