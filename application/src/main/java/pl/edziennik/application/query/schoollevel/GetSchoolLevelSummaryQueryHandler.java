package pl.edziennik.application.query.schoollevel;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.application.common.dispatcher.query.IQueryHandler;
import pl.edziennik.common.dto.schoollevel.SchoolLevelDto;
import pl.edziennik.infrastructure.repository.schoollevel.SchoolLevelQueryRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class GetSchoolLevelSummaryQueryHandler implements IQueryHandler<GetSchoolLevelSummaryQuery, List<SchoolLevelDto>> {

    private final SchoolLevelQueryRepository repository;

    @Override
    public List<SchoolLevelDto> handle(GetSchoolLevelSummaryQuery query) {
        return repository.findAll();
    }
}