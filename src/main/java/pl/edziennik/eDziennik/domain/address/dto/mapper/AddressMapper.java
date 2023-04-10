package pl.edziennik.eDziennik.domain.address.dto.mapper;

import pl.edziennik.eDziennik.domain.address.domain.Address;
import pl.edziennik.eDziennik.domain.parent.domain.dto.ParentRequestApiDto;
import pl.edziennik.eDziennik.domain.school.dto.SchoolRequestApiDto;
import pl.edziennik.eDziennik.domain.student.dto.StudentRequestApiDto;
import pl.edziennik.eDziennik.domain.teacher.dto.TeacherRequestApiDto;

public class AddressMapper {

    private AddressMapper() {
    }

    public static Address mapToAddress(TeacherRequestApiDto dto) {
        Address addressEntity = new Address();
        addressEntity.setAddress(dto.address());
        addressEntity.setCity(dto.city());
        addressEntity.setPostalCode(dto.postalCode());
        return addressEntity;
    }

    public static Address mapToAddress(StudentRequestApiDto dto) {
        Address addressEntity = new Address();
        addressEntity.setAddress(dto.address());
        addressEntity.setCity(dto.city());
        addressEntity.setPostalCode(dto.postalCode());
        return addressEntity;
    }

    public static Address mapToAddress(SchoolRequestApiDto dto) {
        Address addressEntity = new Address();
        addressEntity.setAddress(dto.address());
        addressEntity.setCity(dto.city());
        addressEntity.setPostalCode(dto.postalCode());
        return addressEntity;
    }

    public static Address mapToAddress(ParentRequestApiDto dto) {
        Address addressEntity = new Address();
        addressEntity.setAddress(dto.address());
        addressEntity.setCity(dto.city());
        addressEntity.setPostalCode(dto.postalCode());
        return addressEntity;
    }
}
