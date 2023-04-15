package pl.edziennik.eDziennik.domain.parent.domain.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Builder;
import pl.edziennik.eDziennik.domain.parent.domain.wrapper.ParentId;
import pl.edziennik.eDziennik.domain.student.dto.StudentSimpleResponseApiDto;

@Builder
public record ParentResponseApiDto(
        @JsonUnwrapped
        ParentId parentId,
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
