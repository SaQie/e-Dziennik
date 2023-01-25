package pl.edziennik.eDziennik.server.settings.dao;

import pl.edziennik.eDziennik.server.basics.IBaseDao;
import pl.edziennik.eDziennik.server.settings.domain.Settings;
import pl.edziennik.eDziennik.server.settings.domain.SettingsDto;

import java.util.List;
import java.util.Optional;

public interface SettingsDao extends IBaseDao<Settings> {

    List<SettingsDto> getAllSettingsData();

    Optional<Settings> findByName(String name);

}
