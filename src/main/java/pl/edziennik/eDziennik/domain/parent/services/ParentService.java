package pl.edziennik.eDziennik.domain.parent.services;

import org.springframework.data.domain.Pageable;
import pl.edziennik.eDziennik.domain.parent.domain.dto.ParentRequestApiDto;
import pl.edziennik.eDziennik.domain.parent.domain.dto.ParentResponseApiDto;
import pl.edziennik.eDziennik.domain.parent.domain.wrapper.ParentId;
import pl.edziennik.eDziennik.server.basics.page.PageDto;

public interface ParentService {

    ParentResponseApiDto register(final ParentRequestApiDto dto);

    ParentResponseApiDto findById(final ParentId parentId);

    ParentResponseApiDto update(final ParentId parentId, final ParentRequestApiDto dto);

    void deleteById(final ParentId parentId);

    PageDto<ParentResponseApiDto> findAll(final Pageable pageable);

}
