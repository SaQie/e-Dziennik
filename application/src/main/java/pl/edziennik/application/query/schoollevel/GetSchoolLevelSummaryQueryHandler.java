package pl.edziennik.application.query.schoollevel;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.application.common.dispatcher.IQueryHandler;
import pl.edziennik.common.view.schoollevel.SchoolLevelView;
import pl.edziennik.infrastructure.repository.schoollevel.SchoolLevelQueryRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class GetSchoolLevelSummaryQueryHandler implements IQueryHandler<GetSchoolLevelSummaryQuery, List<SchoolLevelView>> {

    private final SchoolLevelQueryRepository repository;

    @Override
    public List<SchoolLevelView> handle(GetSchoolLevelSummaryQuery query) {
        return repository.findAll();
    }
}
