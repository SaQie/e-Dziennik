package pl.edziennik.eDziennik.domain.teacher.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Builder;
import pl.edziennik.eDziennik.domain.school.dto.SchoolSimpleResponseApiDto;
import pl.edziennik.eDziennik.domain.teacher.domain.wrapper.TeacherId;
import pl.edziennik.eDziennik.domain.user.domain.wrapper.UserId;

@Builder
public record TeacherResponseApiDto(
        @JsonUnwrapped
        TeacherId teacherId,
        @JsonUnwrapped
        UserId userId,
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
