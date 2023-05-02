package pl.edziennik.eDziennik.domain.teacher.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.domain.school.domain.School;
import pl.edziennik.eDziennik.domain.school.repository.SchoolRepository;
import pl.edziennik.eDziennik.domain.teacher.dto.TeacherRequestApiDto;
import pl.edziennik.eDziennik.server.basic.validator.ServiceValidator;

@Service
@AllArgsConstructor
class TeacherValidatorService extends ServiceValidator<TeacherRequestApiDto> {

    private final SchoolRepository schoolRepository;

    protected void validate(final TeacherRequestApiDto dto) {
        runValidators(dto);
        schoolRepository.findById(dto.schoolId())
                .orElseThrow(notFoundException(dto.schoolId(), School.class));
    }

}
