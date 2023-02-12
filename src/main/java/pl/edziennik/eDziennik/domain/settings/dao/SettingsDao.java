package pl.edziennik.eDziennik.domain.settings.dao;

import pl.edziennik.eDziennik.domain.settings.domain.Settings;
import pl.edziennik.eDziennik.domain.settings.dto.SettingsDto;
import pl.edziennik.eDziennik.server.basics.dao.IBaseDao;

import java.util.List;
import java.util.Optional;

public interface SettingsDao extends IBaseDao<Settings> {

    List<SettingsDto> getAllSettingsData();

    Optional<Settings> findByName(String name);

}
