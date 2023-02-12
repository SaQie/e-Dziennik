package pl.edziennik.eDziennik.domain.admin.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.domain.admin.dto.AdminRequestApiDto;
import pl.edziennik.eDziennik.domain.admin.service.validator.AdminValidators;
import pl.edziennik.eDziennik.server.basics.validator.ServiceValidator;

@Service
@AllArgsConstructor
class AdminValidatorService extends ServiceValidator<AdminValidators, AdminRequestApiDto> {

    @Override
    protected void valid(AdminRequestApiDto dto) {
        runValidatorChain(dto);
    }
}
