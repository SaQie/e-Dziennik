package pl.edziennik.eDziennik.domain.parent.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edziennik.eDziennik.domain.parent.domain.dto.ParentRequestApiDto;
import pl.edziennik.eDziennik.domain.parent.domain.dto.ParentResponseApiDto;
import pl.edziennik.eDziennik.server.basics.page.PageDto;

public interface ParentService {

    ParentResponseApiDto register(ParentRequestApiDto dto);

    ParentResponseApiDto findById(Long id);

    ParentResponseApiDto update(Long id, ParentRequestApiDto dto);

    void deleteById(Long id);

    PageDto<ParentResponseApiDto> findAll(Pageable pageable);

}
