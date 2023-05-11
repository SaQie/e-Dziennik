package pl.edziennik.common.dto.school;

import pl.edziennik.common.valueobject.*;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.id.SchoolLevelId;

public record DetailedSchoolDto(
        SchoolId schoolId,
        Name name,
        PostalCode postalCode,
        City city,
        Nip nip,
        Regon regon,
        Address address,
        PhoneNumber phoneNumber,
        SchoolLevelId schoolLevelId,
        Name schoolLevelName
) {
}
