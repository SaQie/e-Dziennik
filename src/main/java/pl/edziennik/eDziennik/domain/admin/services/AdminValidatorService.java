package pl.edziennik.eDziennik.domain.admin.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.domain.admin.dto.AdminRequestApiDto;
import pl.edziennik.eDziennik.domain.admin.services.validator.AdminValidators;
import pl.edziennik.eDziennik.server.basics.validator.ServiceValidator;

@Service
@AllArgsConstructor
class AdminValidatorService extends ServiceValidator<AdminValidators, AdminRequestApiDto> {


    protected void valid(AdminRequestApiDto dto) {
        runValidators(dto);
    }
}
