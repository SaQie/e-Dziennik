package pl.edziennik.common.view.school;

import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.id.SchoolLevelId;
import pl.edziennik.common.valueobject.vo.*;

public record DetailedSchoolView(
        SchoolId schoolId,
        Name name,
        PostalCode postalCode,
        City city,
        Nip nip,
        Regon regon,
        Address address,
        PhoneNumber phoneNumber,
        SchoolLevelId schoolLevelId,
        Name schoolLevelName,
        FullName directorName
) {
}
