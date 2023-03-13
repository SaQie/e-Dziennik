package pl.edziennik.eDziennik.domain.parent.services;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import pl.edziennik.eDziennik.domain.parent.dao.ParentDao;
import pl.edziennik.eDziennik.domain.parent.domain.Parent;
import pl.edziennik.eDziennik.domain.parent.domain.dto.ParentRequestApiDto;
import pl.edziennik.eDziennik.domain.parent.domain.dto.mapper.ParentMapper;
import pl.edziennik.eDziennik.domain.parent.services.validator.ParentValidators;
import pl.edziennik.eDziennik.server.basics.validator.ServiceValidator;
import pl.edziennik.eDziennik.server.exceptions.BusinessException;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

@Service
@AllArgsConstructor
class ParentValidatorService extends ServiceValidator<ParentValidators, ParentRequestApiDto> {

    protected void valid(ParentRequestApiDto dto) {
        runValidators(dto);
    }


    protected void checkParentHasStudent(Long id) {
        runSelectedValidator(id, ParentValidators.PARENT_STILL_HAS_STUDENT_VALIDATOR);
    }
}
