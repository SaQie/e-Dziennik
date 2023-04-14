package pl.edziennik.eDziennik.domain.student.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.domain.school.domain.School;
import pl.edziennik.eDziennik.domain.school.repository.SchoolRepository;
import pl.edziennik.eDziennik.domain.schoolclass.domain.SchoolClass;
import pl.edziennik.eDziennik.domain.schoolclass.repository.SchoolClassRepository;
import pl.edziennik.eDziennik.domain.student.dto.StudentRequestApiDto;
import pl.edziennik.eDziennik.domain.student.services.validator.StudentValidators;
import pl.edziennik.eDziennik.server.basics.validator.ServiceValidator;
import pl.edziennik.eDziennik.server.basics.validator.ValidatePurpose;

@Service
@AllArgsConstructor
class StudentValidatorService extends ServiceValidator<StudentValidators, StudentRequestApiDto> {

    private final SchoolClassRepository schoolClassRepository;
    private final SchoolRepository schoolRepository;


    protected void valid(final StudentRequestApiDto dto) {
        runValidators(dto, ValidatePurpose.CREATE);
        schoolClassRepository.findById(dto.schoolClassId())
                .orElseThrow(notFoundException(dto.schoolClassId().id(), SchoolClass.class));
        schoolRepository.findById(dto.schoolId())
                .orElseThrow(notFoundException(dto.schoolId().id(), School.class));
    }
}
