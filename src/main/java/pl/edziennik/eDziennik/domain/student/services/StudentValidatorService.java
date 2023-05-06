package pl.edziennik.eDziennik.domain.student.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.domain.school.domain.School;
import pl.edziennik.eDziennik.domain.school.repository.SchoolRepository;
import pl.edziennik.eDziennik.domain.schoolclass.domain.SchoolClass;
import pl.edziennik.eDziennik.domain.schoolclass.repository.SchoolClassRepository;
import pl.edziennik.eDziennik.domain.student.dto.StudentRequestApiDto;
import pl.edziennik.eDziennik.server.basic.validator.ServiceValidator;
import pl.edziennik.eDziennik.server.basic.validator.ValidatePurpose;

@Service
@AllArgsConstructor
class StudentValidatorService extends ServiceValidator<StudentRequestApiDto> {

    private final SchoolClassRepository schoolClassRepository;
    private final SchoolRepository schoolRepository;


    protected void valid(final StudentRequestApiDto dto) {
        validate(dto, ValidatePurpose.CREATE);
        schoolClassRepository.findById(dto.schoolClassId())
                .orElseThrow(notFoundException(dto.schoolClassId(), SchoolClass.class));
        schoolRepository.findById(dto.schoolId())
                .orElseThrow(notFoundException(dto.schoolId(), School.class));
    }
}
