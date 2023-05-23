package pl.edziennik.application.query.schoolclass.detailed;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.query.IQueryHandler;
import pl.edziennik.common.dto.schoolclass.DetailedSchoolClassDto;
import pl.edziennik.common.dto.schoolclass.StudentSummaryForSchoolClassDto;
import pl.edziennik.infrastructure.repository.schoolclass.SchoolClassQueryRepository;
import pl.edziennik.infrastructure.spring.ResourceCreator;
import pl.edziennik.infrastructure.spring.exception.BusinessException;

import java.util.List;

@Component
@AllArgsConstructor
class GetDetailedSchoolClassQueryHandler implements IQueryHandler<GetDetailedSchoolClassQuery, DetailedSchoolClassDto> {

    private final SchoolClassQueryRepository schoolClassQueryRepository;
    private final ResourceCreator res;

    @Override
    public DetailedSchoolClassDto handle(GetDetailedSchoolClassQuery query) {
        DetailedSchoolClassDto dtoHeader = schoolClassQueryRepository.getSchoolClass(query.schoolClassId());
        List<StudentSummaryForSchoolClassDto> studentsSummary = schoolClassQueryRepository.getSchoolClassStudentsSummary(query.schoolClassId());

        if (dtoHeader == null) {
            throw new BusinessException(
                    res.notFoundError(GetDetailedSchoolClassQuery.SCHOOL_CLASS_ID, query.schoolClassId())
            );
        }
        return new DetailedSchoolClassDto(dtoHeader, studentsSummary);
    }
}
