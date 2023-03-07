package pl.edziennik.eDziennik.domain.parent.services;

import pl.edziennik.eDziennik.domain.parent.domain.dto.ParentRequestApiDto;
import pl.edziennik.eDziennik.domain.parent.domain.dto.ParentResponseApiDto;
import pl.edziennik.eDziennik.server.basics.dto.Page;
import pl.edziennik.eDziennik.server.basics.dto.PageRequest;

import java.util.List;

public interface ParentService {

    ParentResponseApiDto register(ParentRequestApiDto dto);

    ParentResponseApiDto findById(Long id);

    void deleteById(Long id);

    Page<List<ParentResponseApiDto>> findAll(PageRequest pageRequest);
}
