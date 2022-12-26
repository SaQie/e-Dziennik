package pl.edziennik.eDziennik.server.admin.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.server.admin.domain.Admin;
import pl.edziennik.eDziennik.server.admin.domain.dto.AdminRequestApiDto;
import pl.edziennik.eDziennik.server.admin.domain.dto.mapper.AdminMapper;
import pl.edziennik.eDziennik.server.admin.services.validator.AdminValidators;
import pl.edziennik.eDziennik.server.basics.ServiceValidator;

@Service
@AllArgsConstructor
public class AdminValidatorService extends ServiceValidator<AdminValidators, AdminRequestApiDto> {

    protected Admin validateDtoAndMapToEntity(AdminRequestApiDto dto){
        super.validate(dto);
        return AdminMapper.mapToEntity(dto);
    }

}
